package com.thailam.piggywallet.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.addwallet.AddWalletActivity;
import com.thailam.piggywallet.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {

    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        //
        initBottomNav();
        initToolbar();
        initFab();
        addFirstFragment();
    }

    private void initBottomNav() {
        BottomNavigationView nav = findViewById(R.id.bottom_nav_view);
        nav.setOnNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.nav_home:
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.content_container,
                            WalletFragment.newInstance(),
                            WalletFragment.TAG);
                    mFragmentTransaction.commit();
                    break;
                case R.id.nav_settings:
                    // TODO: add fragment transaction when do settings screen task
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
    }


    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddWalletActivity.class);
            startActivity(intent);
        });
    }

    private void addFirstFragment() {
        if (findViewById(R.id.content_container) != null) {
            // set up allFragment as the first fragment to appear on activity
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.add(R.id.content_container,
                    WalletFragment.newInstance(),
                    WalletFragment.TAG);
            mFragmentTransaction.commit();
        }
    }

    public static Intent getWalletIntent(Context context, List<Wallet> wallets) {
        Intent intent = new Intent(context, WalletActivity.class);
        if (wallets != null) {
            ArrayList<Wallet> list = new ArrayList<>(wallets);
            intent.putParcelableArrayListExtra(Constants.EXTRA_WALLETS, list);
        }
        return intent;
    }
}
