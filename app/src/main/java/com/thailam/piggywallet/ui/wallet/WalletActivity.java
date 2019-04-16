package com.thailam.piggywallet.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.ui.addwallet.AddWalletActivity;
import com.thailam.piggywallet.ui.walletdetail.WalletDetailActivity;

public class WalletActivity extends AppCompatActivity implements WalletFragment.OnFragmentInteractionListener {

    private SearchView mSearchView;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initBottomNav();
        initToolbar();
        initFab();
        addFirstFragment();
        initSearchView();
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isIconified()) {
            super.onBackPressed();
        } else {
            closeSearchView();
        }
    }

    @Override
    public void onStopFragment() {
        closeSearchView();
    }

    private void initBottomNav() {
        BottomNavigationView nav = findViewById(R.id.bottom_nav_view);
        nav.setOnNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.nav_home:
                    Fragment f = getSupportFragmentManager().findFragmentByTag(WalletFragment.TAG);
                    if (f instanceof WalletFragment) break; // if fragment existed -> break;
                    mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.content_container,
                            WalletFragment.newInstance(),
                            WalletFragment.TAG);
                    mFragmentTransaction.commit();
                    break;
                case R.id.nav_settings:
                    // TODO: implement later
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

    private void initSearchView() {
        mSearchView = findViewById(R.id.search_view_wallet);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String inputStr) {
                mSearchView.clearFocus();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(WalletFragment.TAG);
                if (fragment instanceof WalletFragment) {
                    ((WalletFragment) fragment).getPresenter().searchWallets(inputStr);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(() -> {
            refreshWallets();
            return false;
        });
    }

    private void refreshWallets() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(WalletFragment.TAG);
        if (fragment instanceof WalletFragment) {
            ((WalletFragment) fragment).getPresenter().getWallets();
        }
    }

    private void closeSearchView() {
        mSearchView.clearFocus();
        mSearchView.setQuery("", false);
        mSearchView.setIconified(true);
    }
}
