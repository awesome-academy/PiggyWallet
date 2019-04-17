package com.thailam.piggywallet.data.source.base;

import android.os.AsyncTask;

public class LocalAsyncTaskAdd<P> extends AsyncTask<P, Void, Exception> {

    private OnSaveDataCallback mCallback;
    private DataHandler<P> mHandler;

    public LocalAsyncTaskAdd(DataHandler<P> handler,
                             OnSaveDataCallback callback) {
        mHandler = handler;
        mCallback = callback;
    }

    @Override
    protected Exception doInBackground(P... params) {
        return mHandler.execute(params);
    }

    @Override
    protected void onPostExecute(Exception e) {
        if (e == null) {
            mCallback.onSaveDataSuccess();
        } else {
            mCallback.onSaveDataFail(e);
        }
    }

    public interface DataHandler<P> {
        Exception execute(P[] params);
    }
}
