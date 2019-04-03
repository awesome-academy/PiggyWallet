package com.thailam.piggywallet.ui.wallet;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;

import java.util.List;

public class WalletPresenter implements WalletContract.Presenter,
        WalletDataSource.GetWalletCallback {

    @NonNull
    private WalletDataSource mWalletRepository;

    private WalletContract.View mView;

    public WalletPresenter(@NonNull WalletContract.View view, @NonNull WalletDataSource walletRepository) {
        mView = view;
        mWalletRepository = walletRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getWallets() {
        mWalletRepository.getInitialWallets(this);
    }

    @Override
    public void onDataLoaded(List<Wallet> wallets) {
        // TODO: do when on load data task
    }

    @Override
    public void onDataNotAvailable(Exception e) {

    }
}
