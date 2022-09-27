package com.project.consonant.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.consonant.domain.Member;

@Mapper
public interface MemberMapper {

	Member selectMemberById(String memberId);
	
}
