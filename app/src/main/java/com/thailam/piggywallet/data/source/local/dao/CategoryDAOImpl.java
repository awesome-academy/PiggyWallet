package com.thailam.piggywallet.data.source.local.dao;

import android.content.Context;
import android.database.Cursor;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.local.AppDatabaseHelper;
import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl extends AppDatabaseHelper implements CategoryDAO {
    private static CategoryDAOImpl sInstance;

    private CategoryDAOImpl(Context context) {
        super(context);
    }

    public static CategoryDAOImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new CategoryDAOImpl(context);
                }
            }
        }
        return sInstance;
    }

    static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public List<Category> getCategories() throws Exception {
        initReadDatabase();
        Cursor cursor = mDatabase.query(
                true, CategoryEntry.TBL_NAME_CATE, null,
                null, null, null,
                null, null, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        List<Category> categories = new ArrayList<>();
        while (cursor.moveToNext()) { // check if there are any left
            categories.add(new Category(cursor));
        }
        cursor.close();
        closeDatabase();
        return categories;
    }
}
