package com.thailam.piggywallet.ui.category;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.CategoryDataSource;
import com.thailam.piggywallet.data.source.CategoryRepository;
import com.thailam.piggywallet.data.source.local.CategoryLocalDataSource;
import com.thailam.piggywallet.ui.adapter.CategoryAdapter;

import java.util.List;

public class CategoryDialog extends Dialog implements CategoryContract.View {
    private CategoryAdapter mCategoryAdapter;
    private CategoryContract.Presenter mPresenter;
    private OnCategoryChosen mOnCategoryChosen;

    public CategoryDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.category_dialog);
        ConstraintLayout constraintLayout = findViewById(R.id.dialog_container);
        constraintLayout.setOnClickListener(v -> { // on click outside -> cancel
            this.cancel();
        });
        initPresenter();
        initAdapter();
        initRecyclerView();
    }

    @Override
    public void updateCategories(List<Category> categories) {
        mCategoryAdapter.setCategories(categories);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), "Error: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toggleRefreshing() {

    }

    public void setCategoryDialogResult(OnCategoryChosen onCategoryChosen) {
        mOnCategoryChosen = onCategoryChosen;
    }

    private void initPresenter() {
        if (mPresenter == null) {
            CategoryDataSource source = CategoryLocalDataSource.getInstance(getContext());
            CategoryRepository repo = CategoryRepository.getInstance(source);
            mPresenter = new CategoryPresenter(this, repo);
        }
        mPresenter.start(); // get the categories
    }

    private void initAdapter() {
        mCategoryAdapter = new CategoryAdapter(category -> {
            if (mOnCategoryChosen != null) {
                mOnCategoryChosen.onGetCategory(category);
            }
            this.dismiss();
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =findViewById(R.id.recycler_view_custom_dialog);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mCategoryAdapter);
    }

    public interface OnCategoryChosen {
        void onGetCategory(Category category);
    }
}
