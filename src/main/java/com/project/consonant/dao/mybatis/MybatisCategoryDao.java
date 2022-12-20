package com.project.consonant.dao.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.consonant.dao.CategoryDao;
import com.project.consonant.dao.mybatis.mapper.CategoryMapper;
import com.project.consonant.domain.Category;

@Repository
public class MybatisCategoryDao implements CategoryDao{

	@Autowired
	CategoryMapper categoryMapper;
	
	@Override
	public List<Category> findAllCategory() {
		return categoryMapper.findAllCategory();
	}

}
