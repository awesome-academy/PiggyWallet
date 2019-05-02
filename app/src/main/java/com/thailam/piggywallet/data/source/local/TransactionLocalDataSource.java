package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.local.dao.TransactionDAO;
import com.thailam.piggywallet.data.source.local.dao.TransactionDAOImpl;
import com.thailam.piggywallet.data.source.local.dao.WalletDAO;
import com.thailam.piggywallet.data.source.local.dao.WalletDAOImpl;

import java.util.List;

public class TransactionLocalDataSource implements TransactionDataSource {

    private static TransactionLocalDataSource sInstance;
    private TransactionDAO mTransactionDAO;
    private WalletDAO mWalletDAO;

    private TransactionLocalDataSource(TransactionDAO transactionDAO, WalletDAO walletDAO) {
        mTransactionDAO = transactionDAO;
        mWalletDAO = walletDAO;
    }

    public static TransactionLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TransactionLocalDataSource(TransactionDAOImpl.getInstance(context),
                    WalletDAOImpl.getInstance(context));
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

    @Override
    public boolean saveWalletToSharedPref(Wallet wallet) {
        return mWalletDAO.saveWalletToSharedPref(wallet);
    }
}
