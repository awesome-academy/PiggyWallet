package com.thailam.piggywallet.data.model;

import android.database.Cursor;

import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;

public class Category {
    private int mId;
    private String mName;
    private long mCreatedAt;
    private long mUpdatedAt;

    public Category(int id, String name, long createdAt, long updatedAt) {
        mId = id;
        mName = name;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public Category(Cursor cursor) {
        int indexId = cursor.getColumnIndex(CategoryEntry.ID);
        int indexName = cursor.getColumnIndex(CategoryEntry.NAME);
        int indexCreatedAt = cursor.getColumnIndex(CategoryEntry.CREATED_AT);
        int indexUpdatedAt = cursor.getColumnIndex(CategoryEntry.UPDATED_AT);
        // get data from column index
        mId = cursor.getInt(indexId);
        mName = cursor.getString(indexName);
        mCreatedAt = cursor.getLong(indexCreatedAt);
        mUpdatedAt = cursor.getLong(indexUpdatedAt);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    public long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        mUpdatedAt = updatedAt;
    }
}
