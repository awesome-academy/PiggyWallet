package com.thailam.piggywallet.data.model;

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
