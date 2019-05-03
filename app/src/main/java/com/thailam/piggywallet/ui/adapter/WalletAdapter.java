package com.thailam.piggywallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Wallet;

import java.util.Collections;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private List<Wallet> mWallets;
    private Wallet mRecentlyDeletedWallet;
    private int mRecentlyDeletedWalletPosition;
    private View mView;
    private DeleteWalletCallbacks mDeleteWalletCallbacks;

    public WalletAdapter(OnItemClickListener listener, DeleteWalletCallbacks deleteWalletCallbacks) {
        mListener = listener;
        mDeleteWalletCallbacks = deleteWalletCallbacks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_wallet_item, parent, false);
        mView = parent;
        return new ViewHolder(itemView, mListener);
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

    public void deleteWallet(int position) {
        mRecentlyDeletedWallet = mWallets.get(position);
        mRecentlyDeletedWalletPosition = position;
        mWallets.remove(position);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    private void showUndoSnackBar() {
        Snackbar snackbar = Snackbar.make(mView, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> {
            undoDelete();
        });
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                mDeleteWalletCallbacks.onDeleteWalletCallback(mRecentlyDeletedWallet);
            }
        });
        snackbar.show();
    }

    private void undoDelete() {
        mWallets.add(mRecentlyDeletedWalletPosition, mRecentlyDeletedWallet);
        notifyItemInserted(mRecentlyDeletedWalletPosition);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Wallet mWallet;
        private TextView mTxtTitle;
        private TextView mTxtSubtitle;
        private TextView mTxtAmount;
        private ImageView mImgBtnFeatureImg;
        private OnItemClickListener mListener;

        private ViewHolder(View itemView, OnItemClickListener listener) {
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
            displayFeatureImg();
        }

        private void displayFeatureImg() {
            int drawableId = mWallet.getIconUrl() == null ? R.drawable.ic_circle_icons_money :
                    Integer.valueOf(mWallet.getIconUrl());
            mImgBtnFeatureImg.setImageDrawable(itemView.getResources().getDrawable(drawableId));
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

    /**
     * The callback interface used by {@link com.thailam.piggywallet.ui.wallet.WalletFragment}
     * to perform actions when swipe to delete wallet
     */
    public interface DeleteWalletCallbacks {
        void onDeleteWalletCallback(Wallet wallet);
    }
}
