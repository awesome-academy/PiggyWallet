package com.thailam.piggywallet.data.source.local;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;

public interface DatabaseDAO {

    void saveTransaction(Wallet wallet, Transaction transaction,
                         @NonNull TransactionDataSource.TransactionCallback callback);

    void getInitialTransactions(int walletId, @NonNull TransactionDataSource.GetTransactionCallback callback);
}
