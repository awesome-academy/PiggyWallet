package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.CategoryDataSource;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;
import com.thailam.piggywallet.data.source.local.dao.CategoryDAO;
import com.thailam.piggywallet.data.source.local.dao.CategoryDAOImpl;
import com.thailam.piggywallet.data.source.local.dao.TransactionDAO;
import com.thailam.piggywallet.data.source.local.dao.TransactionDAOImpl;
import com.thailam.piggywallet.data.source.local.dao.WalletDAO;
import com.thailam.piggywallet.data.source.local.dao.WalletDAOImpl;

import java.util.List;

public class AppLocalDataSource implements CategoryDataSource, TransactionDataSource, WalletDataSource {

    private static AppLocalDataSource sInstance;
    private static WalletDAO mWalletDAO;
    private static CategoryDAO mCategoryDAO;
    private static TransactionDAO mTransactionDAO;

    private AppLocalDataSource(WalletDAO walletDAO, CategoryDAO categoryDAO, TransactionDAO transactionDAO) {
        mWalletDAO = walletDAO;
        mCategoryDAO = categoryDAO;
        mTransactionDAO = transactionDAO;
    }

    public static AppLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new AppLocalDataSource(
                            WalletDAOImpl.getInstance(context),
                            CategoryDAOImpl.getInstance(context),
                            TransactionDAOImpl.getInstance(context));
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getCategories(@NonNull GetCategoryCallback callback) {
        LocalAsyncTask<Void, List<Category>> task = new LocalAsyncTask<>(params -> {
            return mCategoryDAO.getCategories();
        }, callback);
        task.execute();
    }

    @Override
    public void saveTransaction(Wallet wallet, Transaction transaction, @NonNull TransactionCallback callback) {
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
    public void getInitialWallets(@NonNull GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.getInitialWallets();
        }, callback);
        task.execute();
    }

    @Override
    public void getSearchedWallets(String input, @NonNull GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.getSearchedWallets(input);
        }, callback);
        task.execute();
    }

    @Override
    public void saveWallet(Wallet wallet, @NonNull OnDataLoadedCallback<Long> callback) {
        LocalAsyncTask<Void, Long> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.saveWallet(wallet);
        }, callback);
        task.execute();
    }

    @Override
    public List<Wallet> getCachedWallets() {
        return null;
    }

    @Override
    public Wallet getWalletFromSharedPref() {
        return mWalletDAO.getWalletFromSharedPref();
    }

    @Override
    public void saveWalletToSharedPref(Wallet wallet) {
        mWalletDAO.saveWalletToSharedPref(wallet);
    }
}
