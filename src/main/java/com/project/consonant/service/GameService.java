package com.project.consonant.service;

import java.util.List;

import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.Game;
import com.project.consonant.domain.InputQuiz;
import com.project.consonant.service.exception.GameException;

public interface GameService {
	List<Category> goCreateGame();
	List<Category> getAllCategory();
	boolean createGame(CreateGameCommand createGameCommand, List<InputQuiz> inputQuizList) throws GameException;
	List<InputQuiz> insertQuiz(InputQuiz inputQuiz);
	List<InputQuiz> removeQuiz(String inputQuiz);
	List<InputQuiz> getInputQuizList();
	void setInputQuizList(List<InputQuiz> quizList);
	
	List<Game> findAllGames();
}
