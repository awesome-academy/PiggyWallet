package com.thailam.piggywallet.ui.addtransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import com.thailam.piggywallet.R;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog mDatePickerDialog;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddTransactionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        initToolbar();
        initDatePicker();
        initViews();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // TODO: handle on date pick
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_add_transaction);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog = new DatePickerDialog(this, AddTransactionActivity.this,
                year, month, dayOfMonth);
    }

    private void initViews() {
        Button btnChooseCategory = findViewById(R.id.button_add_transaction_category);
        btnChooseCategory.setOnClickListener(v -> {
            CustomDialog dialog = new CustomDialog(this);
            dialog.show();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT);
            }
        });
        findViewById(R.id.button_add_transaction_date).setOnClickListener(v -> {
            mDatePickerDialog.show();
        });
        findViewById(R.id.button_add_transaction_save).setOnClickListener(v -> {
            // TODO: save data from dialog to db
        });

    }
}
