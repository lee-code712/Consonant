package com.project.consonant.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.consonant.domain.History;

@Mapper
public interface HistoryMapper {
	public int playGameHistory(History history);
	public int findHistory(@Param("gameNo") int gameNo, @Param("memberId") String memberId);
	public int updatePlayGameHistory(History history);
}
