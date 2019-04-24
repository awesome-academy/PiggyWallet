package com.thailam.piggywallet.ui.walletdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.TransactionRepository;
import com.thailam.piggywallet.data.source.local.TransactionLocalDataSource;
import com.thailam.piggywallet.ui.adapter.TransactionOuterAdapter;
import com.thailam.piggywallet.ui.addtransaction.TransactionActivity;
import com.thailam.piggywallet.util.TypeFormatUtils;

import java.util.List;

public class WalletDetailActivity extends AppCompatActivity implements WalletDetailContract.View,
        View.OnClickListener {
    private static final String EXTRA_WALLET = "com.thailam.piggywallet.extras.EXTRA_WALLET";
    private Wallet mWallet;

    private WalletDetailContract.Presenter mPresenter;
    private TransactionOuterAdapter mTransactionOuterAdapter;

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
    public void initPresenter() {
        if (mPresenter == null) {
            TransactionLocalDataSource source = TransactionLocalDataSource.getInstance(this);
            TransactionRepository repo = TransactionRepository.getInstance(source);
            mPresenter = new WalletDetailPresenter(this, repo);
        }
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.progress_bar_wallet_detail).setVisibility(View.VISIBLE);
        findViewById(R.id.image_view_no_transactions_wallet_detail).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.progress_bar_wallet_detail).setVisibility(View.GONE);
        findViewById(R.id.image_view_no_transactions_wallet_detail).setVisibility(View.GONE);
    }

    @Override
    public void updateTransactions(List<Transaction> transactions) {
        mTransactionOuterAdapter.setTransactionParents(transactions);
    }

    @Override
    public void showNoTransactionData() {
        findViewById(R.id.text_view_no_transaction).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_transaction:
                startActivity(TransactionActivity.getIntent(getApplicationContext(), mWallet));
                break;
            case R.id.button_overview_staticstic:
                WalletDetailDialogFragment dialog =
                        WalletDetailDialogFragment.newInstance(R.style.FullScreenDialogStyle);
                dialog.show(getSupportFragmentManager(), WalletDetailDialogFragment.TAG);
                break;
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
        TextView txtBalance = findViewById(R.id.text_view_overview_balance_amount);
        TextView txtInflow = findViewById(R.id.text_view_overview_inflow_amount);
        TextView txtOutflow = findViewById(R.id.text_view_overview_outflow_amount);
        txtBalance.setText(TypeFormatUtils.doubleToMoneyString(mWallet.getAmount()));
        txtInflow.setText(TypeFormatUtils.doubleToMoneyString(mWallet.getInflow()));
        txtOutflow.setText(TypeFormatUtils.doubleToMoneyString(mWallet.getOutflow()));
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_wallet_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


    private void initAdapter() {
        mTransactionOuterAdapter = new TransactionOuterAdapter(this, transaction -> {
            // TODO: implement later
        });
        mPresenter.getInitialTransactions(mWallet.getId());
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_wallet_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mTransactionOuterAdapter);   // set guide adapter to view
    }
}
