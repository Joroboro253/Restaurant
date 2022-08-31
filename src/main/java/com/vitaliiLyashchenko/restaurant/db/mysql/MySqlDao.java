package com.vitaliiLyashchenko.restaurant.db.mysql;

import com.vitaliiLyashchenko.restaurant.db.*;

public class MySqlDao extends Dao {

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }

    @Override
    public DishDao getDishDao() {
        return new MySqlDishDao();
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new MySqlCategoryDao();
    }

    @Override
    public CartDao getCartDao() {
        return new MySqlCartDao();
    }

    @Override
    public ReceiptDao getReceiptDao() {
        return new MySqlReceiptDao();
    }
}
