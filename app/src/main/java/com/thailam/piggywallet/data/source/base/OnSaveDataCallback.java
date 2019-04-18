package com.thailam.piggywallet.data.source.base;

public interface OnSaveDataCallback {
    void onSaveDataSuccess();

    void onSaveDataFail(String str);
}
