package com.thailam.piggywallet.ui.wallet;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;

import java.util.List;

public class WalletPresenter implements WalletContract.Presenter,
        WalletDataSource.GetWalletCallback {
    @NonNull
    private WalletContract.View mView;
    @NonNull
    private WalletDataSource mWalletRepository;

    public WalletPresenter(@NonNull WalletContract.View view,
                           @NonNull WalletDataSource walletRepository) {
        mView = view;
        mWalletRepository = walletRepository;
    }

    @Override
    public void start() {
    }

    @Override
    public void getWallets() {
        mView.toggleIsRefreshing();
        mWalletRepository.getInitialWallets(this);
    }

    @Override
    public void handleFirstSwipeRefresh() {
        if (getCachedWallets().size() == 0) getWallets();
    }

    @Override
    public List<Wallet> getCachedWallets() {
        return mWalletRepository.getCachedWallets();
    }

    @Override
    public void onDataLoaded(List<Wallet> wallets) {
        mView.updateWallets(wallets);
        mView.toggleIsRefreshing();
    }

    @Override
    public void onDataNotAvailable(Exception e) {
    }
}
