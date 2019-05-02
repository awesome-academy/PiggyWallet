package com.thailam.piggywallet.data.source.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferenceHelper {
    private static AppPreferenceHelper sInstance;
    private static SharedPreferences mPreferences;

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

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}