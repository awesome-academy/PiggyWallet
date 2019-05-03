package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

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
    public void getInitialWallets(boolean force, @NonNull final GetWalletCallback callback) {
        if (mCachedWallets != null && !force) { // if data is in cache, return immediately w/ that
            callback.onDataLoaded(mCachedWallets);
            return;
        }
        mWalletLocalDataSource.getInitialWallets(force, new GetWalletCallback() {
            @Override
            public void onDataLoaded(List<Wallet> wallets) {
                refreshWalletsCache(wallets);
                callback.onDataLoaded(mCachedWallets);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    @Override
    public void getSearchedWallets(String input, @NonNull GetWalletCallback callback) {
        mWalletLocalDataSource.getSearchedWallets(input, callback);
    }

    @Override
    public void saveWallet(Wallet wallet, @NonNull OnDataLoadedCallback<Long> callback) {
        mWalletLocalDataSource.saveWallet(wallet, new OnDataLoadedCallback<Long>() {
            @Override
            public void onDataLoaded(Long data) {
                refreshWalletsCache(null);
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    @Override
    public List<Wallet> getCachedWallets() {
        return mCachedWallets;
    }

    @Override
    public boolean putWalletToPrefs(Wallet wallet) {
        return mWalletLocalDataSource.putWalletToPrefs(wallet);
    }

    @Override
    public Wallet getWalletFromPrefs() {
        return mWalletLocalDataSource.getWalletFromPrefs();
    }

    public void deleteWallet(Wallet wallet, @NonNull OnDataLoadedCallback<Integer> callback) {
        mWalletLocalDataSource.deleteWallet(wallet, callback);
    }

    private void refreshWalletsCache(List<Wallet> wallets) {
        if (mCachedWallets == null) {
            mCachedWallets = new ArrayList<>(wallets);
            return;
        }
        mCachedWallets.clear();
        mCachedWallets.addAll(wallets);
    }
}
