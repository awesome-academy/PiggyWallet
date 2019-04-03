package com.thailam.piggywallet.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.ui.addwallet.AddWalletActivity;
import com.thailam.piggywallet.util.Constants;

import java.util.List;

public class WalletActivity extends AppCompatActivity {

    private FragmentTransaction mFragmentTransaction;

    private FloatingActionButton mFab;

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
                    String fragTag = Constants.FRAG_TAG_WALLET;
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragTag);
                    if (fragment == null) {
                        List<Wallet> walletsList = getWalletsIntent();
                        mFragmentTransaction.add(R.id.content_container,
                                WalletFragment.newInstance(walletsList),
                                fragTag);
                        mFragmentTransaction.addToBackStack(null);
                    } else {
                        mFragmentTransaction.replace(R.id.content_container, fragment, fragTag);
                    }
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
        mFab = findViewById(R.id.fab_add);
        mFab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddWalletActivity.class);
            startActivity(intent);
        });
    }

    private void addFirstFragment() {
        if (findViewById(R.id.content_container) != null) {
            List<Wallet> walletsList = getWalletsIntent();
            String fragTag = WalletFragment.FRAG_TAG;
            // set up allFragment as the first fragment to appear on activity
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.add(R.id.content_container,
                    WalletFragment.newInstance(walletsList),
                    fragTag);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();
        }
    }

    private List<Wallet> getWalletsIntent() {
        Intent intent = getIntent();
        if (intent == null) return null;
        return intent.getParcelableArrayListExtra(Constants.EXTRA_WALLETS);
    }
}
