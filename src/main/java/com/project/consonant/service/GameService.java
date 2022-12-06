package com.project.consonant.service;

import java.util.List;

import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.InputQuiz;

public interface GameService {
	List<Category> goCreateGame();
	boolean createGame(CreateGameCommand createGameCommand, List<InputQuiz> inputQuizList);
	List<InputQuiz> insertQuiz(InputQuiz inputQuiz);
	List<InputQuiz> removeQuiz(String inputQuiz);
	List<InputQuiz> getInputQuizList();
}
