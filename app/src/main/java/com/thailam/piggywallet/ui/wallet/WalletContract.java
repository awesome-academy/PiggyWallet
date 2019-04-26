package com.thailam.piggywallet.ui.wallet;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

import java.util.List;

public interface WalletContract {
    interface View extends BaseView {
        void updateWallets(List<Wallet> wallets);

        void onGetWalletsError(Exception e);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends BasePresenter {
        void getWallets();

        void searchWallets(String input);

        void handleFirstSwipeRefresh();

        List<Wallet> getCachedWallets();
    }
}
