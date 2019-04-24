package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;
import com.thailam.piggywallet.data.source.local.entry.DatabaseEntry;
import com.thailam.piggywallet.data.source.local.entry.TransactionEntry;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

/**
 * Helper class to create database
 */
public class AppDatabaseHelper extends SQLiteOpenHelper {
    protected static SQLiteDatabase mDatabase;
    private static final String CREATE_TBL_WALLETS = "CREATE TABLE " + WalletEntry.TBL_NAME_WALLET + "("
            + WalletEntry.ID + " INTEGER PRIMARY KEY , " + WalletEntry.TITLE + " TEXT, "
            + WalletEntry.SUBTITLE + " TEXT," + WalletEntry.AMOUNT + " DECIMAL, "
            + WalletEntry.INFLOW + " DECIMAL, " + WalletEntry.OUTFLOW + " DECIMAL, "
            + WalletEntry.ICON + " TEXT, "
            + WalletEntry.CREATED_AT + " INTEGER," + WalletEntry.UPDATED_AT + " INTEGER " + ")";
    private static final String CREATE_TBL_CATE = "CREATE TABLE " + CategoryEntry.TBL_NAME_CATE + "("
            + CategoryEntry.ID + " INTEGER PRIMARY KEY, " + CategoryEntry.NAME + " TEXT, "
            + CategoryEntry.TYPE + " TEXT, "
            + CategoryEntry.CREATED_AT + " INTEGER, " + CategoryEntry.UPDATED_AT + " INTEGER " + ")";
    private static final String CREATE_TBL_TRANS = "CREATE TABLE " + TransactionEntry.TBL_NAME_TRANS + "("
            + TransactionEntry.ID + " INTEGER PRIMARY KEY , " + TransactionEntry.NOTE + " TEXT, "
            + TransactionEntry.FOR_CAT_ID + " INTEGER, " + TransactionEntry.FOR_WALLET_ID + " INTEGER, "
            + TransactionEntry.AMOUNT + " DECIMAL, "
            + TransactionEntry.DATE + " INTEGER, "
            + "FOREIGN KEY (" + TransactionEntry.FOR_CAT_ID + ") REFERENCES " + CategoryEntry.TBL_NAME_CATE + "(" + CategoryEntry.ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + TransactionEntry.FOR_WALLET_ID + ") REFERENCES " + WalletEntry.TBL_NAME_WALLET + "(" + WalletEntry.ID + ") ON DELETE CASCADE "
            + ")";
    // Dropping tables queries
    private static final String DROP_TBL_WALLET = "DROP TABLE IF EXISTS " + WalletEntry.TBL_NAME_WALLET;
    private static final String DROP_TBL_TRANS = "DROP TABLE IF EXISTS " + TransactionEntry.TBL_NAME_TRANS;
    private static final String DROP_TBL_CATE = "DROP TABLE IF EXISTS " + CategoryEntry.TBL_NAME_CATE;

    protected AppDatabaseHelper(Context context) {
        super(context, DatabaseEntry.DB_NAME, null, DatabaseEntry.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute the queries
        db.execSQL(CREATE_TBL_WALLETS);
        db.execSQL(CREATE_TBL_TRANS);
        db.execSQL(CREATE_TBL_CATE);
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
        // rebuild
        onCreate(db);
    }

    protected void initReadDatabase() {
        mDatabase = getReadableDatabase();
    }

    protected void initWriteDatabase() {
        mDatabase = getWritableDatabase();
    }

    protected void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
}
