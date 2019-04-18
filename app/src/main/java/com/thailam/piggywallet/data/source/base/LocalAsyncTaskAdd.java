package com.thailam.piggywallet.data.source.base;

import android.os.AsyncTask;

public class LocalAsyncTaskAdd extends AsyncTask<Void, Void, String> {

    private OnSaveDataCallback mCallback;
    private DataHandler mHandler;

    public LocalAsyncTaskAdd(DataHandler handler,
                             OnSaveDataCallback callback) {
        mHandler = handler;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return mHandler.execute();
    }

    @Override
    protected void onPostExecute(String str) {
        if (str == null) {
            mCallback.onSaveDataSuccess();
        } else {
            mCallback.onSaveDataFail(str);
        }
    }


    public interface DataHandler {
        String execute();
    }
}
