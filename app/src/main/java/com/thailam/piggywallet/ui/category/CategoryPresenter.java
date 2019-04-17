package com.thailam.piggywallet.ui.category;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.CategoryDataSource;
import com.thailam.piggywallet.data.source.local.CategoryLocalDataSource;
import com.thailam.piggywallet.util.Constants;

import java.util.List;

public class CategoryPresenter implements CategoryContract.Presenter, CategoryLocalDataSource.GetCategoryCallback {
    @NonNull
    private CategoryContract.View mView;
    @NonNull
    private CategoryDataSource mCategoryRepository;

    CategoryPresenter(@NonNull CategoryContract.View view,
                      @NonNull CategoryDataSource categoryRepository) {
        mView = view;
        mCategoryRepository = categoryRepository;
    }

    @Override
    public void start() {
        getCategories();
    }

    @Override
    public void getCategories() {
        mView.toggleRefreshing();
        mCategoryRepository.getCategories(this);
    }

    @Override
    public void onDataLoaded(List<Category> categories) {
        mView.toggleRefreshing();
        mView.updateCategories(categories);
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        String msg = (e == null) ? Constants.NO_DATA_ERROR : e.getMessage();
        mView.toggleRefreshing();
        mView.showError(msg);
    }
}
