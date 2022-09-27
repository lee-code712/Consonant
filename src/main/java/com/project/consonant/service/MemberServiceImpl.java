package com.project.consonant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consonant.dao.MemberDao;
import com.project.consonant.domain.Member;
import com.project.consonant.service.exception.LoginException;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public Member login(Member member) throws LoginException {
		
		Member findMember = memberDao.findMember(member.getMemberId());

		if (findMember == null) {
			throw new LoginException("존재하지 않는 회원입니다.");
		}
		if (!member.getPasswd().equals(findMember.getPasswd())) {
			throw new LoginException("비밀번호가 일치하지 않습니다.");
		}
		
		return findMember;
	}

}
