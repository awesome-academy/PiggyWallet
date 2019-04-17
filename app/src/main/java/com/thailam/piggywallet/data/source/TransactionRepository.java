package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements TransactionDataSource {

    private static TransactionRepository sInstance;
    private TransactionDataSource mTransactionLocalDataSource;
    private List<Transaction> mCachedTransactions;
    private int mCachedWalletId;

    private TransactionRepository(@NonNull TransactionDataSource localDataSource) {
        mTransactionLocalDataSource = localDataSource;
    }

    public static TransactionRepository getInstance(@NonNull TransactionDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new TransactionRepository(localDataSource);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void saveTransaction(Wallet wallet, Transaction transaction, @NonNull TransactionCallback callback) {
        mTransactionLocalDataSource.saveTransaction(wallet, transaction, callback);
    }

    @Override
    public void getInitialTransactions(int walletId, @NonNull GetTransactionCallback callback) {
        if (mCachedTransactions != null && mCachedWalletId == walletId) {
            callback.onDataLoaded(mCachedTransactions);
            return;
        }

        mTransactionLocalDataSource.getInitialTransactions(walletId, new GetTransactionCallback() {
            @Override
            public void onDataLoaded(List<Transaction> transactions) {
                mCachedWalletId = walletId;
                refreshCachedTransactions(transactions);
                callback.onDataLoaded(mCachedTransactions);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    private void refreshCachedTransactions(List<Transaction> transactions) {
        if (mCachedTransactions == null) {
            mCachedTransactions = new ArrayList<>(transactions);
            return;
        }
        mCachedTransactions.clear();
        mCachedTransactions.addAll(transactions);
    }
}
