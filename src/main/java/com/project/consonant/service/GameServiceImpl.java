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

@Service
public class GameServiceImpl implements GameService{
	
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	GameDao gameDao;
	
	List<InputQuiz> quizList = new ArrayList<>(); 
	
	public List<Category> goCreateGame() {
		quizList = new ArrayList<>();
		return categoryDao.findAllCategory();
	}
	public List<InputQuiz> insertQuiz(InputQuiz inputQuiz) {
		quizList.add(inputQuiz);
		for(InputQuiz iq : quizList) {
			System.out.println(iq.getQuestion());
		}
		return quizList;
	}
	
	public List<InputQuiz> removeQuiz(String question) {
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
	
	public List<InputQuiz> getInputQuizList(){
		return quizList;
	}

	@Transactional
	public boolean createGame(CreateGameCommand createGameCommand, List<InputQuiz> inputQuizList) {
		
		Game game = new Game();
		game.setMemberId(createGameCommand.getMemberId());
		game.setGameTitle(createGameCommand.getGameTitle());
		game.setGameIntro(createGameCommand.getGameIntro());
		game.setGameDifficulty(createGameCommand.getGameDifficulty());
		game.setCategory(createGameCommand.getCategory());
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

}
