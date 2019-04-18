package com.thailam.piggywallet.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.CategoryDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryLocalDataSource implements CategoryDataSource {
    private static CategoryLocalDataSource sInstance;
    private static AppDatabaseHelper mAppDatabaseHelper;

    private CategoryLocalDataSource(Context context) {
        mAppDatabaseHelper = AppDatabaseHelper.getInstance(context);
    }

    public static CategoryLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WalletLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new CategoryLocalDataSource(context);
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getCategories(@NonNull GetCategoryCallback callback) {
        LocalAsyncTask<Void, List<Category>> task = new LocalAsyncTask<>(params -> {
            SQLiteDatabase db = mAppDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(
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
            db.close();
            return categories;
        }, callback);
        task.execute();
    }
}
