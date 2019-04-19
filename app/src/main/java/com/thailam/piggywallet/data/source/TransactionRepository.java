package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements TransactionDataSource {

    private static TransactionRepository sInstance;
    private TransactionDataSource mTransactionLocalDataSource;
    private List<Transaction> mCachedTransactions;

    public TransactionRepository(@NonNull TransactionDataSource localDataSource) {
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
    public void saveTransaction(Transaction transaction, @NonNull SaveTransactionCallback callback) {
        mTransactionLocalDataSource.saveTransaction(transaction, callback);
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
