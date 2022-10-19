package com.project.consonant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consonant.dao.RankingDao;
import com.project.consonant.domain.Member;

@Service
public class RankingServiceImpl implements RankingService {

	@Autowired
	private RankingDao rankingDao;

	@Override
	public List<Member> getTopRankings() {
		return rankingDao.findTopRankings();
	}
	
}
