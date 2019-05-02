package com.thailam.piggywallet.data.source.prefs;

import com.thailam.piggywallet.data.model.Wallet;

public interface PreferenceHelper {
    boolean saveWalletToSharedPref(Wallet wallet);

    Wallet getWalletFromSharedPref();
}
