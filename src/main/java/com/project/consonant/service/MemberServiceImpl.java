package com.project.consonant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.consonant.dao.MemberDao;
import com.project.consonant.domain.Member;
import com.project.consonant.service.exception.LoginException;
import com.project.consonant.service.exception.SignUpException;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private RankingService rankingSvc;
	
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

	@Transactional
	@Override
	public void signUp(Member member) throws SignUpException {
		if (memberDao.findMember(member.getMemberId()) != null) {
			throw new SignUpException("이미 등록된 회원 ID 입니다.");
		}
		
		memberDao.createMember(member);
	}
	
	@Override
	public Member findMember(String memberId) {
		return memberDao.findMember(memberId);
	}

	@Override
	public void updatePoint(String memberId, int point, int status) {
		memberDao.updatePoint(memberId, point, status);
	}

	@Transactional
	@Override
	public void updateTotalScoreAndRankings(String memberId, int addScore) {
		// total score 갱신
		memberDao.updateTotalScore(memberId, addScore);
		
		// 전체 ranking 갱신
		rankingSvc.updateRankings();
	}

}
