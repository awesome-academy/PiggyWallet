package com.thailam.piggywallet.ui.walletdetail;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.thailam.piggywallet.R;

public class WalletDetailDialogFragment extends DialogFragment {

    public static final String TAG = "WalletDetailDialogFragment";
    public static final String BUNDLE_THEME_RES_ID = "BUNDLE_THEME_RES_ID";

    public static WalletDetailDialogFragment newInstance(int themeResId) {
        WalletDetailDialogFragment frag = new WalletDetailDialogFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_THEME_RES_ID, themeResId);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.wallet_detail_overview_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void initViews() {
        if (getActivity() == null) return;
        Window window = getDialog().getWindow();
        if (window != null) { // transparent background for the dialog
            window.getDecorView().setOnTouchListener(
                    new SwipeDismissTouchListener(window.getDecorView(), this::dismiss));
            if (getView() == null) return;
            getView().findViewById(R.id.button_wallet_overview_dialog).setOnTouchListener(
                    new SwipeDismissTouchListener(window.getDecorView(), this::dismiss));
        }
    }
}
