package com.thailam.piggywallet.ui.category;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.CategoryDataSource;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;
import com.thailam.piggywallet.data.source.local.CategoryLocalDataSource;
import com.thailam.piggywallet.util.Constants;

import java.util.List;

public class CategoryPresenter implements CategoryContract.Presenter, OnDataLoadedCallback<List<Category>> {
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
        mView.showProgressBar();
        mCategoryRepository.getCategories(this);
    }

    @Override
    public void onDataLoaded(List<Category> categories) {
        mView.hideProgressBar();
        mView.updateCategories(categories);
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        mView.hideProgressBar();
        mView.showError(e);
    }
}
