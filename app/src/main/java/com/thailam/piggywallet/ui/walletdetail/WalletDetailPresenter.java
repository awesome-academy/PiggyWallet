package com.thailam.piggywallet.ui.walletdetail;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.prefs.PreferenceHelper;

import java.util.List;

public class WalletDetailPresenter implements WalletDetailContract.Presenter, TransactionDataSource.GetTransactionCallback {
    @NonNull
    private WalletDetailContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;
    @NonNull
    private PreferenceHelper mPreferenceRepository;

    WalletDetailPresenter(@NonNull WalletDetailContract.View view,
                          @NonNull TransactionDataSource transactionRepository,
                          @NonNull PreferenceHelper preferenceRepository) {
        mView = view;
        mTransactionRepository = transactionRepository;
        mPreferenceRepository = preferenceRepository;
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
        if(!mPreferenceRepository.saveWalletToSharedPref(wallet)) {
            mView.onSaveWalletToSharedPrefFailed();
        }
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
