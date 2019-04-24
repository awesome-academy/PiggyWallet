package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.CategoryDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;
import com.thailam.piggywallet.data.source.local.dao.CategoryDAO;
import com.thailam.piggywallet.data.source.local.dao.CategoryDAOImpl;

import java.util.List;

public class CategoryLocalDataSource implements CategoryDataSource {
    private static CategoryLocalDataSource sInstance;
    private static CategoryDAO mCategoryDAO;

    private CategoryLocalDataSource(CategoryDAO categoryDAO) {
        mCategoryDAO = categoryDAO;
    }

    public static CategoryLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WalletLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new CategoryLocalDataSource(CategoryDAOImpl.getInstance(context));
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getCategories(@NonNull OnDataLoadedCallback<List<Category>> callback) {
        LocalAsyncTask<Void, List<Category>> task = new LocalAsyncTask<>(params -> {
            return mCategoryDAO.getCategories();
        }, callback);
        task.execute();
    }
}
