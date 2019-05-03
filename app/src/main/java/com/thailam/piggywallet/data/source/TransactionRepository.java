package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.TransactionParent;
import com.thailam.piggywallet.data.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements TransactionDataSource {
    private static TransactionRepository sInstance;
    private TransactionDataSource mTransactionLocalDataSource;
    private List<TransactionParent> mCachedTransactionParents;
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
        if (mCachedTransactionParents != null && mCachedWalletId == walletId) {
            callback.onDataLoaded(mCachedTransactionParents);
            return;
        }

        mTransactionLocalDataSource.getInitialTransactions(walletId, new GetTransactionCallback() {
            @Override
            public void onDataLoaded(List<TransactionParent> transactionParents) {
                mCachedWalletId = walletId;
                refreshCachedTransactions(transactionParents);
                callback.onDataLoaded(mCachedTransactionParents);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    private void refreshCachedTransactions(List<TransactionParent> transactionParents) {
        if (mCachedTransactionParents == null) {
            mCachedTransactionParents = new ArrayList<>(transactionParents);
            return;
        }
        mCachedTransactionParents.clear();
        mCachedTransactionParents.addAll(transactionParents);
    }
}
