package com.project.consonant.dao;

import org.apache.ibatis.annotations.Param;

import com.project.consonant.domain.History;

public interface HistoryDao {
	int playGameHistory(History history);
	int findHistory(int gameNo, String memberId);
	int updatePlayGameHistory(History history);
}
