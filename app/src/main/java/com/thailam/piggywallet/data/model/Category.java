package com.thailam.piggywallet.data.model;

import java.util.Date;

public class Category {
    private int mId;
    private String mName;
    private Date mCreatedAt;
    private Date mUpdatedAt;

    public Category(int id, String name, Date createdAt, Date updatedAt) {
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

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }
}
