package com.thailam.piggywallet.ui.addwallet;

import com.thailam.piggywallet.ui.base.BaseView;

public interface AddWalletContract {
    interface View extends BaseView {
        void showAddWalletSuccess();

        void showAddWalletFail(Exception e);

        void showMissingTitleError();
    }

    interface Presenter {
        void saveWallet(String title, String subtitle, String balanceStr);
    }
}
