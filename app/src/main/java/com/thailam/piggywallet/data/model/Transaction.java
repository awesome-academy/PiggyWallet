package com.thailam.piggywallet.data.model;

import android.database.Cursor;

import com.thailam.piggywallet.data.source.local.entry.TransactionEntry;

public class Transaction {
    private int mId;
    private String mNote;
    private double mAmount;
    private int mCategoryId;
    private long mDate;
    private int mWalletId;

    private Transaction(Builder builder) {
        mId = builder.mId;
        mNote = builder.mNote;
        mAmount = builder.mAmount;
        mCategoryId = builder.mCategoryId;
        mDate = builder.mDate;
        mWalletId = builder.mWalletId;
    }

    public Transaction(Cursor cursor) {
        int indexId = cursor.getColumnIndex(TransactionEntry.ID);
        int indexNote = cursor.getColumnIndex(TransactionEntry.NOTE);
        int indexAmount = cursor.getColumnIndex(TransactionEntry.AMOUNT);
        int indexCategoryId = cursor.getColumnIndex(TransactionEntry.FOR_CAT_ID);
        int indexWalletId = cursor.getColumnIndex(TransactionEntry.FOR_WALLET_ID);
        int indexDate = cursor.getColumnIndex(TransactionEntry.DATE);
        // get data from column index
        mId = cursor.getInt(indexId);
        mNote = cursor.getString(indexNote);
        mAmount = cursor.getDouble(indexAmount);
        mCategoryId = cursor.getInt(indexCategoryId);
        mWalletId = cursor.getInt(indexWalletId);
        mDate = cursor.getLong(indexDate);
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

    public int getWalletId() {
        return mWalletId;
    }

    public void setWalletId(int walletId) {
        mWalletId = walletId;
    }

    public static class Builder {
        private int mId;
        private String mNote;
        private double mAmount;
        private int mCategoryId;
        private long mDate;
        private int mWalletId;


        public Builder(int id) {
            mId = id;
        }

        public Builder setNote(String note) {
            mNote = note;
            return this;
        }

        public Builder setAmount(double amount) {
            mAmount = amount;
            return this;
        }

        public Builder setCategoryId(int categoryId) {
            mCategoryId = categoryId;
            return this;
        }

        public Builder setDate(long date) {
            mDate = date;
            return this;
        }

        public Builder setWalletId(int walletId) {
            mWalletId = walletId;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
