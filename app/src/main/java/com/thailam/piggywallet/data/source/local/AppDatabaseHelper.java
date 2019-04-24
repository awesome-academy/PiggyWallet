package com.thailam.piggywallet.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.local.entry.CategoryEntry;
import com.thailam.piggywallet.data.source.local.entry.DatabaseEntry;
import com.thailam.piggywallet.data.source.local.entry.TransactionEntry;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to create database
 */
public class AppDatabaseHelper extends SQLiteOpenHelper implements DatabaseDAO {
    private static AppDatabaseHelper sInstance;
    private static final String CREATE_TBL_WALLETS = "CREATE TABLE " + WalletEntry.TBL_NAME_WALLET + "("
            + WalletEntry.ID + " INTEGER PRIMARY KEY , " + WalletEntry.TITLE + " TEXT, "
            + WalletEntry.SUBTITLE + " TEXT," + WalletEntry.AMOUNT + " DECIMAL, "
            + WalletEntry.INFLOW + " DECIMAL, " + WalletEntry.OUTFLOW + " DECIMAL, "
            + WalletEntry.ICON + " TEXT, "
            + WalletEntry.CREATED_AT + " INTEGER," + WalletEntry.UPDATED_AT + " INTEGER " + ")";
    private static final String CREATE_TBL_CATE = "CREATE TABLE " + CategoryEntry.TBL_NAME_CATE + "("
            + CategoryEntry.ID + " INTEGER PRIMARY KEY, " + CategoryEntry.NAME + " TEXT, "
            + CategoryEntry.CREATED_AT + " INTEGER, " + CategoryEntry.UPDATED_AT + " INTEGER " + ")";
    private static final String CREATE_TBL_TRANS = "CREATE TABLE " + TransactionEntry.TBL_NAME_TRANS + "("
            + TransactionEntry.ID + " INTEGER PRIMARY KEY , " + TransactionEntry.NOTE + " TEXT, "
            + TransactionEntry.FOR_CAT_ID + " INTEGER, " + TransactionEntry.FOR_WALLET_ID + " INTEGER, "
            + TransactionEntry.AMOUNT + " DECIMAL, "
            + TransactionEntry.DATE + " INTEGER, "
            + "FOREIGN KEY (" + TransactionEntry.FOR_CAT_ID + ") REFERENCES " + CategoryEntry.TBL_NAME_CATE + "(" + CategoryEntry.ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + TransactionEntry.FOR_WALLET_ID + ") REFERENCES " + WalletEntry.TBL_NAME_WALLET + "(" + WalletEntry.ID + ") ON DELETE CASCADE "
            + ")";
    // Droping tables
    private static final String DROP_TBL_WALLET = "DROP TABLE IF EXISTS " + WalletEntry.TBL_NAME_WALLET;
    private static final String DROP_TBL_TRANS = "DROP TABLE IF EXISTS " + TransactionEntry.TBL_NAME_TRANS;
    private static final String DROP_TBL_CATE = "DROP TABLE IF EXISTS " + CategoryEntry.TBL_NAME_CATE;

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

    @Override
    public void saveTransaction(Wallet wallet, Transaction transaction,
                                @NonNull TransactionDataSource.TransactionCallback callback) {
        LocalAsyncTask<Void, Long> task = new LocalAsyncTask<>(params -> {
            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put(TransactionEntry.AMOUNT, transaction.getAmount());
                values.put(TransactionEntry.NOTE, transaction.getNote());
                values.put(TransactionEntry.DATE, transaction.getDate());
                values.put(TransactionEntry.FOR_CAT_ID, transaction.getCategoryId());
                values.put(TransactionEntry.FOR_WALLET_ID, transaction.getWalletId());
                long result = db.insertOrThrow(TransactionEntry.TBL_NAME_TRANS, null, values);
                updateWalletOnSaveTransaction(db, wallet, transaction.getAmount());
                db.setTransactionSuccessful();
                return result;
            } catch (Exception e) {
                throw new Exception(e);
            } finally {
                db.endTransaction();
            }
        }, callback);
        task.execute();
    }

    @Override
    public void getInitialTransactions(int walletId, @NonNull TransactionDataSource.GetTransactionCallback callback) {
        LocalAsyncTask<Void, List<Transaction>> task = new LocalAsyncTask<>(params -> {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(
                    true, TransactionEntry.TBL_NAME_TRANS, null,
                    null, null, null,
                    null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            List<Transaction> transactions = new ArrayList<>();
            while (cursor.moveToNext()) { // check if there are any left
                transactions.add(new Transaction(cursor));
            }
            cursor.close();
            db.close();
            return transactions;
        }, callback);
        task.execute();
    }

    /**
     * Additional method for saveTransaction(), update the wallet info with the transaction
     *
     * @param db     : current SQlite db transcation
     * @param wallet : the target Wallet of the transaction
     * @param amount : the amount of the transaction
     */
    private void updateWalletOnSaveTransaction(SQLiteDatabase db, Wallet wallet, double amount) {
        String whereClause = "_id = ?";
        String balanceFlowColEntry;
        double addedBalanceFlowAmount;
        if (amount >= 0) {
            balanceFlowColEntry = WalletEntry.INFLOW;
            addedBalanceFlowAmount = wallet.getInflow() + amount;
        } else {
            balanceFlowColEntry = WalletEntry.OUTFLOW;
            addedBalanceFlowAmount = wallet.getOutflow() + amount;
        }
        double addedAmount = wallet.getAmount() + amount;
        // updating the wallet
        ContentValues walletValues = new ContentValues();
        walletValues.put(WalletEntry.AMOUNT, addedAmount);
        walletValues.put(balanceFlowColEntry, addedBalanceFlowAmount);
        db.update(WalletEntry.TBL_NAME_WALLET,
                walletValues,
                whereClause,
                new String[]{String.valueOf(wallet.getId())});
    }
}
