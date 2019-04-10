package com.thailam.piggywallet.data.source.base;

public interface OnDataLoadedCallback<T> {
    void onDataLoaded(T data);

    void onDataNotAvailable(Exception e);
}
