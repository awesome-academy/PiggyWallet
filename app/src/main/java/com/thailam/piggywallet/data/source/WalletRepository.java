package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletRepository implements WalletDataSource {

    private static WalletRepository sInstance;

    private WalletDataSource mWalletLocalDataSource;

    private List<Wallet> mCachedWallets;

    private WalletRepository(@NonNull WalletDataSource localDataSource) {
        mWalletLocalDataSource = localDataSource;
    }

    public static WalletRepository getInstance(@NonNull WalletDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new WalletRepository(localDataSource);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getInitialWallets(@NonNull final GetWalletCallback callback) {
        if (mCachedWallets != null) { // if data is in cache, return immediately w/ that
            callback.onDataLoaded(mCachedWallets);
            return;
        }
        mWalletLocalDataSource.getInitialWallets(new GetWalletCallback() {
            @Override
            public void onDataLoaded(List<Wallet> wallets) {
                refreshCache(wallets);
                callback.onDataLoaded(mCachedWallets);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                // TODO: implement later
            }
        });
    }

    @Override
    public boolean addWallet(Wallet wallet) {
        // TODO: implement at add wallet task
        return false;
    }

    private void refreshCache(List<Wallet> wallets) {
        if (mCachedWallets == null) {
            mCachedWallets = new ArrayList<>(wallets);
            return;
        }
        mCachedWallets.clear();
        mCachedWallets = wallets;
    }
}
