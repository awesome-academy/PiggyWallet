package com.thailam.piggywallet.data.source.prefs;

import com.thailam.piggywallet.data.model.Wallet;

public interface PreferenceHelper {
    void saveWalletToSharedPref(Wallet wallet);

    Wallet getWalletFromSharedPref();
}
