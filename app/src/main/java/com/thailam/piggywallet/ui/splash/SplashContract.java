package com.thailam.piggywallet.ui.splash;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

import java.util.List;

public interface SplashContract {
    interface View extends BaseView<Presenter> {
        void changeScreen(List<Wallet> wallets);
    }

    interface Presenter extends BasePresenter {
        void loadWalletsData();
    }
}
