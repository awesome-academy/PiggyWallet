package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.base.OnSaveDataCallback;

public interface TransactionDataSource {
    void saveTransaction(Transaction transaction, @NonNull OnSaveDataCallback callback);
}
