package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.local.dao.TransactionDAO;
import com.thailam.piggywallet.data.source.local.dao.TransactionDAOImpl;

import java.util.List;

public class TransactionLocalDataSource implements TransactionDataSource {

    private static TransactionLocalDataSource sInstance;
    private TransactionDAO mTransactionDAO;

    private TransactionLocalDataSource(TransactionDAO transactionDAO) {
        mTransactionDAO = transactionDAO;
    }

    public static TransactionLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TransactionLocalDataSource(TransactionDAOImpl.getInstance(context));
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void saveTransaction(Wallet wallet, Transaction transaction,
                                @NonNull TransactionCallback callback) {
        LocalAsyncTask<Void, Long> task = new LocalAsyncTask<>(params -> {
            return mTransactionDAO.saveTransaction(wallet, transaction);
        }, callback);
        task.execute();
    }

    @Override
    public void getInitialTransactions(int walletId, @NonNull GetTransactionCallback callback) {
        LocalAsyncTask<Void, List<Transaction>> task = new LocalAsyncTask<>(params -> {
            return mTransactionDAO.getInitialTransactions(walletId);
        }, callback);
        task.execute();
    }
}
