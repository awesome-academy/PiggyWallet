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

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Transaction;
import com.thailam.piggywallet.data.model.TransactionParent;

import java.util.ArrayList;
import java.util.Collections;
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
        viewHolder.bindData(mTransactionParents.get(i).getTransactions(), mViewPool, mListener);
    }

    @Override
    public int getItemCount() {
        return mTransactionParents == null ? 0 : mTransactionParents.size();
    }

    public void setTransactionParents(List<Transaction> transactions) {
        if (transactions == null) return;
        long commonDate = transactions.get(0).getDate(); // get first since all date will be common
        Collections.reverse(transactions);
        mTransactionParents.add(new TransactionParent(transactions, commonDate));
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;
        private LinearLayoutManager mLinearLayoutManager;

        private ViewHolder(Context context, View itemView) {
            super(itemView);
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

        private void bindData(List<Transaction> transactions,
                              RecyclerView.RecycledViewPool viewPool,
                              TransactionInnerAdapter.OnItemClickListener listener) {
            TransactionInnerAdapter transactionInnerAdapter =
                    new TransactionInnerAdapter(transactions, listener);
            if (mRecyclerView == null) return;
            mRecyclerView.setAdapter(transactionInnerAdapter);
            mRecyclerView.setRecycledViewPool(viewPool);
        }
    }
}
