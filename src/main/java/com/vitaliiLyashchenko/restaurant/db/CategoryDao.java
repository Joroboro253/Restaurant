package com.vitaliiLyashchenko.restaurant.db;

import com.vitaliiLyashchenko.restaurant.db.entity.Category;
import com.vitaliiLyashchenko.restaurant.exceptions.DbException;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories() throws DbException;
}
