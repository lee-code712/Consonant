package com.project.consonant.service;

import com.project.consonant.domain.Member;
import com.project.consonant.service.exception.LoginException;
import com.project.consonant.service.exception.SignUpException;

public interface MemberService {
	
	Member login(Member member) throws LoginException;
	
	void signUp(Member member) throws SignUpException;

}
