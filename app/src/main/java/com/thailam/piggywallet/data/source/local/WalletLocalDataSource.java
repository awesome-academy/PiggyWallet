package com.thailam.piggywallet.data.source.local;

import android.content.Context;

import com.thailam.piggywallet.data.source.WalletDataSource;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.base.DataHandler;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

import java.util.ArrayList;
import java.util.List;

public class WalletLocalDataSource implements WalletDataSource {

    private static WalletLocalDataSource sInstance;
    private static AppDatabaseHelper mAppDatabaseHelper;
    private static final String QUERY_LIMIT = "5";

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

    @Override
    public void getInitialWallets(@NonNull final GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(new DataHandler<Void, List<Wallet>>() {
            @Override
            public List<Wallet> execute(Void[] params) {
                SQLiteDatabase db = mAppDatabaseHelper.getReadableDatabase();
                List<Wallet> wallets = new ArrayList<>();
                Cursor cursor = db.query(WalletEntry.TBL_NAME_WALLET, null, null, null, null, null, null, QUERY_LIMIT);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) { // check if there are any left
                    Wallet wallet = new Wallet.Builder(cursor).build();
                    wallets.add(wallet);
                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                return wallets;
            }
        }, callback);
        task.execute();
    }
}
