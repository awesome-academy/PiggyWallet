package com.thailam.piggywallet.data.model;

import java.util.Date;

public class User {
    private int mId;
    private String mEmail;
    private String mPassword;
    private Date mCreatedAt;
    private Date mUpdatedAt;

    public User(int id, String email, String password, Date createdAt, Date updatedAt) {
        mId = id;
        mEmail = email;
        mPassword = password;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
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
