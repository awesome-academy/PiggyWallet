package com.thailam.piggywallet.data.source.local.dao;

import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;

import java.util.List;

public interface TransactionDAO {
    long saveTransaction(Wallet wallet, Transaction transaction) throws Exception;

    List<Transaction> getInitialTransactions(int walletId) throws Exception;
}
