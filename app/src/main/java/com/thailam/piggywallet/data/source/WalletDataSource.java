package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

import java.util.List;

/**
 * Main entry point for accessing wallet data
 */
public interface WalletDataSource {

    interface GetWalletCallback extends OnDataLoadedCallback<List<Wallet>> { }

    void getInitialWallets(@NonNull GetWalletCallback callback);

    void getSearchedWallets(String input, @NonNull GetWalletCallback callback);

    boolean addWallet(Wallet wallet);

    List<Wallet> getCachedWallets();
}
