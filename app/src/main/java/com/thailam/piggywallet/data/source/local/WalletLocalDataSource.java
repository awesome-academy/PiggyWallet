package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;
import com.thailam.piggywallet.data.source.local.dao.WalletDAO;
import com.thailam.piggywallet.data.source.local.dao.WalletDAOImpl;

import java.util.List;

public class WalletLocalDataSource implements WalletDataSource {

    private static WalletLocalDataSource sInstance;
    private WalletDAO mWalletDAO;

    private WalletLocalDataSource(WalletDAO walletDAO) {
        mWalletDAO = walletDAO;
    }

    public static WalletLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WalletLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new WalletLocalDataSource(WalletDAOImpl.getInstance(context));
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getInitialWallets(@NonNull final GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.getInitialWallets();
        }, callback);
        task.execute();
    }

    @Override
    public void getSearchedWallets(String input, @NonNull final GetWalletCallback callback) {
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
}
