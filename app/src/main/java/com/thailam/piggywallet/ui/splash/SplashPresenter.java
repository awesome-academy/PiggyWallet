package com.thailam.piggywallet.ui.splash;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;

import java.util.List;

public class SplashPresenter implements SplashContract.Presenter, WalletDataSource.GetWalletCallback {
    @NonNull
    private final SplashContract.View mView;
    @NonNull
    private WalletDataSource mWalletRepository;

    public SplashPresenter(@NonNull SplashContract.View view, @NonNull WalletDataSource walletRepository) {
        mView = view;
        mWalletRepository = walletRepository;
    }

    @Override
    public void start() {
        loadWalletsData();
    }

    @Override
    public void loadWalletsData() {
        mWalletRepository.getInitialWallets(this);
    }

    @Override
    public void onDataLoaded(List<Wallet> wallets) {
        mView.changeScreen(wallets);
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        // TODO: handle exception here
    }
}
