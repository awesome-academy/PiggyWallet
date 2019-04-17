package com.thailam.piggywallet.ui.walletdetail;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.thailam.piggywallet.R;

public class WalletDetailOverviewDialog extends Dialog {
    WalletDetailOverviewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_detail_overview_dialog);
        initViews();
    }

    private void initViews() {
        Window window = getWindow();
        if (window != null) { // transparent background for the dialog
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getDecorView().setOnTouchListener(
                    new SwipeDismissTouchListener(window.getDecorView(), this::dismiss));
            findViewById(R.id.button_wallet_overview_dialog).setOnTouchListener(
                    new SwipeDismissTouchListener(window.getDecorView(), this::dismiss));
        }
    }
}
