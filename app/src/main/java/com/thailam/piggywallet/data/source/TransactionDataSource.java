package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.TransactionParent;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

import java.util.List;

public interface TransactionDataSource {

    interface TransactionCallback extends OnDataLoadedCallback<Long> {
    }

    interface GetTransactionCallback extends OnDataLoadedCallback<List<TransactionParent>> {
    }

    void saveTransaction(Wallet wallet, Transaction transaction,
                         @NonNull TransactionCallback callback);

    void getInitialTransactions(boolean force, int walletId, @NonNull GetTransactionCallback callback);
}
