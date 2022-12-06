package com.project.consonant.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.consonant.domain.Category;

@Mapper
public interface CategoryMapper {
	public List<Category> findAllCategory();
}
