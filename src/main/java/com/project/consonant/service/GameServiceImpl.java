package com.project.consonant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.consonant.dao.CategoryDao;
import com.project.consonant.dao.GameDao;
import com.project.consonant.dao.HistoryDao;
import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.Game;
import com.project.consonant.domain.GameInfoVO;
import com.project.consonant.domain.History;
import com.project.consonant.domain.InputQuiz;
import com.project.consonant.domain.Quiz;
import com.project.consonant.service.exception.GameException;

@Service
public class GameServiceImpl implements GameService{
	
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	GameDao gameDao;
	@Autowired
	HistoryDao historyDao;
	@Autowired
	MemberService memberSvc;
	
	
	public List<Category> goCreateGame() {
		return categoryDao.findAllCategory();
	}
	public List<Category> getAllCategory(){	//카테고리 가져오기
		return categoryDao.findAllCategory();
	}
	public List<InputQuiz> insertQuiz(List<InputQuiz> inputQuizList, InputQuiz inputQuiz) { //게임 생성 시 퀴즈 등록
		inputQuizList.add(inputQuiz);
		return inputQuizList;
	}
	
	public List<InputQuiz> removeQuiz(List<InputQuiz> inputQuizList, String question) { //게임 생성 시 퀴즈 삭제
		for(int i = 0; i < inputQuizList.size(); i++) {
			InputQuiz iq = inputQuizList.get(i);
			if(iq.getQuestion().equals(question)) {
				inputQuizList.remove(i);
				break;
			}
		}
		
		return inputQuizList;
	}

	//게임 생성
	@Transactional
	public boolean createGame(CreateGameCommand createGameCommand, List<InputQuiz> inputQuizList) throws GameException {
		if (inputQuizList.size() == 0) { //추가된 퀴즈의 개수가 0개일때 게임 생성 불가
			throw new GameException("퀴즈를 추가해야 합니다.");
		}
		
		Game game = new Game();
		game.setMemberId(createGameCommand.getMemberId());
		game.setGameTitle(createGameCommand.getGameTitle());
		game.setGameIntro(createGameCommand.getGameIntro());
		game.setGameDifficulty(createGameCommand.getGameDifficulty());
		game.setCategoryId(createGameCommand.getCategory());
		game.setQuizNumber(inputQuizList.size());
		
		int scoreSum = 0;
		for(InputQuiz iq : inputQuizList) {
			scoreSum += iq.getQuizDifficulty();
		}
		game.setGameScore(scoreSum);
		game.setGameNo(0);
		int createGameResult = gameDao.createGame(game);
		
		int createQuizResult = 0;
		for(InputQuiz iq : inputQuizList) {
			Quiz quiz = new Quiz();
			quiz.setGameNo(game.getGameNo());
			quiz.setQuestion(iq.getQuestion());
			quiz.setAnswer(iq.getAnswer());
			quiz.setQuizDifficulty(iq.getQuizDifficulty());
			quiz.setHint(iq.getHint());
			quiz.setHintPoint(iq.getHintPoint());
			
			createQuizResult += gameDao.createQuiz(quiz);
		}
		
		if(createGameResult != 1 || createQuizResult == 0)
			return false;
		if(createQuizResult != inputQuizList.size())
			return false;
		
		return true;
	}

	
	
	//게임 리스트 가져오기
	public List<Game> findAllGames(String memberId){
		List<Game> gameList = gameDao.findAllGames(memberId);
		for(Game g : gameList) {
			System.out.println(g.getGameTitle());
		}
		return gameList;
	}
	
	//카테고리별 게임 리스트 가져오기
	public List<Game> findAllGamesByCategory(String memberId, String categoryId){
		List<Game> gameList = gameDao.findAllGamesByCategory(memberId, categoryId);
		for(Game g : gameList) {
			System.out.println(g.getGameTitle());
		}
		return gameList;
	}
	
	
	
	//게임 플레이
	//퀴즈와 함께 게임 정보 가져오기
	public GameInfoVO findGame(int gameNo) {
		GameInfoVO gameInfoVO = gameDao.findGame(gameNo);
		return gameInfoVO;
	}

	
	//게임 채점
	@Transactional
	public Map<String, Integer> gameResult(List<Quiz> playGameQuiz, HashMap<Integer, String> userAnswer, Game gameInfo, String memberId){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		
		int pointFlag = 1; //포인트 획득 가능 여부
		int score = 0; //획득 점수
		int correctNum = 0; //맞은 문제 개수
		for(int i = 0; i < playGameQuiz.size(); i++){
			Quiz q = playGameQuiz.get(i);
			if(q.getAnswer().equals(userAnswer.get(i))) { //정답과 답안이 일치하면 점수 추가, 맞은문제로 표시
				score += q.getQuizDifficulty();
				resultMap.put("index" + (i + 1), 1);
				correctNum++;
			}
			else { //하나라도 오답인 경우 점수를 받을 수 없음. 오답으로 표시
				pointFlag = -1;
				resultMap.put("index" + (i + 1), -1);
			}
		}
		
		
		resultMap.put("score", score);
		memberSvc.updateTotalScoreAndRankings(memberId, score);
		
		resultMap.put("correctNum", correctNum);
		
		if(pointFlag == -1) { //하나라도 틀린 문제 존재 -> 포인트 획득 불가
			resultMap.put("point", 0);
		}
		else {
			resultMap.put("point", 100); //다 맞음 -> 포인트 획득
			memberSvc.updatePoint(memberId, 100, 1);
		}
		
		//db에 history 저장
		History history = new History(correctNum, score, gameInfo.getGameNo(), memberId);
		int historyNum = historyDao.findHistory(gameInfo.getGameNo(), memberId);
		if(historyNum == 0) { //기존 이력이 없으면 추가
			historyDao.playGameHistory(history);
		}
		else { //기존 이력이 있으면 업데이트
			historyDao.updatePlayGameHistory(history);
		}
		
		return resultMap;
	}
	
	
}
