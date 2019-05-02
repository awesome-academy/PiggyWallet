package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.prefs.AppPreferenceHelper;
import com.thailam.piggywallet.data.source.prefs.PreferenceHelper;

public class PrefsRepository implements PreferenceHelper {
    private static PrefsRepository sInstance;
    private PreferenceHelper mAppPreferenceHelper;

    private PrefsRepository(@NonNull PreferenceHelper preferenceHelper) {
        mAppPreferenceHelper = preferenceHelper;
    }

    public static PrefsRepository getInstance(@NonNull PreferenceHelper preferenceHelper) {
        if (sInstance == null) {
            sInstance = new PrefsRepository(preferenceHelper);
        }
        return sInstance;
    }

    @Override
    public boolean saveWalletToSharedPref(Wallet wallet) {
        return mAppPreferenceHelper.saveWalletToSharedPref(wallet);
    }

    @Override
    public Wallet getWalletFromSharedPref() {
        return mAppPreferenceHelper.getWalletFromSharedPref();
    }
}
