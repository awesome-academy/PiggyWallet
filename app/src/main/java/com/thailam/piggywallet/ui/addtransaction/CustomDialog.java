package com.thailam.piggywallet.ui.addtransaction;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.thailam.piggywallet.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private FloatingActionButton mFabFood, mFabEntertainment, mFabBill, mFabHealth;
    private TextView mTxtViewFood;

    CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        if (getWindow() != null) { // transparent background for the dialog
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        initFabs();
        ConstraintLayout constraintLayout = findViewById(R.id.dialog_container);
        constraintLayout.setOnClickListener(v -> { // on click outside -> cancel
            this.cancel();
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            default:
                break;
        }
    }

    private void initFabs() {
        mFabFood = findViewById(R.id.fab_transaction_food);
        mFabEntertainment = findViewById(R.id.fab_transaction_entertainment);
        mFabBill = findViewById(R.id.fab_transaction_bill);
        mFabHealth = findViewById(R.id.fab_transaction_health);
        mFabFood.setOnClickListener(this);
        mFabEntertainment.setOnClickListener(this);
        mFabBill.setOnClickListener(this);
        mFabHealth.setOnClickListener(this);
        // TODO: implement more
        mTxtViewFood = findViewById(R.id.text_view_food);
        mTxtViewFood.setVisibility(View.INVISIBLE);
        openFabs();
    }

    private void openFabs() {
        showFabs();
        final float translationXY = 150;
        mFabFood.animate().translationX(translationXY);
        mFabEntertainment.animate().translationY(translationXY);
        mTxtViewFood.animate().translationX(translationXY);
        mFabBill.animate().translationX(-translationXY);
        mFabHealth.animate().translationY(-translationXY);
        mTxtViewFood.setVisibility(View.VISIBLE);
    }

    private void showFabs() {
        mFabFood.show();
        mFabEntertainment.show();
        mFabBill.show();
        mFabHealth.show();
    }
}
