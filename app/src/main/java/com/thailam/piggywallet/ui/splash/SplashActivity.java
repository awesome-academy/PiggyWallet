package com.thailam.piggywallet.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.ui.wallet.WalletActivity;

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
        Intent intent = new Intent(this, WalletActivity.class);
        if (wallets != null) {
            for (Wallet wallet : wallets) {
                String tag = "wallets" + wallet.getId();
                intent.putExtra(tag, wallet);
            }
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new SplashPresenter(this, WalletRepository.getInstance(WalletLocalDataSource.getInstance(getApplicationContext())));
        }
        mPresenter.start();
    }
}
