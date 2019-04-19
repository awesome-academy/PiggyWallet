package com.thailam.piggywallet.ui.addtransaction;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.util.Constants;

public class TransactionPresenter implements TransactionContract.Presenter, TransactionDataSource.TransactionCallback {
    @NonNull
    private TransactionContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;

    TransactionPresenter(@NonNull TransactionContract.View view,
                         @NonNull TransactionDataSource transactionRepository) {
        mView = view;
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void start() {
    }

    @Override
    public void saveTransaction(String note, String amountStr, Category category, long dateInput) {
        if (!validateInputs(amountStr, category)) return;
        int defaultId = 0;
        double amount = Double.valueOf(amountStr);
        int categoryId = category.getId();
        long date = dateInput == 0 ? System.currentTimeMillis() : dateInput;
        Transaction transaction = new Transaction.Builder(defaultId)
                .setNote(note)
                .setAmount(amount)
                .setCategoryId(categoryId)
                .setDate(date)
                .build();
        // save the transaction
        mTransactionRepository.saveTransaction(transaction, this);
    }

    private boolean validateInputs(String amountStr, Category category) {
        if (amountStr == null || amountStr.isEmpty()) {
            mView.showMissingAmountError();
            return false;
        }
        if (category == null) {
            mView.showMissingCategoryError();
            return false;
        }
        return true;
    }

    @Override
    public void onDataLoaded(Long data) {
        mView.showSuccess();
    }

    @Override
    public void onDataNotAvailable(Exception e) {
        mView.showError(e);
    }
}
