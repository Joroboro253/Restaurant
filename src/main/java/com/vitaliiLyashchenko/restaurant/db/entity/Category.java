package com.vitaliiLyashchenko.restaurant.db.entity;

import java.io.Serializable;

/**
 * Entity class for category table in database
 */
public class Category implements Serializable {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        Category category = new Category();

        public Category getCategory() {
            return category;
        }

        public Builder setId(long id) {
            category.id = id;
            return this;
        }

        public Builder setName(String name) {
            category.name = name;
            return this;
        }
    }
}
