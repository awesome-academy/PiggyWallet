package com.thailam.piggywallet.data.source;

import android.support.annotation.NonNull;

import com.thailam.piggywallet.data.model.Category;
import com.thailam.piggywallet.data.source.base.OnDataLoadedCallback;

import java.util.List;

public interface CategoryDataSource {
    void getCategories(@NonNull OnDataLoadedCallback<List<Category>> callback);
}
