package com.thailam.piggywallet.data.model;

public class Transaction {
    private int mId;
    private String mNote;
    private double mAmount;
    private int mCategoryId;
    private long mDate;

    public Transaction(int id, String note, double amount, int categoryId, long date) {
        mId = id;
        mNote = note;
        mAmount = amount;
        mCategoryId = categoryId;
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
