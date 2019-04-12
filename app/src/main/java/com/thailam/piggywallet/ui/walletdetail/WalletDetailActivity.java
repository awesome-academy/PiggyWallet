package com.thailam.piggywallet.ui.walletdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.util.Constants;

public class WalletDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        //
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_wallet_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    public static Intent getWalletIdIntent(Context context, int walletId) {
        Intent intent = new Intent(context, WalletDetailActivity.class);
        intent.putExtra(Constants.EXTRA_WALLET_ID, walletId);
        return intent;
    }
}
