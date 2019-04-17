package com.thailam.piggywallet.data.source.base;

import android.os.AsyncTask;

public class LocalAsyncTaskGet<P, T> extends AsyncTask<P, Void, T> {

    private OnDataLoadedCallback<T> mCallback;
    private DataHandler<P, T> mHandler;

    public LocalAsyncTaskGet(DataHandler<P, T> handler,
                             OnDataLoadedCallback<T> callback) {
        mHandler = handler;
        mCallback = callback;
    }

    @Override
    protected T doInBackground(P... params) {
        return mHandler.execute(params);
    }

    @Override
    protected void onPostExecute(T result) {
        if (result == null) {
            mCallback.onDataNotAvailable(null);
        } else {
            mCallback.onDataLoaded(result);
        }
    }

    public interface DataHandler<P, T> {
        T execute(P[] params);
    }
}
