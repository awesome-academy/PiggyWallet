package com.thailam.piggywallet.ui.addtransaction;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;

public class TransactionPresenter implements TransactionContract.Presenter,
        TransactionDataSource.TransactionCallback {
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
    public void saveTransaction(Wallet wallet, String note, String amountStr,
                                Category category, long dateInput) {
        if (!validateInputs(amountStr, category)) return;
        double amount = Double.valueOf(amountStr);
        int categoryId = category.getId();
        long date = dateInput == 0 ? System.currentTimeMillis() : dateInput;
        Transaction transaction = new Transaction.Builder()
                .setNote(note)
                .setAmount(amount)
                .setCategoryId(categoryId)
                .setWalletId(wallet.getId())
                .setDate(date)
                .build();
        // save the transaction
        mTransactionRepository.saveTransaction(wallet, transaction, this);
    }

    private boolean validateInputs(String amountStr, Category category) {
        if (amountStr == null || amountStr.isEmpty() || Double.parseDouble(amountStr) == 0) {
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
