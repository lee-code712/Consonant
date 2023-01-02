package com.project.consonant.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.consonant.domain.Member;

@Mapper
public interface MemberMapper {

	Member selectMemberById(String memberId);
	
	void insertMember(Member member);
	
	void updatePoint(@Param("memberId") String memberId, @Param("point") int point);
	
	void updateTotalScore(@Param("memberId") String memberId, @Param("score") int addScore);
	
}
