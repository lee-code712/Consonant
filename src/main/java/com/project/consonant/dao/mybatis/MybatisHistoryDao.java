package com.project.consonant.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.consonant.dao.HistoryDao;
import com.project.consonant.dao.mybatis.mapper.HistoryMapper;
import com.project.consonant.domain.History;

@Repository
public class MybatisHistoryDao implements HistoryDao{
	@Autowired
	HistoryMapper historyMapper;
	
	@Override
	public int playGameHistory(History history) {
		return historyMapper.playGameHistory(history);
	}

	@Override
	public int findHistory(int gameNo, String memberId) {
		
		return historyMapper.findHistory(gameNo, memberId);
	}

	@Override
	public int updatePlayGameHistory(History history) {
		
		return historyMapper.updatePlayGameHistory(history);
	}
	
	
}
