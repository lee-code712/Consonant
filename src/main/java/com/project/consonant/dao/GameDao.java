package com.project.consonant.dao;

import java.util.List;

import com.project.consonant.domain.Game;
import com.project.consonant.domain.Quiz;

public interface GameDao {
	int createGame(Game game);
	int createQuiz(Quiz quiz);
	List<Game> findAllGames(String memberId);
	List<Game> findAllGamesByCategory(String memberId, String categoryId);
}	
