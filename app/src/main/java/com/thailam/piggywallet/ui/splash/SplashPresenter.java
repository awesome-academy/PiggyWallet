package com.thailam.piggywallet.ui.splash;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.prefs.AppPreferenceHelper;
import com.thailam.piggywallet.data.source.prefs.PreferenceHelper;

import java.util.List;

public class SplashPresenter implements SplashContract.Presenter,
        WalletDataSource.GetWalletCallback {
    @NonNull
    private final SplashContract.View mView;
    @NonNull
    private WalletDataSource mWalletRepository;
    @NonNull
    private PreferenceHelper mPreferenceRepository;

    SplashPresenter(@NonNull SplashContract.View view,
                    @NonNull WalletDataSource walletRepository,
                    @NonNull PreferenceHelper preferenceRepository) {
        mView = view;
        mWalletRepository = walletRepository;
        mPreferenceRepository = preferenceRepository;
    }

    @Override
    public void start() {
        loadWalletsData();
    }

    @Override
    public void loadWalletsData() {
        mWalletRepository.getInitialWallets(false, this);
    }

    @Override
    public Wallet getWalletFromSharedPrefs() {
        return mPreferenceRepository.getWalletFromSharedPref();
    }

    @Override
    public void onDataLoaded(List<Wallet> wallets) {
        mView.openWalletDetail();
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        mView.openWalletDetail();
        mView.onLoadWalletError(e);
    }
}
