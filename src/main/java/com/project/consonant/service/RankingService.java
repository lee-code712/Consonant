package com.project.consonant.service;

import java.util.List;

import com.project.consonant.domain.Member;

public interface RankingService {

	List<Member> getTopRankings();
	
	void updateRankings();
	
}
