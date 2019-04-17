package com.thailam.piggywallet.ui.addtransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.TransactionRepository;
import com.thailam.piggywallet.data.source.local.TransactionLocalDataSource;
import com.thailam.piggywallet.ui.category.CategoryDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, CategoryDialog.OnCategoryChosen, AddTransactionContract.View {

    private AddTransactionContract.Presenter mPresenter;
    private DatePickerDialog mDatePickerDialog;
    private Category mCategory;
    private EditText mEditTextNote;
    private EditText mEditTextAmount;
    private Button mBtnChooseCategory, mBtnChooseDate;
    private long mDate;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddTransactionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        initPresenter();
        initToolbar();
        initViews();
        initDatePicker();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "-" + month + "-" + year;
        String errMsg = "Error choosing date";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YY");
        try {
            Date d = format.parse(date);
            mDate = d.getTime();
            mBtnChooseDate.setText(date);
        } catch (ParseException e) {
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onGetCategory(Category category) {
        mCategory = category;
        mBtnChooseCategory.setText(category.getName());
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            TransactionDataSource source = TransactionLocalDataSource.getInstance(getApplicationContext());
            TransactionRepository repo = TransactionRepository.getInstance(source);
            mPresenter = new AddTransactionPresenter(this, repo);
        }
    }

    @Override
    public void showSuccess() {
        onBackPressed(); // move back to prev screen
        Toast.makeText(this, "Added transaction", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, "Err:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_add_transaction_category:
                CustomDialog dialog = new CustomDialog(this);
                dialog.show();
                break;
            case R.id.button_add_transaction_date:
                mDatePickerDialog.show();
                break;
            case R.id.button_add_transaction_save:
                handleSaveTransaction();
                break;
        }
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
        mEditTextAmount = findViewById(R.id.edit_text_add_transaction_amount);
        mEditTextNote = findViewById(R.id.edit_text_add_transaction_note);
        mBtnChooseCategory = findViewById(R.id.button_add_transaction_category);
        mBtnChooseDate = findViewById(R.id.button_add_transaction_date);
    }

    private void handleSaveTransaction() {
        if (!validateInputs()) return;
        int defaultId = 0;
        String note = mEditTextNote.getText().toString();
        double amount = Double.valueOf(mEditTextAmount.getText().toString());
        int categoryId = mCategory.getId();
        long date = (mDate == 0) ? System.currentTimeMillis() : mDate;
        Transaction transaction = new Transaction(defaultId, note, amount, categoryId, date);
        mPresenter.saveTransaction(transaction);
    }

    private boolean validateInputs() {
        if (mEditTextAmount.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mCategory == null) {
            Toast.makeText(this, "Please choose a category", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
