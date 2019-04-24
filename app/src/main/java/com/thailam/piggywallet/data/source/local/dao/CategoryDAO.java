package com.thailam.piggywallet.data.source.local.dao;

import com.thailam.piggywallet.data.model.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> getCategories() throws Exception;
}
