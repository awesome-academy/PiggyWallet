package com.thailam.piggywallet.ui.addtransaction;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

public interface TransactionContract {
    interface View extends BaseView {
        void showSuccess();

        void showError(Exception e);

        void showMissingAmountError();

        void showMissingCategoryError();

        void showInvalidAmountError(String input);
    }

    interface Presenter extends BasePresenter {
        void saveTransaction(Wallet wallet, String note, String amountStr, Category category, long date);
    }
}
