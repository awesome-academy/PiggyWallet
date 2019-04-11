package com.thailam.piggywallet.ui.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.ui.adapter.WalletAdapter;

import java.util.List;
import java.util.Objects;

public class WalletFragment extends Fragment implements WalletContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    public static String TAG = "WalletFragment";

    private static boolean isLoading = false;

    private WalletContract.Presenter mPresenter;
    private WalletAdapter mWalletAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WalletFragment.
     */
    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        initPresenter();
        initAdapter();
        initSwipeRefresh();
        initRecyclerView();
    }

    @Override
    public void onRefresh() {
        mPresenter.getWallets();
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            WalletLocalDataSource source = WalletLocalDataSource.getInstance(getContext());
            WalletRepository repo = WalletRepository.getInstance(source);
            mPresenter = new WalletPresenter(this, repo);
        }
    }

    @Override
    public void toggleIsRefreshing() {
        if (isLoading) {
            mSwipeRefreshLayout.setRefreshing(false);
            isLoading = false;
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            isLoading = true;
        }
    }

    @Override
    public void updateWallets(List<Wallet> wallets) {
        mWalletAdapter.setWallets(wallets);
    }

    private void initAdapter() {
        mWalletAdapter = new WalletAdapter(walletId -> {
            // TODO: implement add to go to detail in wallet detail later task
        });
        List<Wallet> wallets = mPresenter.getCachedWallets();
        if (wallets != null) {
            mWalletAdapter.setWallets(wallets);
        }
    }

    private void initSwipeRefresh() {
        mSwipeRefreshLayout = Objects.requireNonNull(getView()).findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         *
         * set refreshing = true and load all the guides again
         */
        mSwipeRefreshLayout.post(() -> { // load first time
            mPresenter.handleFirstSwipeRefresh();
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_wallet_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mWalletAdapter);   // set guide adapter to view
    }
}
