package com.thailam.piggywallet.ui.addtransaction;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.util.Constants;

public class AddTransactionPresenter implements AddTransactionContract.Presenter, TransactionDataSource.SaveTransactionCallback {
    @NonNull
    private AddTransactionContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;

    AddTransactionPresenter(@NonNull AddTransactionContract.View view,
                            @NonNull TransactionDataSource transactionRepository) {
        mView = view;
        mTransactionRepository = transactionRepository;
    }

    @Override
    public void start() {
    }

    @Override
    public void saveTransaction(EditText editTextNote, EditText editTextAmount, Category category, long dateInput) {
        if (!validateInputs(editTextAmount, category)) return;
        int defaultId = 0;
        String note = editTextNote.getText().toString();
        double amount = Double.valueOf(editTextAmount.getText().toString());
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

    private boolean validateInputs(EditText editTextAmount, Category category) {
        if (editTextAmount.getText().toString().isEmpty()) {
            mView.showErrorPrompt(Constants.ERR_CODE_MISSING_AMOUNT);
            return false;
        }
        if (category == null) {
            mView.showErrorPrompt(Constants.ERR_CODE_MISSING_CATEGORY);
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
