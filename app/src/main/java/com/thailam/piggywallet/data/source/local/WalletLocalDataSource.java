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
import java.util.Date;
import java.util.List;

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

    @Override
    public void getInitialWallets(@NonNull final GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(new DataHandler<Void, List<Wallet>>() {
            @Override
            public List<Wallet> execute(Void[] params) {
                SQLiteDatabase db = mAppDatabaseHelper.getReadableDatabase();
                List<Wallet> wallets = new ArrayList<>();
                final String QUERY_LIMIT = "5";
                Cursor cursor = db.query(WalletEntry.TBL_NAME_WALLET, null, null, null, null, null, null, QUERY_LIMIT);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) { // check if there are any left
                    Wallet wallet = convertCursorToWallet(cursor);
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

    /**
     * Helper function to convert cursor to wallet object
     *
     * @param cursor(Cursor)
     * @return Wallet
     */
    private Wallet convertCursorToWallet(Cursor cursor) {
        int indexId = cursor.getColumnIndex(WalletEntry.ID);
        int indexTitle = cursor.getColumnIndex(WalletEntry.TITLE);
        int indexSubtitle = cursor.getColumnIndex(WalletEntry.SUBTITLE);
        int indexAmount = cursor.getColumnIndex(WalletEntry.AMOUNT);
        int indexIconUrl = cursor.getColumnIndex(WalletEntry.ICON);
        int indexCreatedAt = cursor.getColumnIndex(WalletEntry.CREATED_AT);
        int indexUpdatedAt = cursor.getColumnIndex(WalletEntry.UPDATED_AT);
        // get data from column index
        int id = cursor.getInt(indexId);
        String title = cursor.getString(indexTitle);
        String subtitle = cursor.getString(indexSubtitle);
        double amount = cursor.getDouble(indexAmount);
        String iconUrl = cursor.getString(indexIconUrl);
        Date createdAt = new Date(cursor.getString(indexCreatedAt));
        Date updatedAt = new Date(cursor.getString(indexUpdatedAt));
        // return data as Wallet obj
        return new Wallet.WalletBuilder(id)
                .setTitle(title)
                .setSubtitle(subtitle)
                .setAmount(amount)
                .setIconUrl(iconUrl)
                .setCreatedAt(createdAt)
                .setUpdatedAt(updatedAt)
                .build();
    }
}
