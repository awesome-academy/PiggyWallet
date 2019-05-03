package com.thailam.piggywallet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.TransactionParent;
import com.thailam.piggywallet.util.TypeFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class TransactionOuterAdapter extends RecyclerView.Adapter<TransactionOuterAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView.RecycledViewPool mViewPool;
    private List<TransactionParent> mTransactionParents;
    private TransactionInnerAdapter.OnItemClickListener mListener;

    public TransactionOuterAdapter(Context context, TransactionInnerAdapter.OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mTransactionParents = new ArrayList<>();
        mViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_wallet_detail_item, parent, false);
        return new ViewHolder(mContext, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mTransactionParents.get(i), mViewPool, mListener);
    }

    @Override
    public int getItemCount() {
        return mTransactionParents == null ? 0 : mTransactionParents.size();
    }

    public void setTransactionParents(List<TransactionParent> transactionParents) {
        if (transactionParents == null) return;
        mTransactionParents = transactionParents;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private RecyclerView mRecyclerView;
        private LinearLayoutManager mLinearLayoutManager;
        private TextView mTxtViewMonthYear;
        private TextView mTxtViewTotal;

        private ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTxtViewMonthYear = itemView.findViewById(R.id.text_view_transaction_date);
            mTxtViewTotal = itemView.findViewById(R.id.text_view_transaction_total);
            initRecyclerView(context);
        }

        private void bindData(TransactionParent transactionParents,
                              RecyclerView.RecycledViewPool viewPool,
                              TransactionInnerAdapter.OnItemClickListener listener) {
            List<Transaction> transactions = transactionParents.getTransactions();
            if (mRecyclerView == null || transactions == null) return;
            TransactionInnerAdapter transactionInnerAdapter = new TransactionInnerAdapter(transactions, listener);
            mRecyclerView.setAdapter(transactionInnerAdapter);
            mRecyclerView.setRecycledViewPool(viewPool);
            if (transactions.size() > 0) {
                String dateStr = TypeFormatUtils.getDateFromLong(transactions.get(0).getDate());
                mTxtViewMonthYear.setText(dateStr);
                initTotalBalance(transactions);
            }
        }

        private void initTotalBalance(List<Transaction> transactions) {
            double total = calcTotalBalance(transactions);
            int balanceColorResId = total > 0 ? R.color.color_positive_balance : R.color.color_negative_balance;
            int color = mContext.getResources().getColor(balanceColorResId);
            mTxtViewTotal.setTextColor(color);
            mTxtViewTotal.setText(String.valueOf(total));
        }

        private void initRecyclerView(Context context) {
            mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView = itemView.findViewById(R.id.recycler_view_wallet_detail_inside);
            if (mRecyclerView == null) return;
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                    mLinearLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
        }

        private double calcTotalBalance(List<Transaction> transactions) {
            double total = 0;
            for (Transaction transaction : transactions) {
                total += transaction.getAmount();
            }
            return total;
        }
    }
}
