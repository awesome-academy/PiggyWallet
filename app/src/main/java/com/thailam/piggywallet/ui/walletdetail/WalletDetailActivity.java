package com.thailam.piggywallet.ui.walletdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.TransactionParent;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionDataSource;
import com.thailam.piggywallet.data.source.TransactionRepository;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.TransactionLocalDataSource;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.data.source.prefs.AppPreferenceHelper;
import com.thailam.piggywallet.ui.adapter.TransactionOuterAdapter;
import com.thailam.piggywallet.ui.addtransaction.TransactionActivity;
import com.thailam.piggywallet.ui.wallet.WalletActivity;
import com.thailam.piggywallet.util.Constants;
import com.thailam.piggywallet.util.TypeFormatUtils;

import java.util.List;

public class WalletDetailActivity extends AppCompatActivity implements WalletDetailContract.View,
        View.OnClickListener {
    private static final String EXTRA_WALLET = "com.thailam.piggywallet.extras.EXTRA_WALLET";
    private static final int REQUEST_CODE_ADD_TRANSACTION = 1;
    private Wallet mWallet;

    private WalletDetailContract.Presenter mPresenter;
    private TransactionOuterAdapter mTransactionOuterAdapter;

    private TextView mTxtBalance;
    private TextView mTxtInflow;
    private TextView mTxtOutflow;

    public static Intent getIntent(Context context, Wallet wallet) {
        Intent intent = new Intent(context, WalletDetailActivity.class);
        intent.putExtra(EXTRA_WALLET, wallet);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        getWalletExtra();
        initPresenter();
        initViews();
        initToolbar();
        initAdapter();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.getInitialTransactions(true, mWallet.getId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) mPresenter.saveWalletToSharedPref(mWallet);
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            TransactionDataSource transactionDataSource = TransactionLocalDataSource.getInstance(this);
            TransactionRepository transactionRepo = TransactionRepository.getInstance(transactionDataSource);
            AppPreferenceHelper helper = AppPreferenceHelper.getInstance(this, Constants.PREF_WALLET);
            WalletLocalDataSource walletDataSource = WalletLocalDataSource.getInstance(this, helper);
            WalletRepository walletRepository = WalletRepository.getInstance(walletDataSource);
            mPresenter = new WalletDetailPresenter(this, transactionRepo, walletRepository);
        }
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.progress_bar_wallet_detail).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.progress_bar_wallet_detail).setVisibility(View.GONE);
    }

    @Override
    public void updateTransactions(List<TransactionParent> transactionParents) {
        mTransactionOuterAdapter.setTransactionParents(transactionParents);
    }

    @Override
    public void showNoTransactionData() {
        findViewById(R.id.text_view_no_transaction).setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveWalletToSharedPrefFailed() {
        String errMsg = getResources().getString(R.string.save_wallet_to_prefs_error);
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_transaction:
                startActivityForResult(TransactionActivity.getIntent(getApplicationContext(), mWallet),
                        REQUEST_CODE_ADD_TRANSACTION);
                break;
            case R.id.button_overview_staticstic:
                WalletDetailDialogFragment dialog =
                        WalletDetailDialogFragment.newInstance(R.style.FullScreenDialogStyle);
                dialog.show(getSupportFragmentManager(), WalletDetailDialogFragment.TAG);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TRANSACTION) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    Toast.makeText(this, Constants.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                    return;
                }
                String resultAmount = data.getStringExtra(Constants.EXTRA_EDIT_TEXT_AMOUNT);
                String resultType = data.getStringExtra(Constants.EXTRA_CATEGORY_TYPE);
                updateOverviewInfo(resultAmount, resultType);
            }
        }
    }

    private void updateOverviewInfo(String resultAmount, String resultType) {
        double totalBalanceChange = Double.parseDouble(resultAmount);
        double finalFlowChange;
        double finalAmount = totalBalanceChange + mWallet.getAmount();
        mTxtBalance.setText(TypeFormatUtils.doubleToMoneyString(finalAmount));
        if (resultType.equals(Constants.CATEGORY_INFLOW)) {
            finalFlowChange = totalBalanceChange + mWallet.getInflow();
            mTxtInflow.setText(TypeFormatUtils.doubleToMoneyString(finalFlowChange));
        } else {
            finalFlowChange = totalBalanceChange + mWallet.getInflow();
            mTxtOutflow.setText(TypeFormatUtils.doubleToMoneyString(finalFlowChange));
        }
    }

    private void getWalletExtra() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                mWallet = intent.getExtras().getParcelable(EXTRA_WALLET);
            }
        }
    }

    private void initViews() {
        findViewById(R.id.fab_add_transaction).setOnClickListener(this);
        findViewById(R.id.button_overview_staticstic).setOnClickListener(this);
        TextView txtWalletName = findViewById(R.id.text_view_overview_title);
        txtWalletName.setText(mWallet.getTitle());
        mTxtBalance = findViewById(R.id.text_view_overview_balance_amount);
        mTxtInflow = findViewById(R.id.text_view_overview_inflow_amount);
        mTxtOutflow = findViewById(R.id.text_view_overview_outflow_amount);
        mTxtBalance.setText(TypeFormatUtils.doubleToMoneyString(mWallet.getAmount()));
        mTxtInflow.setText(TypeFormatUtils.doubleToMoneyString(mWallet.getInflow()));
        mTxtOutflow.setText(TypeFormatUtils.doubleToMoneyString(mWallet.getOutflow()));
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_wallet_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 0) {
                Intent intent = new Intent(this, WalletActivity.class);
                startActivity(intent);
            } else {
                onBackPressed();
            }
        });
    }

    private void initAdapter() {
        mTransactionOuterAdapter = new TransactionOuterAdapter(this, transaction -> {
            // TODO: implement later
        });
        mPresenter.getInitialTransactions(false, mWallet.getId());
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_wallet_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mTransactionOuterAdapter);   // set guide adapter to view
    }
}
