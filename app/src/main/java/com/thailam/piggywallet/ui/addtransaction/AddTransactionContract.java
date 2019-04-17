package com.thailam.piggywallet.ui.addtransaction;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

public interface AddTransactionContract {
    interface View extends BaseView {
        void showSuccess();

        void showError(String msg);
    }

    interface Presenter extends BasePresenter {
        void saveTransaction(Transaction transaction);
    }
}
