package com.thailam.piggywallet.data.model;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

public class Wallet implements Parcelable {

    public static final Parcelable.Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel source) {
            return new Wallet(source);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[0];
        }
    };

    private int mId;
    private String mTitle;
    private String mSubtitle;
    private String mIconUrl;
    private double mAmount;
    private double mInflow;
    private double mOutflow;
    private long mCreatedAt;
    private long mUpdatedAt;

    private Wallet(Builder builder) {
        mId = builder.mId;
        mTitle = builder.mTitle;
        mSubtitle = builder.mSubtitle;
        mIconUrl = builder.mIconUrl;
        mAmount = builder.mAmount;
        mInflow = builder.mInflow;
        mOutflow = builder.mOutflow;
        mCreatedAt = builder.mCreatedAt;
        mUpdatedAt = builder.mUpdatedAt;
    }

    public Wallet(Parcel source) {
        mId = source.readInt();
        mTitle = source.readString();
        mSubtitle = source.readString();
        mIconUrl = source.readString();
        mAmount = source.readDouble();
        mInflow = source.readDouble();
        mOutflow = source.readDouble();
        mCreatedAt = source.readLong();
        mUpdatedAt = source.readLong();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
    }

    public double getInflow() {
        return mInflow;
    }

    public void setInflow(double inflow) {
        mInflow = inflow;
    }

    public double getOutflow() {
        return mOutflow;
    }

    public void setOutflow(double outflow) {
        mOutflow = outflow;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    public long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mSubtitle);
        dest.writeString(mIconUrl);
        dest.writeDouble(mAmount);
        dest.writeDouble(mInflow);
        dest.writeDouble(mOutflow);
        dest.writeLong(mCreatedAt);
        dest.writeLong(mUpdatedAt);
    }

    //Builder class
    public static class Builder {
        private static final String DEFAULT_STR = null;
        private static final int DEFAULT_NUM = 0;
        private int mId;
        private String mTitle;
        private String mSubtitle;
        private String mIconUrl;
        private double mAmount;
        private double mInflow;
        private double mOutflow;
        private long mCreatedAt;
        private long mUpdatedAt;

        public Builder() {
            // required empty constructor
        }

        public Builder(Cursor cursor) {
            int indexId = cursor.getColumnIndex(WalletEntry.ID);
            int indexTitle = cursor.getColumnIndex(WalletEntry.TITLE);
            int indexSubtitle = cursor.getColumnIndex(WalletEntry.SUBTITLE);
            int indexAmount = cursor.getColumnIndex(WalletEntry.AMOUNT);
            int indexInflow = cursor.getColumnIndex(WalletEntry.INFLOW);
            int indexOutflow = cursor.getColumnIndex(WalletEntry.OUTFLOW);
            int indexIconUrl = cursor.getColumnIndex(WalletEntry.ICON);
            int indexCreatedAt = cursor.getColumnIndex(WalletEntry.CREATED_AT);
            int indexUpdatedAt = cursor.getColumnIndex(WalletEntry.UPDATED_AT);
            // get data from column index
            if (indexId != -1) mId = cursor.getInt(indexId);
            if (indexTitle != -1) mTitle = cursor.getString(indexTitle);
            if (indexId != -1) mSubtitle = cursor.getString(indexSubtitle);
            if (indexSubtitle != -1) mIconUrl = cursor.getString(indexIconUrl);
            if (indexAmount != -1) mAmount = cursor.getDouble(indexAmount);
            if (indexInflow != -1) mInflow = cursor.getDouble(indexInflow);
            if (indexOutflow != -1) mOutflow = cursor.getDouble(indexOutflow);
            if (indexCreatedAt != -1) mCreatedAt = cursor.getLong(indexCreatedAt);
            if (indexUpdatedAt != -1) mUpdatedAt = cursor.getLong(indexUpdatedAt);
        }

        public Builder(SharedPreferences pref) {
            mId = pref.getInt(WalletEntry.ID, DEFAULT_NUM);
            mTitle = pref.getString(WalletEntry.TITLE, DEFAULT_STR);
            mSubtitle = pref.getString(WalletEntry.SUBTITLE, DEFAULT_STR);
            mAmount = (double) pref.getFloat(WalletEntry.AMOUNT, DEFAULT_NUM);
            mInflow = (double) pref.getFloat(WalletEntry.INFLOW, DEFAULT_NUM);
            mOutflow = (double) pref.getFloat(WalletEntry.OUTFLOW, DEFAULT_NUM);
            mIconUrl = pref.getString(WalletEntry.ICON, DEFAULT_STR);
            mCreatedAt = pref.getLong(WalletEntry.CREATED_AT, DEFAULT_NUM);
            mUpdatedAt = pref.getLong(WalletEntry.UPDATED_AT, DEFAULT_NUM);
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            mSubtitle = subtitle;
            return this;
        }

        public Builder setIconUrl(String iconUrl) {
            mIconUrl = iconUrl;
            return this;
        }

        public Builder setAmount(double amount) {
            mAmount = amount;
            return this;
        }

        public Builder setInflow(double inflow) {
            mInflow = inflow;
            return this;
        }

        public Builder setOutflow(double outflow) {
            mOutflow = outflow;
            return this;
        }

        public Builder setCreatedAt(long createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(long updatedAt) {
            mUpdatedAt = updatedAt;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }
    }
}
