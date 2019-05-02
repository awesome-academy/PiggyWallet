package com.thailam.piggywallet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;

import java.util.Collections;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private List<Wallet> mWallets;

    public WalletAdapter(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_wallet_item, parent, false);
        return new ViewHolder(parent.getContext(), itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mWallets.get(i));
    }

    @Override
    public int getItemCount() {
        return mWallets == null ? 0 : mWallets.size();
    }

    public void setWallets(List<Wallet> wallets) {
        Collections.reverse(wallets);
        mWallets = wallets;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Wallet mWallet;
        private TextView mTxtTitle;
        private TextView mTxtSubtitle;
        private TextView mTxtAmount;
        private ImageButton mImgBtnFeatureImg;
        private OnItemClickListener mListener;

        private ViewHolder(Context context, View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mTxtTitle = itemView.findViewById(R.id.text_card_title);
            mTxtSubtitle = itemView.findViewById(R.id.text_card_subtitle);
            mTxtAmount = itemView.findViewById(R.id.text_card_balance);
            mImgBtnFeatureImg = itemView.findViewById(R.id.image_card_feature);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(mWallet);
        }

        private void bindView(final Wallet wallet) {
            mWallet = wallet;
            mTxtTitle.setText(wallet.getTitle());
            mTxtSubtitle.setText(wallet.getSubtitle());
            displayWalletAmount(wallet.getAmount());
        }

        private void displayWalletAmount(double amount) {
            int colorId = amount >= 0 ? R.color.color_positive_balance : R.color.color_negative_balance;
            mTxtAmount.setTextColor(itemView.getResources().getColor(colorId));
            mTxtAmount.setText(String.valueOf(amount));
        }
    }

    /**
     * The callback interface used by {@link com.thailam.piggywallet.ui.wallet.WalletFragment}
     * to perform on item click in the wallet recycler view in wallet activity
     */
    public interface OnItemClickListener {
        void onItemClick(Wallet wallet);
    }
}
