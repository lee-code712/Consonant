package com.project.consonant.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.project.consonant.dao.MemberDao;
import com.project.consonant.dao.mybatis.mapper.MemberMapper;
import com.project.consonant.domain.Member;

@Repository
public class MybatisMemberDao implements MemberDao {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public Member findMember(String memberId) throws DataAccessException {
		return memberMapper.selectMemberById(memberId);
	}
	
	@Override
	public void createMember(Member member) throws DataAccessException {
		memberMapper.insertMember(member);
	}

	@Override
	public void updatePoint(String memberId, int point, int status) {
		if (status == 1) {
			memberMapper.updatePoint(memberId, point);
		} else if (status == -1) {
			memberMapper.updatePoint(memberId, -point);
		}
	}

	@Override
	public void updateTotalScore(String memberId, int addScore) {
		memberMapper.updateTotalScore(memberId, addScore);
	}
}
