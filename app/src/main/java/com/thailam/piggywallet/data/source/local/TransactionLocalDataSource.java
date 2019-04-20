package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;

public class TransactionLocalDataSource implements TransactionDataSource {

    private static TransactionLocalDataSource sInstance;
    private static DatabaseDAO mDatabaseDAO;

    private TransactionLocalDataSource(DatabaseDAO databaseDAO) {
        mDatabaseDAO = databaseDAO;
    }

    public static TransactionLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TransactionLocalDataSource(AppDatabaseHelper.getInstance(context));
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void saveTransaction(Transaction transaction, @NonNull TransactionCallback callback) {
        mDatabaseDAO.saveTransaction(transaction, callback);
    }

    @Override
    public void getInitialTransactions(int walletId, @NonNull GetTransactionCallback callback) {
        mDatabaseDAO.getInitialTransactions(walletId, callback);
    }
}
