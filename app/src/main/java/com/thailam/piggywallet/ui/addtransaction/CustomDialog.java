package com.thailam.piggywallet.ui.addtransaction;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.thailam.piggywallet.R;
import com.thailam.piggywallet.ui.adapter.CategoryAdapter;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private CategoryAdapter mCategoryAdapter;

    CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        initAdapter();
        initRecyclerView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.layout.custom_dialog:
                this.cancel();
                break;
            default:
                break;
        }
    }

    private void initAdapter() {
        mCategoryAdapter = new CategoryAdapter(category -> {
            // TODO: handle on click category in next task
        });
        // mCategoryAdapter.setCategories(categories);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =findViewById(R.id.recycler_view_custom_dialog);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mCategoryAdapter);   // set guide adapter to view
    }
}
