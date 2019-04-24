package com.thailam.piggywallet.ui.addtransaction;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.util.Constants;

public class TransactionPresenter implements TransactionContract.Presenter,
        TransactionDataSource.TransactionCallback {
    @NonNull
    private TransactionContract.View mView;
    @NonNull
    private TransactionDataSource mTransactionRepository;
    private final String AMOUNT_REGEX = "[0-9]{1,12}";

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
        double amount = getTransactionAmount(category.getType(), amountStr);
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

    private double getTransactionAmount(String categoryType, String amountStr) {
        StringBuilder finalAmtStr = new StringBuilder();
        if (categoryType.equals(Constants.CATEGORY_OUTFLOW)) {
            finalAmtStr.append("-").append(amountStr);
        } else {
            finalAmtStr.append(amountStr);
        }
        return Double.valueOf(finalAmtStr.toString());
    }

    private boolean validateInputs(String amountStr, Category category) {
        if (!amountStr.matches(AMOUNT_REGEX)) {
            mView.showInvalidAmountError(amountStr);
            return false;
        }
        if (amountStr.isEmpty()) {
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
