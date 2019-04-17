package com.thailam.piggywallet.ui.walletdetail;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;

import java.util.List;

public class WalletDetailPresenter implements WalletDetailContract.Presenter, TransactionDataSource.GetTransactionCallback {
    @NonNull
    private WalletDetailContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;

    WalletDetailPresenter(@NonNull WalletDetailContract.View view,
                                 @NonNull TransactionDataSource transactionRepository) {
        mView = view;
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getInitialTransactions(int walletId) {
        mView.showProgressBar();
        mTransactionRepository.getInitialTransactions(walletId, this);
    }

    @Override
    public void onDataLoaded(List<Transaction> transactions) {
        mView.hideProgressBar();
        mView.updateTransactions(transactions);
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        mView.hideProgressBar();
        mView.showNoTransactionData();
    }
}
