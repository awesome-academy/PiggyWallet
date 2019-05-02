package com.thailam.piggywallet.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletDataSource;
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
        initPresenter();
        navigateToNextScreen();
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
            WalletDataSource source = WalletLocalDataSource.getInstance(getApplicationContext());
            WalletRepository repo = WalletRepository.getInstance(source);
            mPresenter = new SplashPresenter(this, repo);
        }
    }

    private void navigateToNextScreen() {
        Wallet lastVisitedWallet = mPresenter.getWalletFromSharedPrefs();
        if (lastVisitedWallet != null) {
            Intent intent = WalletDetailActivity.getIntent(this, lastVisitedWallet);
            startActivity(intent);
            finish();
        } else {
            mPresenter.start();
        }
    }
}
