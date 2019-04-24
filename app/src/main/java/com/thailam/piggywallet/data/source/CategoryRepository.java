package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements CategoryDataSource {
    private static CategoryRepository sInstance;
    private CategoryDataSource mCategoryLocalDataSource;
    private List<Category> mCachedCategories;

    private CategoryRepository(@NonNull CategoryDataSource categoryLocalDataSource) {
        mCategoryLocalDataSource = categoryLocalDataSource;
    }

    public static CategoryRepository getInstance(@NonNull CategoryDataSource categoryLocalDataSource) {
        if (sInstance == null) {
            sInstance = new CategoryRepository(categoryLocalDataSource);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getCategories(@NonNull OnDataLoadedCallback<List<Category>> callback) {
        if (mCachedCategories != null) {
            callback.onDataLoaded(mCachedCategories);
            return;
        }
        mCategoryLocalDataSource.getCategories(new OnDataLoadedCallback<List<Category>>() {
            @Override
            public void onDataLoaded(List<Category> categories) {
                refreshCategoriesCache(categories);
                callback.onDataLoaded(mCachedCategories);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    private void refreshCategoriesCache(List<Category> categories) {
        if (mCachedCategories == null) {
            mCachedCategories = new ArrayList<>(categories);
            return;
        }
        mCachedCategories.clear();
        mCachedCategories.addAll(categories);
    }
}
