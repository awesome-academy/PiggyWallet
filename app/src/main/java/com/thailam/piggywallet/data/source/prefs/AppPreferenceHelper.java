package com.thailam.piggywallet.data.source.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

public class AppPreferenceHelper implements PreferenceHelper {
    private static AppPreferenceHelper sInstance;
    private SharedPreferences mPreferences;

    private AppPreferenceHelper(Context context, String prefFileName) {
        mPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public static AppPreferenceHelper getInstance(Context context, String prefFileName) {
        if (sInstance == null) {
            synchronized (AppPreferenceHelper.class) {
                if (sInstance == null) {
                    sInstance = new AppPreferenceHelper(context, prefFileName);
                }
            }
        }
        return sInstance;
    }

    @Override
    public boolean saveWalletToSharedPref(Wallet wallet) {
        if (wallet == null || mPreferences == null) return false;
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(WalletEntry.ID, wallet.getId())
                .putString(WalletEntry.TITLE, wallet.getTitle())
                .putString(WalletEntry.SUBTITLE, wallet.getTitle())
                .putFloat(WalletEntry.AMOUNT, (float) wallet.getAmount())
                .putFloat(WalletEntry.INFLOW, (float) wallet.getInflow())
                .putFloat(WalletEntry.OUTFLOW, (float) wallet.getOutflow())
                .putString(WalletEntry.ICON, wallet.getIconUrl())
                .putLong(WalletEntry.CREATED_AT, wallet.getCreatedAt())
                .putLong(WalletEntry.UPDATED_AT, wallet.getUpdatedAt());
        return editor.commit();
    }

    @Override
    public Wallet getWalletFromSharedPref() {
        if (mPreferences == null) return null;
        return new Wallet.Builder(mPreferences).build();
    }
}
