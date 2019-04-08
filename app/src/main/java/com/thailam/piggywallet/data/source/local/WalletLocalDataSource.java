package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import com.thailam.piggywallet.data.source.WalletDataSource;

public class WalletLocalDataSource implements WalletDataSource {

    private static WalletLocalDataSource sInstance;
    private static AppDatabaseHelper mAppDatabaseHelper;

    private WalletLocalDataSource(Context context) {
        mAppDatabaseHelper = new AppDatabaseHelper(context);
    }

    public static WalletLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WalletLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new WalletLocalDataSource(context);
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
