package com.thailam.piggywallet.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.ui.addwallet.AddWalletActivity;

public class WalletActivity extends AppCompatActivity implements WalletFragment.OnFragmentInteractionListener {
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
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

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
    }

    private void initFab() {
        findViewById(R.id.fab_add).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddWalletActivity.class);
            startActivity(intent);
        });
    }

    private void addFirstFragment() {
        if (findViewById(R.id.content_container) != null) {
            // set up allFragment as the first fragment to appear on activity
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_container,
                    WalletFragment.newInstance(),
                    WalletFragment.TAG);
            fragmentTransaction.commit();
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
