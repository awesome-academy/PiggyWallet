package com.thailam.piggywallet.data.model;

public class WalletTransaction {
    private int mId;
    private int mWalletId;
    private int mTransactionId;

    public WalletTransaction(int id, int walletId, int transactionId) {
        mId = id;
        mWalletId = walletId;
        mTransactionId = transactionId;
    }

    public void setWalletId(int walletId) {
        mWalletId = walletId;
    }

    public void setTransactionId(int transactionId) {
        mTransactionId = transactionId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getWalletId() {
        return mWalletId;
    }

    public int getTransactionId() {
        return mTransactionId;
    }
}
