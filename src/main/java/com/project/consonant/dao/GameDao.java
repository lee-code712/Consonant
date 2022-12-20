package com.project.consonant.dao;

import com.project.consonant.domain.Game;
import com.project.consonant.domain.Quiz;

public interface GameDao {
	int createGame(Game game);
	int createQuiz(Quiz quiz);
}	
