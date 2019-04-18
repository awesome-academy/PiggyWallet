package com.thailam.piggywallet.ui.category;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.ui.base.BasePresenter;

import java.util.List;

public interface CategoryContract {
    interface View {
        void updateCategories(List<Category> categories);

        void showError(String msg);

        void toggleRefreshing();
    }

    interface Presenter extends BasePresenter {
        void getCategories();
    }
}
