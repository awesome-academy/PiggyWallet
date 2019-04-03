package com.thailam.piggywallet.ui.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends Fragment implements WalletContract.View {
    public static String FRAG_TAG = "WalletFragment";

    private WalletContract.Presenter mPresenter;

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
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            WalletLocalDataSource source = WalletLocalDataSource.getInstance(getContext());
            WalletRepository repo = WalletRepository.getInstance(source);
            mPresenter = new WalletPresenter(this, repo);
        }
        mPresenter.start();
    }
}
