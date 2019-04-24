package com.thailam.piggywallet.data.source.local.dao;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;

import java.util.List;

public interface TransactionDAO {
    long saveTransaction(Wallet wallet, Transaction transaction,
                         @NonNull TransactionDataSource.TransactionCallback callback) throws Exception;

    List<Transaction> getInitialTransactions(int walletId,
                                             @NonNull TransactionDataSource.GetTransactionCallback callback) throws Exception;
}
