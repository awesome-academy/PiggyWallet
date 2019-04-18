package com.thailam.piggywallet.ui.addtransaction;

import android.widget.EditText;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.ui.base.BasePresenter;
import com.thailam.piggywallet.ui.base.BaseView;

public interface AddTransactionContract {
    interface View extends BaseView {
        void showSuccess();

        void showError(String str);

        void showErrorPrompt(String errCode);
    }

    interface Presenter extends BasePresenter {
        void saveTransaction(EditText editTextNote, EditText editTextAmount, Category category, long date);
    }
}
