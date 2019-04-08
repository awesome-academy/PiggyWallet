package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

public class WalletRepository {
    private static WalletRepository sInstance;
    private WalletDataSource mWalletLocalDataSource;

    private WalletRepository(@NonNull WalletDataSource localDataSource) {
        mWalletLocalDataSource = localDataSource;
    }

    public static WalletRepository getInstance(@NonNull  WalletDataSource localDataSource) {
        if(sInstance == null) {
            sInstance = new WalletRepository(localDataSource);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
