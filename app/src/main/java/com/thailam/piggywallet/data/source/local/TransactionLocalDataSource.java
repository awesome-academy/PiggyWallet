package com.thailam.piggywallet.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;
import com.thailam.piggywallet.data.source.local.entry.TransactionEntry;

import java.sql.SQLException;

public class TransactionLocalDataSource implements TransactionDataSource {

    private static TransactionLocalDataSource sInstance;
    private static AppDatabaseHelper mAppDatabaseHelper;

    private TransactionLocalDataSource(Context context) {
        mAppDatabaseHelper = AppDatabaseHelper.getInstance(context);
    }

    public static TransactionLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TransactionLocalDataSource(context);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void saveTransaction(Transaction transaction, @NonNull SaveTransactionCallback callback) {
        LocalAsyncTask<Void, Long> task = new LocalAsyncTask<>(params -> {
            SQLiteDatabase db = mAppDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TransactionEntry.AMOUNT, transaction.getAmount());
            values.put(TransactionEntry.NOTE, transaction.getNote());
            values.put(TransactionEntry.DATE, transaction.getDate());
            values.put(TransactionEntry.FOR_CAT_ID, transaction.getCategoryId());
            values.put(TransactionEntry.FOR_WALLET_ID, transaction.getWalletId());
            long result = db.insertOrThrow(TransactionEntry.TBL_NAME_TRANS, null, values);
            db.close();
            return result;
        }, callback);
        task.execute();
    }
}
