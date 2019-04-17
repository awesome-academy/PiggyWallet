package com.thailam.piggywallet.data.model;

import java.util.List;

public class TransactionParent {
    private List<Transaction> mTransactions;
    private long mDate;

    public TransactionParent(List<Transaction> transactions, long date) {
        mTransactions = transactions;
        mDate = date;
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
