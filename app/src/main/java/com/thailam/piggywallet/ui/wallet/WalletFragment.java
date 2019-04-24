package com.thailam.piggywallet.ui.wallet;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.ui.adapter.WalletAdapter;
import com.thailam.piggywallet.ui.walletdetail.WalletDetailActivity;
import com.thailam.piggywallet.util.Constants;

import java.util.List;
import java.util.Objects;

public class WalletFragment extends Fragment implements WalletContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    public static String TAG = "WalletFragment";

    private WalletContract.Presenter mPresenter;
    private WalletAdapter mWalletAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
    public void onStop() {
        if (mListener != null) {
            mListener.onStopFragment();
        }
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public void updateWallets(List<Wallet> wallets) {
        mWalletAdapter.setWallets(wallets);
    }

    @Override
    public void showErrorMessage(Exception e) {
        String errMsg = e == null ? Constants.UNKNOWN_ERROR : e.getMessage();
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void showProgressBar() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public WalletContract.Presenter getPresenter() {
        return mPresenter;
    }

    private void initAdapter() {
        mWalletAdapter = new WalletAdapter(wallet -> {
            Intent intent = WalletDetailActivity.getIntent(getContext(), wallet);
            startActivity(intent);
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
        /*
          Showing Swipe Refresh animation on activity create
          As animation won't start on onCreate, post runnable is used

          set refreshing = true and load all the guides again
         */
        mSwipeRefreshLayout.post(() -> { // load first time
            mPresenter.handleFirstSwipeRefresh();
        });
    }

    private void initRecyclerView() {
        if (getView() == null) return;
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_wallet_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mWalletAdapter);   // set guide adapter to view
    }

    public interface OnFragmentInteractionListener {
        void onStopFragment();
    }
}
