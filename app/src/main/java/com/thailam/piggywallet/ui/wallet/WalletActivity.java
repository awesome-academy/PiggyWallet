package com.thailam.piggywallet.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {
    public static Intent getWalletIntent(Context context, List<Wallet> wallets) {
        Intent intent = new Intent(context, WalletActivity.class);
        if (wallets != null) {
            ArrayList<Wallet> list = new ArrayList<>(wallets);
            intent.putParcelableArrayListExtra(Constants.EXTRA_WALLETS, list);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
    }
}
