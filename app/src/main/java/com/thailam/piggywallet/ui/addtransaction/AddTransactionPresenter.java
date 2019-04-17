package com.thailam.piggywallet.ui.addtransaction;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.base.OnSaveDataCallback;
import com.thailam.piggywallet.util.Constants;

public class AddTransactionPresenter implements AddTransactionContract.Presenter, OnSaveDataCallback {
    @NonNull
    private AddTransactionContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;

    AddTransactionPresenter(@NonNull AddTransactionContract.View view,
                            @NonNull TransactionDataSource transactionRepository) {
        mView = view;
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void start() {
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        mTransactionRepository.saveTransaction(transaction, this);
    }

    @Override
    public void onSaveDataSuccess() {
        mView.showSuccess();
    }

    @Override
    public void onSaveDataFail(Exception e) {
        mView.showError((e ==null)? Constants.UNKNOWN_ERROR : e.getMessage());
    }
}
