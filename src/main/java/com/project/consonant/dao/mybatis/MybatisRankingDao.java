package com.project.consonant.dao.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.consonant.dao.RankingDao;
import com.project.consonant.dao.mybatis.mapper.RankingMapper;
import com.project.consonant.domain.Member;

@Repository
public class MybatisRankingDao implements RankingDao {

	@Autowired
	private RankingMapper rankingMapper;

	@Override
	public List<Member> findTopRankings() {
		return rankingMapper.selectTop10Rankings();
	}
	
}
