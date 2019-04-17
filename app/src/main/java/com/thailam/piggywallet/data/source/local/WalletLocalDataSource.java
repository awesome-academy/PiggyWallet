package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

import java.util.ArrayList;
import java.util.List;

public class WalletLocalDataSource implements WalletDataSource {

    private static WalletLocalDataSource sInstance;
    private static AppDatabaseHelper mAppDatabaseHelper;
    private static final String QUERY_LIMIT = "5";
    private static final String QUERY_SEARCH_WALLETS_LIKE = WalletEntry.TITLE + " LIKE ? ";

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
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            SQLiteDatabase db = mAppDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    true, WalletEntry.TBL_NAME_WALLET, null,
                    null, null, null,
                    null, null, QUERY_LIMIT);
            return getWallets(db, cursor);
        }, callback);
        task.execute();
    }

    @Override
    public void getSearchedWallets(String input, @NonNull final GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            String selectionArgs[] = new String[]{"%" + input + "%"};
            SQLiteDatabase db = mAppDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(true, WalletEntry.TBL_NAME_WALLET, null,
                    QUERY_SEARCH_WALLETS_LIKE, selectionArgs, null,
                    null, null, null);
            return getWallets(db, cursor);
        }, callback);
        task.execute();
    }

    @Override
    public boolean addWallet(Wallet wallet) {
        //
        return false;
    }

    @Override
    public List<Wallet> getCachedWallets() {
        return null;
    }

    private List<Wallet> getWallets(SQLiteDatabase db, Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        List<Wallet> wallets = new ArrayList<>();
        while (cursor.moveToNext()) { // check if there are any left
            wallets.add(new Wallet.Builder(cursor).build());
        }
        cursor.close();
        db.close();
        return wallets;
    }
}
