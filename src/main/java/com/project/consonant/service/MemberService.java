package com.project.consonant.service;

import com.project.consonant.domain.Member;
import com.project.consonant.service.exception.LoginException;
import com.project.consonant.service.exception.SignUpException;

public interface MemberService {
	
	Member login(Member member) throws LoginException;
	
	void signUp(Member member) throws SignUpException;
	
	Member findMember(String memberId);
	
	void updatePoint(String memberId, int point, int status);
	
	void updateTotalScoreAndRankings(String memberId, int addScore);

}
