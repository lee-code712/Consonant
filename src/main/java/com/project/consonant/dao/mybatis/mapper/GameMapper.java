package com.project.consonant.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.consonant.domain.Game;
import com.project.consonant.domain.Quiz;

@Mapper
public interface GameMapper {
	public int createGame(Game game);
	public int createQuiz(Quiz quiz);
	public List<Game> findAllGames();
}
