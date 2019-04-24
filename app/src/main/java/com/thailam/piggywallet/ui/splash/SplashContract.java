package com.thailam.piggywallet.ui.splash;

import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

public interface SplashContract {
    interface View extends BaseView {
        void openWalletDetail();

        void showError(Exception e);
    }

    interface Presenter extends BasePresenter {
        void loadWalletsData();
    }
}
