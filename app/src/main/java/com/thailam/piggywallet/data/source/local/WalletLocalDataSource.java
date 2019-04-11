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
            return queryGetWallets(
                    true, WalletEntry.TBL_NAME_WALLET, null,
                    null, null, null,
                    null, null, QUERY_LIMIT);
        }, callback);
        task.execute();
    }

    @Override
    public void getSearchedWallets(String input, @NonNull GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            String selectionArgs[] = new String[]{"%" + input + "%"};
            return queryGetWallets(true, WalletEntry.TBL_NAME_WALLET, null,
                    QUERY_SEARCH_WALLETS_LIKE, selectionArgs, null,
                    null, null, null);
        }, callback);
        task.execute();
    }

    @Override
    public boolean addWallet(Wallet wallet) {
        // TODO: implement at task add walet
        return false;
    }

    @Override
    public List<Wallet> getCachedWallets() {
        return null;
    }

    private List<Wallet> queryGetWallets(boolean distinct, String table, String[] columns,
                                         String selection, String[] selectionArgs, String groupBy,
                                         String having, String orderBy, String limit) {
        SQLiteDatabase db = mAppDatabaseHelper.getReadableDatabase();
        List<Wallet> walletsList = new ArrayList<>();
        Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) { // check if there are any left
            Wallet wallet = new Wallet.Builder(cursor).build();
            walletsList.add(wallet);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return walletsList;
    }
}
