package com.project.consonant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.consonant.dao.CategoryDao;
import com.project.consonant.dao.GameDao;
import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.Game;
import com.project.consonant.domain.InputQuiz;
import com.project.consonant.domain.Quiz;
import com.project.consonant.service.exception.GameException;

@Service
public class GameServiceImpl implements GameService{
	
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	GameDao gameDao;
	
	List<InputQuiz> quizList = new ArrayList<>();  //게임 생성에 필요한 퀴즈를 저장해둠
	
	public List<Category> goCreateGame() {
		quizList = new ArrayList<>();
		return categoryDao.findAllCategory();
	}
	public List<Category> getAllCategory(){	//카테고리 가져오기
		return categoryDao.findAllCategory();
	}
	public List<InputQuiz> insertQuiz(InputQuiz inputQuiz) { //게임 생성 시 퀴즈 등록
		quizList.add(inputQuiz);
		for(InputQuiz iq : quizList) {
			System.out.println(iq.getQuestion());
		}
		return quizList;
	}
	
	public List<InputQuiz> removeQuiz(String question) { //게임 생성 시 퀴즈 삭제
		for(int i = 0; i < quizList.size(); i++) {
			InputQuiz iq = quizList.get(i);
			if(iq.getQuestion().equals(question)) {
				quizList.remove(i);
				break;
			}
		}
		
		for(InputQuiz iq : quizList) {
			System.out.println(iq.getQuestion());
		}
		return quizList;
	}
	
	public void setInputQuizList(List<InputQuiz> quizList) {
		this.quizList = quizList;
	}
	public List<InputQuiz> getInputQuizList(){
		return quizList;
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
		//System.out.println("서비스실행결과:" + game.getGameNo()); //mybatis에서 selectKey로 넘겨받은 gameNo
		
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
	public List<Game> findAllGames(){
		List<Game> gameList = gameDao.findAllGames();
		for(Game g : gameList) {
			System.out.println(g.getGameTitle());
		}
		return gameDao.findAllGames();
	}
}
