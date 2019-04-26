package com.thailam.piggywallet.data.source.local.dao;

import com.thailam.piggywallet.data.model.Wallet;

import java.util.List;

public interface WalletDAO {
    List<Wallet> getInitialWallets() throws Exception;

    List<Wallet> getSearchedWallets(String input) throws Exception;

    long saveWallet(Wallet wallet)throws Exception;
}
