package com.thailam.piggywallet.ui.walletdetail;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.TransactionParent;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.WalletDataSource;

import java.util.List;

public class WalletDetailPresenter implements WalletDetailContract.Presenter, TransactionDataSource.GetTransactionCallback {
    @NonNull
    private WalletDetailContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;
    @NonNull
    private WalletDataSource mWalletRepository;

    WalletDetailPresenter(@NonNull WalletDetailContract.View view,
                          @NonNull TransactionDataSource transactionRepository,
                          @NonNull WalletDataSource walletRepository) {
        mView = view;
        mTransactionRepository = transactionRepository;
        mWalletRepository = walletRepository;
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
    public void saveWalletToSharedPref(Wallet wallet) {
        if(!mWalletRepository.putWalletToPrefs(wallet)) {
            mView.onSaveWalletToSharedPrefFailed();
        }
    }

    @Override
    public void onDataLoaded(List<TransactionParent> transactionParents) {
        mView.hideProgressBar();
        mView.updateTransactions(transactionParents);
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        mView.hideProgressBar();
        mView.showNoTransactionData();
    }
}
