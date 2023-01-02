package com.project.consonant.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.consonant.domain.Member;

@Mapper
public interface RankingMapper {

	List<Member> selectTop10Rankings();

	void updateRankingByTotalScore();
	
}
