package com.vitaliiLyashchenko.restaurant.db;

import com.vitaliiLyashchenko.restaurant.db.entity.Dish;
import com.vitaliiLyashchenko.restaurant.exceptions.DbException;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface DishDao {

    Dish getDishById(long id) throws DbException;

    /**
     * Get all dishes from dish table in db
     * @return list of dishes
     */
    List<Dish> getAllDishes() throws DbException;

    /**
     * Get sorted dishes from category using pagination. All from dish table in db
     * @param categoryId id of category
     * @param sortBy name of field in dish table to sort by
     * @param dishesInPage how many dishes are on one page
     * @param pageNum page number
     * @return list of dishes
     */
    List<Dish> getSortedDishesFromCategoryOnPage(int categoryId, String sortBy, int dishesInPage, int pageNum) throws DbException;

    /**
     * Get sorted dishes using pagination
     * @param sortBy name of field in dish table to sort by
     * @param dishesInPage how many dishes are on one page
     * @param pageNum page number
     * @return list of dishes
     */
    List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException;

    /**
     * How many rows are in dish table
     * @return number of dishes
     */
    int getDishesNumber() throws DbException;

    /**
     * How many rows with current category in dish table
     * @param categoryId id of category
     * @return number of dishes
     */
    int getDishesNumberInCategory(int categoryId) throws DbException;

    /**
     * How many times were dish orders.
     * @return answer in view Map(dish_id, Pair(dish_name, order_count))
     */
    Map<Integer, Pair<String, Integer>> getDishesOrderCount() throws DbException;
}

