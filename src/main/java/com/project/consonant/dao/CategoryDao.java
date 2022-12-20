package com.project.consonant.dao;

import java.util.List;

import com.project.consonant.domain.Category;

public interface CategoryDao {
	List<Category> findAllCategory();
}
