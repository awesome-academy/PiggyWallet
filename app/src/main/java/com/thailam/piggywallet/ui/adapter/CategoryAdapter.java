package com.thailam.piggywallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.util.Constants;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private OnItemClickListener mListener;
    private List<Category> mCategories;

    public CategoryAdapter(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_custom_dialog_category, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mCategories.get(i));
    }

    @Override
    public int getItemCount() {
        return mCategories == null ? 0 : mCategories.size();
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTxtViewTitle;
        private ImageView mImageViewIcon;
        private OnItemClickListener mListener;
        private Category mCategory;

        private ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mTxtViewTitle = itemView.findViewById(R.id.text_view_dialog_title);
            mImageViewIcon = itemView.findViewById(R.id.image_view_dialog_icon);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(mCategory);
        }

        private void bindView(Category category) {
            mCategory = category;
            int drawableId = R.drawable.ic_circle_icons_money;
            mImageViewIcon.setImageDrawable(itemView.getResources().getDrawable(drawableId));
            displayBackground();
            mTxtViewTitle.setText(mCategory.getName());
        }

        private void displayBackground() {
            int bgColorId =  mCategory.getType().equals(Constants.CATEGORY_INFLOW) ? R.color.color_positive_balance :
                    R.color.color_negative_balance;
            itemView.setBackgroundColor(itemView.getResources().getColor(bgColorId));
        }
    }

    /**
     * The callback interface used by {@link com.thailam.piggywallet.ui.addtransaction.TransactionActivity}
     * to perform on click category item in category dialog
     */
    public interface OnItemClickListener {
        void onItemClick(Category category);
    }
}
