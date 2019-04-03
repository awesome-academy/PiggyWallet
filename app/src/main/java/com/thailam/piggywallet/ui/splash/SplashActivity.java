package com.thailam.piggywallet.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.ui.wallet.WalletActivity;
import com.thailam.piggywallet.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    public SplashActivity() {
        // required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    public void changeScreen(List<Wallet> wallets) {
        Intent intent = WalletActivity.getWalletIntent(getApplicationContext(), wallets);
        startActivity(intent);
        finish();
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
}
