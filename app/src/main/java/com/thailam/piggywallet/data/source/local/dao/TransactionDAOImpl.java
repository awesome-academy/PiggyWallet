package com.thailam.piggywallet.data.source.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.TransactionParent;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.local.AppDatabaseHelper;
import com.thailam.piggywallet.data.source.local.entry.TransactionEntry;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;
import com.thailam.piggywallet.util.Constants;
import com.thailam.piggywallet.util.TypeFormatUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionDAOImpl extends AppDatabaseHelper implements TransactionDAO {
    private static TransactionDAOImpl sInstance;

    private TransactionDAOImpl(Context context) {
        super(context);
    }

    public static TransactionDAOImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new TransactionDAOImpl(context);
                }
            }
        }
        return sInstance;
    }

    static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public long saveTransaction(Wallet wallet, Transaction transaction) throws Exception {
        initWriteDatabase();
        mDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TransactionEntry.AMOUNT, transaction.getAmount());
            values.put(TransactionEntry.NOTE, transaction.getNote());
            values.put(TransactionEntry.DATE, transaction.getDate());
            values.put(TransactionEntry.FOR_CAT_ID, transaction.getCategoryId());
            values.put(TransactionEntry.FOR_WALLET_ID, transaction.getWalletId());
            long result = mDatabase.insertOrThrow(TransactionEntry.TBL_NAME_TRANS, null, values);
            if (updateWalletOnSaveTransaction(wallet, transaction.getAmount())) {
                mDatabase.setTransactionSuccessful();
                return result;
            } else {
                throw new Exception(Constants.ERROR_UPDATE_FAIL);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            mDatabase.endTransaction();
            closeDatabase();
        }
    }

    @Override
    public List<TransactionParent> getInitialTransactions(int walletId) {
        String whereClause = TransactionEntry.FOR_WALLET_ID + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(walletId)};
        initReadDatabase();
        Cursor cursor = mDatabase.query(
                true, TransactionEntry.TBL_NAME_TRANS, null,
                whereClause, whereArgs, TransactionEntry.DATE,
                null, null, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        List<Transaction> transactions = new ArrayList<>();
        while (cursor.moveToNext()) { // check if there are any left
            transactions.add(new Transaction(cursor));
        }
        List<TransactionParent> list = convertTransactionsToTransactionParents(transactions);
        cursor.close();
        closeDatabase();
        return list;
    }

    private List<TransactionParent> convertTransactionsToTransactionParents(List<Transaction> transactions) {
        List<Transaction> tempList = new ArrayList<>();
        List<TransactionParent> transactionParents = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (tempList.size() == 0) {
                tempList.add(transaction);
            } else {
                String newDate = TypeFormatUtils.getDateFromLong(transaction.getDate());
                String oldDate = TypeFormatUtils.getDateFromLong(transactions.get(i - 1).getDate());
                if (newDate.equals(oldDate)) {
                    tempList.add(transaction);
                } else {
                    transactionParents.add(new TransactionParent(tempList, tempList.get(0).getDate()));
                    tempList = new ArrayList<>();
                    tempList.add(transaction);
                }
            }
        }
        transactionParents.add(new TransactionParent(tempList, tempList.get(0).getDate()));
        Collections.reverse(transactionParents);
        return transactionParents;
    }

    private boolean updateWalletOnSaveTransaction(Wallet wallet, double amount) {
        String whereClause = " id = ?";
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
        ContentValues values = new ContentValues();
        values.put(WalletEntry.AMOUNT, addedAmount);
        values.put(balanceFlowColEntry, addedBalanceFlowAmount);
        int rowAffected = mDatabase.update(WalletEntry.TBL_NAME_WALLET,
                values, whereClause,
                new String[]{String.valueOf(wallet.getId())});
        return rowAffected != 0;
    }
}
