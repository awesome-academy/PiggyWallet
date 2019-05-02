package com.thailam.piggywallet.data.source.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.local.AppDatabaseHelper;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;
import com.thailam.piggywallet.data.source.prefs.PreferenceHelper;
import com.thailam.piggywallet.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class WalletDAOImpl extends AppDatabaseHelper implements WalletDAO {
    private static WalletDAOImpl sInstance;
    private static final String QUERY_LIMIT = "20";
    private static final String QUERY_SEARCH_WALLETS_LIKE = WalletEntry.TITLE + " LIKE ? ";

    private WalletDAOImpl(Context context) {
        super(context);
    }

    public static WalletDAOImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new WalletDAOImpl(context);
                }
            }
        }
        return sInstance;
    }

    static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public List<Wallet> getInitialWallets() throws Exception {
        initReadDatabase();
        Cursor cursor = mDatabase.query(
                true, WalletEntry.TBL_NAME_WALLET, null,
                null, null, null,
                null, null, QUERY_LIMIT);
        return retrieveListHelper(cursor);
    }

    @Override
    public List<Wallet> getSearchedWallets(String input) throws Exception {
        String[] selectionArgs = new String[]{"%" + input + "%"};
        initReadDatabase();
        Cursor cursor = mDatabase.query(true, WalletEntry.TBL_NAME_WALLET, null,
                QUERY_SEARCH_WALLETS_LIKE, selectionArgs, null,
                null, null, null);
        return retrieveListHelper(cursor);
    }

    @Override
    public long saveWallet(Wallet wallet) throws Exception {
        initWriteDatabase();
        ContentValues values = new ContentValues();
        values.put(WalletEntry.TITLE, wallet.getTitle());
        values.put(WalletEntry.SUBTITLE, wallet.getSubtitle());
        values.put(WalletEntry.AMOUNT, wallet.getAmount());
        values.put(WalletEntry.ICON, wallet.getIconUrl());
        long result = 0;
        try {
            result = mDatabase.insertOrThrow(WalletEntry.TBL_NAME_WALLET, null, values);
        } catch (SQLException e) {
            throw e;
        } finally {
            closeDatabase();
        }
        return result;
    }

    private List<Wallet> retrieveListHelper(Cursor cursor) throws NullPointerException {
        if (cursor.getCount() == 0) {
            throw new NullPointerException(Constants.NO_DATA_ERROR);
        }
        List<Wallet> wallets = new ArrayList<>();
        while (cursor.moveToNext()) { // check if there are any left
            wallets.add(new Wallet.Builder(cursor).build());
        }
        cursor.close();
        closeDatabase();
        return wallets;
    }
}
