package com.thailam.piggywallet.ui.addwallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;
import com.thailam.piggywallet.data.source.WalletRepository;
import com.thailam.piggywallet.data.source.local.WalletLocalDataSource;
import com.thailam.piggywallet.util.Constants;

public class AddWalletActivity extends AppCompatActivity implements AddWalletContract.View {

    private AddWalletContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        initPresenter();
        initToolbar();
        initViews();
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            WalletLocalDataSource source = WalletLocalDataSource.getInstance(this);
            WalletRepository repo = WalletRepository.getInstance(source);
            mPresenter = new AddWalletPresenter(this, repo);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_add_wallet);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        findViewById(R.id.button_add_wallet_save).setOnClickListener(v -> {
            Wallet wallet = createWalletFromInputs();
            mPresenter.saveWallet(wallet);
        });
    }

    private Wallet createWalletFromInputs() {
        EditText mWalletTitle = findViewById(R.id.edit_text_add_wallet_title);
        EditText mWalletSubtitle = findViewById(R.id.edit_text_add_wallet_subtitle);
        EditText mWalletBalance = findViewById(R.id.edit_text_add_wallet_balance);
        double amount = Double.parseDouble(mWalletBalance.getText().toString());
        return new Wallet.Builder()
                .setTitle(mWalletTitle.getText().toString())
                .setSubtitle(mWalletSubtitle.getText().toString())
                .setAmount(amount).build();
    }

    @Override
    public void showAddWalletSuccess() {
        finish();
        String successMsg = getResources().getString(R.string.prompt_wallet_created);
        Toast.makeText(this, successMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddWalletFail(Exception e) {
        String errMsg = e == null ? Constants.UNKNOWN_ERROR : e.getMessage();
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}
