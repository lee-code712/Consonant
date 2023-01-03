package com.project.consonant.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.consonant.domain.Game;
import com.project.consonant.domain.GameInfoVO;
import com.project.consonant.domain.Quiz;

@Mapper
public interface GameMapper {
	public int createGame(Game game);
	public int createQuiz(Quiz quiz);
	public List<Game> findAllGames(String memberId);
	public List<Game> findAllGamesByCategory(@Param("memberId") String memberId, @Param("categoryId") String categoryId);
	public GameInfoVO findGame(int gameNo);
}
