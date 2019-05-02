package com.thailam.piggywallet.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;
import com.thailam.piggywallet.data.source.base.LocalAsyncTask;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;
import com.thailam.piggywallet.data.source.local.dao.WalletDAO;
import com.thailam.piggywallet.data.source.local.dao.WalletDAOImpl;
import com.thailam.piggywallet.data.source.local.entry.WalletEntry;
import com.thailam.piggywallet.data.source.prefs.AppPreferenceHelper;

import java.util.List;

public class WalletLocalDataSource implements WalletDataSource {

    private static WalletLocalDataSource sInstance;
    private WalletDAO mWalletDAO;
    private AppPreferenceHelper mAppPreferenceHelper;

    private WalletLocalDataSource(WalletDAO walletDAO, AppPreferenceHelper appPreferenceHelper) {
        mWalletDAO = walletDAO;
        mAppPreferenceHelper = appPreferenceHelper;
    }

    public static WalletLocalDataSource getInstance(Context context, AppPreferenceHelper preferenceHelper) {
        if (sInstance == null) {
            synchronized (WalletLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new WalletLocalDataSource(WalletDAOImpl.getInstance(context),
                            preferenceHelper);
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getInitialWallets(boolean force, @NonNull final GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.getInitialWallets();
        }, callback);
        task.execute();
    }

    @Override
    public void getSearchedWallets(String input, @NonNull final GetWalletCallback callback) {
        LocalAsyncTask<Void, List<Wallet>> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.getSearchedWallets(input);
        }, callback);
        task.execute();
    }

    @Override
    public void saveWallet(Wallet wallet, @NonNull OnDataLoadedCallback<Long> callback) {
        LocalAsyncTask<Void, Long> task = new LocalAsyncTask<>(params -> {
            return mWalletDAO.saveWallet(wallet);
        }, callback);
        task.execute();
    }

    @Override
    public List<Wallet> getCachedWallets() {
        return null;
    }

    @Override
    public boolean putWalletToPrefs(Wallet wallet) {
        if (wallet == null || mAppPreferenceHelper.getPreferences() == null) return false;
        SharedPreferences.Editor editor = mAppPreferenceHelper.getPreferences().edit();
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
    public Wallet getWalletFromPrefs() {
        if (mAppPreferenceHelper.getPreferences() == null) return null;
        return new Wallet.Builder(mAppPreferenceHelper.getPreferences()).build();
    }
}
