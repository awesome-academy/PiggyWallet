package com.thailam.piggywallet.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.thailam.piggywallet.data.source.local.entry.WalletEntry;

public class Wallet implements Parcelable {

    private static final Parcelable.Creator<Wallet> CREATOR = new Creator<Wallet>() {
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
    private long mCreatedAt;
    private long mUpdatedAt;

    private Wallet(Builder builder) {
        mId = builder.mId;
        mTitle = builder.mTitle;
        mSubtitle = builder.mSubtitle;
        mIconUrl = builder.mIconUrl;
        mAmount = builder.mAmount;
        mCreatedAt = builder.mCreatedAt;
        mUpdatedAt = builder.mUpdatedAt;
    }

    public Wallet(Parcel source) {
        mId = source.readInt();
        mTitle = source.readString();
        mSubtitle = source.readString();
        mIconUrl = source.readString();
        mAmount = source.readDouble();
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
        dest.writeLong(mCreatedAt);
        dest.writeLong(mUpdatedAt);
    }

    //Builder class
    public static class Builder {
        private int mId;
        private String mTitle;
        private String mSubtitle;
        private String mIconUrl;
        private double mAmount;
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
            int indexIconUrl = cursor.getColumnIndex(WalletEntry.ICON);
            int indexCreatedAt = cursor.getColumnIndex(WalletEntry.CREATED_AT);
            int indexUpdatedAt = cursor.getColumnIndex(WalletEntry.UPDATED_AT);
            // get data from column index
            mId = cursor.getInt(indexId);
            mTitle = cursor.getString(indexTitle);
            mSubtitle = cursor.getString(indexSubtitle);
            mIconUrl = cursor.getString(indexIconUrl);
            mAmount = cursor.getDouble(indexAmount);
            mCreatedAt = cursor.getLong(indexCreatedAt);
            mUpdatedAt = cursor.getLong(indexUpdatedAt);
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

        public Builder setCreatedAt(long createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(long updatedAt) {
            mUpdatedAt = updatedAt;
            return this;
        }

        // main build
        public Wallet build() {
            return new Wallet(this);
        }
    }
}
