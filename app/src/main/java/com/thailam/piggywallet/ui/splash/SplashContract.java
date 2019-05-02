package com.thailam.piggywallet.ui.splash;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

public interface SplashContract {
    interface View extends BaseView {
        void openWalletDetail();

        void onLoadWalletError(Exception e);
    }

    interface Presenter extends BasePresenter {
        void loadWalletsData();

        Wallet getWalletFromSharedPrefs();
    }
}
