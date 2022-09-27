package com.project.consonant.service;

import com.project.consonant.domain.Member;
import com.project.consonant.service.exception.LoginException;

public interface MemberService {
	
	Member login(Member member) throws LoginException;

}
