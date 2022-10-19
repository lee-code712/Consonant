package com.project.consonant.dao;

import java.util.List;

import com.project.consonant.domain.Member;

public interface RankingDao {

	List<Member> findTopRankings();
	
}
