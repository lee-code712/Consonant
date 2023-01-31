package com.project.consonant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.Game;
import com.project.consonant.domain.GameInfoVO;
import com.project.consonant.domain.InputQuiz;
import com.project.consonant.domain.Quiz;
import com.project.consonant.service.exception.GameException;

public interface GameService {
	List<Category> goCreateGame();
	List<Category> getAllCategory();
	boolean createGame(CreateGameCommand createGameCommand, List<InputQuiz> inputQuizList) throws GameException;
	List<InputQuiz> insertQuiz(List<InputQuiz> inputQuizList, InputQuiz inputQuiz);
	List<InputQuiz> removeQuiz(List<InputQuiz> inputQuizList, String inputQuiz);

	List<Game> findAllGames(String memberId);
	List<Game> findAllGamesByCategory(String memberId, String categoryId);
	GameInfoVO findGame(int gameNo);
	//List<Quiz> getPlayGameQuiz();
	//void setPlayGameQuiz(List<Quiz> playGameQuiz);
	//Game getGameInfo();
	//void setGameInfo(Game gameInfo);
	//Map<Integer, String> getUserAnswer();
	//void setUserAnswer(Map<Integer, String> userAnswer);
	Map<String, Integer> gameResult(List<Quiz> playGameQuiz, HashMap<Integer, String> userAnswer, Game gameInfo, String memberId);
	//String[] getResultArray();
	//void setResultArray(String[] resultArray);
}
