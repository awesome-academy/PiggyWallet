package com.thailam.piggywallet.data.model;

import android.database.Cursor;

import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;

public class Category {
    private int mId;
    private String mName;
    private String mType;
    private long mCreatedAt;
    private long mUpdatedAt;

    public Category(int id, String name, String type, long createdAt, long updatedAt) {
        mId = id;
        mName = name;
        mType = type;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public Category(Cursor cursor) {
        int indexId = cursor.getColumnIndex(CategoryEntry.ID);
        int indexName = cursor.getColumnIndex(CategoryEntry.NAME);
        int indexType = cursor.getColumnIndex(CategoryEntry.TYPE);
        int indexCreatedAt = cursor.getColumnIndex(CategoryEntry.CREATED_AT);
        int indexUpdatedAt = cursor.getColumnIndex(CategoryEntry.UPDATED_AT);
        // get data from column index
        mId = cursor.getInt(indexId);
        mName = cursor.getString(indexName);
        mType = cursor.getString(indexType);
        mCreatedAt = cursor.getLong(indexCreatedAt);
        mUpdatedAt = cursor.getLong(indexUpdatedAt);
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
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
