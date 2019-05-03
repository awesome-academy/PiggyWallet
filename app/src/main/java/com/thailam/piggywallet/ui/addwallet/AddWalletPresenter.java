package com.thailam.piggywallet.ui.addwallet;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

public class AddWalletPresenter implements AddWalletContract.Presenter, OnDataLoadedCallback<Long> {
    @NonNull
    private AddWalletContract.View mView;
    @NonNull
    private WalletDataSource mWalletRepository;

    AddWalletPresenter(@NonNull AddWalletContract.View view,
                       @NonNull WalletDataSource walletRepository) {
        mView = view;
        mWalletRepository = walletRepository;
    }

    @Override
    public void saveWallet(String title, String subtitle, String amountStr) {
        if (title.isEmpty()) {
            mView.showMissingTitleError();
            return;
        }
        Double amount = amountStr.isEmpty() ? 0 : Double.parseDouble(amountStr);
        Wallet wallet = new Wallet.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setAmount(amount).build();
        mWalletRepository.saveWallet(wallet, this);
    }

    @Override
    public void onDataLoaded(Long data) {
        mView.showAddWalletSuccess();
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        mView.showAddWalletFail(e);
    }
}
