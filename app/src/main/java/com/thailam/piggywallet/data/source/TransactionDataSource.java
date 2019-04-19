package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

public interface TransactionDataSource {
    interface TransactionCallback extends OnDataLoadedCallback<Long> { }
    void saveTransaction(Transaction transaction, @NonNull TransactionCallback callback);
}
