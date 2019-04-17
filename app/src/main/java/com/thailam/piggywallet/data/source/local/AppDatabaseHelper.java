package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;
import com.thailam.piggywallet.data.source.local.entry.DatabaseEntry;
import com.thailam.piggywallet.data.source.local.entry.TransactionEntry;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;
import com.thailam.piggywallet.data.source.local.entry.WalletTransactionEntry;

/**
 * Helper class to create database
 */
public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static AppDatabaseHelper sInstance;
    private static final String CREATE_TBL_WALLETS = "CREATE TABLE " + WalletEntry.TBL_NAME_WALLET + "("
            + WalletEntry.ID + " INTEGER PRIMARY KEY , " + WalletEntry.TITLE + " TEXT,"
            + WalletEntry.SUBTITLE + " TEXT," + WalletEntry.AMOUNT + " DECIMAL,"
            + WalletEntry.ICON + " TEXT,"
            + WalletEntry.CREATED_AT + " INTEGER," + WalletEntry.UPDATED_AT + " INTEGER " + ")";
    private static final String CREATE_TBL_CATE = "CREATE TABLE " + CategoryEntry.TBL_NAME_CATE + "("
            + CategoryEntry.ID + " INTEGER PRIMARY KEY, " + CategoryEntry.NAME + " TEXT,"
            + CategoryEntry.CREATED_AT + " INTEGER," + CategoryEntry.UPDATED_AT + " INTEGER " + ")";
    private static final String CREATE_TBL_TRANS = "CREATE TABLE " + TransactionEntry.TBL_NAME_TRANS + "("
            + TransactionEntry.ID + " INTEGER PRIMARY KEY , " + TransactionEntry.NOTE + " TEXT,"
            + TransactionEntry.FOR_CAT_ID + " INTEGER," + TransactionEntry.AMOUNT + " DECIMAL,"
            + TransactionEntry.DATE + " INTEGER,"
            + " FOREIGN KEY (" + TransactionEntry.FOR_CAT_ID + ") REFERENCES " + CategoryEntry.TBL_NAME_CATE + "(" + CategoryEntry.ID + ") ON DELETE CASCADE )";
    private static final String CREATE_TBL_WALLETS_TRANS = "CREATE TABLE " + WalletTransactionEntry.TBL_NAME_WALLET_TRANS + "("
            + WalletTransactionEntry.ID + " INTEGER PRIMARY KEY ,"
            + WalletTransactionEntry.FOR_WAL_ID + " INTEGER NOT NULL, " + WalletTransactionEntry.FOR_TRANS_ID + " INTEGER NOT NULL,"
            + " FOREIGN KEY (" + WalletTransactionEntry.FOR_WAL_ID + ") REFERENCES " + WalletEntry.TBL_NAME_WALLET + "(" + WalletEntry.ID + ") ON DELETE CASCADE, "
            + " FOREIGN KEY (" + WalletTransactionEntry.FOR_TRANS_ID + ") REFERENCES " + TransactionEntry.TBL_NAME_TRANS + "(" + TransactionEntry.ID + ") ON DELETE CASCADE )";
    // Droping tables
    private static final String DROP_TBL_WALLET = "DROP TABLE IF EXISTS " + WalletEntry.TBL_NAME_WALLET;
    private static final String DROP_TBL_TRANS = "DROP TABLE IF EXISTS " + TransactionEntry.TBL_NAME_TRANS;
    private static final String DROP_TBL_CATE = "DROP TABLE IF EXISTS " + CategoryEntry.TBL_NAME_CATE;
    private static final String DROP_TBL_WALLET_TRANS = "DROP TABLE IF EXISTS " + WalletTransactionEntry.TBL_NAME_WALLET_TRANS;

    private AppDatabaseHelper(Context context) {
        super(context, DatabaseEntry.DB_NAME, null, DatabaseEntry.DB_VERSION);
    }

    static AppDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new AppDatabaseHelper(context);
                }
            }
        }
        return sInstance;
    }

    static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute the queries
        db.execSQL(CREATE_TBL_WALLETS);
        db.execSQL(CREATE_TBL_TRANS);
        db.execSQL(CREATE_TBL_CATE);
        db.execSQL(CREATE_TBL_WALLETS_TRANS);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TBL_WALLET);
        db.execSQL(DROP_TBL_TRANS);
        db.execSQL(DROP_TBL_CATE);
        db.execSQL(DROP_TBL_WALLET_TRANS);
        // rebuild
        onCreate(db);
    }
}
