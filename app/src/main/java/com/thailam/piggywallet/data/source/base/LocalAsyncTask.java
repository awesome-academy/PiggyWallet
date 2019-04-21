package com.thailam.piggywallet.data.source.base;

import android.os.AsyncTask;

import java.sql.SQLException;

public class LocalAsyncTask<P, T> extends AsyncTask<P, Void, T> {
    private OnDataLoadedCallback<T> mCallback;
    private DataHandler<P, T> mHandler;
    private SQLException mSQLException;

    public LocalAsyncTask(DataHandler<P, T> handler,
                          OnDataLoadedCallback<T> callback) {
        mHandler = handler;
        mCallback = callback;
        mSQLException = null;
    }

    @Override
    protected T doInBackground(P... params) {
        try {
            return mHandler.execute(params);
        } catch (SQLException e) {
            mSQLException = e;
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(T result) {
        if (result == null) {
            mCallback.onDataNotAvailable(mSQLException);
        } else {
            mCallback.onDataLoaded(result);
        }
    }

    public interface DataHandler<P, T> {
        T execute(P[] params) throws SQLException;
    }
}
