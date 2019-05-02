
package com.thailam.piggywallet.ui.addwallet;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.base.BaseView;

public interface AddWalletContract {
    interface View extends BaseView {
        void showAddWalletSuccess();

        void showAddWalletFail(Exception e);
    }

    interface Presenter {
        void saveWallet(Wallet wallet);
    }
}
