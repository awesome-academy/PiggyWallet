package com.thailam.piggywallet.ui.walletdetail;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

import java.util.List;

public interface WalletDetailContract {
    interface View extends BaseView {
        void showProgressBar();

        void hideProgressBar();

        void updateTransactions(List<Transaction> transactions);

        void showNoTransactionData();
    }

    interface Presenter extends BasePresenter {
        void getInitialTransactions(int walletId);
    }
}
