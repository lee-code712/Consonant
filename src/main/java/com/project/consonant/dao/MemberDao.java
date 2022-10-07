package com.project.consonant.dao;

import com.project.consonant.domain.Member;

public interface MemberDao {

	Member findMember(String memberId);
	
	void createMember(Member member);
}
