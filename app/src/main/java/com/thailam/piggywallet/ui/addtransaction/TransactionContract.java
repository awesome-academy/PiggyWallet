package com.thailam.piggywallet.ui.addtransaction;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

public interface TransactionContract {
    interface View extends BaseView {
        void showSuccess();

        void showError(Exception e);

        void showMissingAmountError();

        void showMissingCategoryError();
    }

    interface Presenter extends BasePresenter {
        void saveTransaction(String note, String amountStr, Category category, long date);
    }
}
