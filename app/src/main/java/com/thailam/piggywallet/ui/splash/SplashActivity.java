package com.thailam.piggywallet.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.ui.wallet.WalletActivity;
import com.thailam.piggywallet.ui.walletdetail.WalletDetailActivity;
import com.thailam.piggywallet.util.Constants;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    public SplashActivity() {
        // required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isLastWalletVisitedExist()) initPresenter();
    }

    @Override
    public void openWalletDetail() {
        Intent intent = new Intent(this, WalletActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onLoadWalletError(Exception e) {
        String errMsg = e == null ? Constants.UNKNOWN_ERROR : e.getMessage();
        Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            WalletLocalDataSource source = WalletLocalDataSource.getInstance(getApplicationContext());
            WalletRepository repo = WalletRepository.getInstance(source);
            mPresenter = new SplashPresenter(this, repo);
        }
        mPresenter.start();
    }

    private boolean isLastWalletVisitedExist() {
        SharedPreferences pref = getSharedPreferences(Constants.PREF_WALLET, MODE_PRIVATE);
        if (pref != null) {
            Wallet wallet = new Wallet.Builder(pref).build();
            Intent intent = WalletDetailActivity.getIntent(this, wallet);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
