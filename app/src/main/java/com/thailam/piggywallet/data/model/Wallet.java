package com.thailam.piggywallet.data.model;

import java.util.Date;
import java.io.Serializable;

public class Wallet implements Serializable {
    private int mId;
    private String mTitle;
    private String mSubtitle;
    private String mIconUrl;
    private double mAmount;
    private Date mCreatedAt;
    private Date mUpdatedAt;

    private Wallet(WalletBuilder builder) {
        mId = builder.mId;
        mTitle = builder.mTitle;
        mSubtitle = builder.mSubtitle;
        mIconUrl = builder.mIconUrl;
        mAmount = builder.mAmount;
        mCreatedAt = builder.mCreatedAt;
        mUpdatedAt = builder.mUpdatedAt;
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

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    //Builder class
    public static class WalletBuilder {
        private int mId;
        private String mTitle;
        private String mSubtitle;
        private String mIconUrl;
        private double mAmount;
        private Date mCreatedAt;
        private Date mUpdatedAt;

        public WalletBuilder(int id) {
            mId = id;
        }

        public WalletBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public WalletBuilder setSubtitle(String subtitle) {
            mSubtitle = subtitle;
            return this;
        }

        public WalletBuilder setIconUrl(String iconUrl) {
            mIconUrl = iconUrl;
            return this;
        }

        public WalletBuilder setAmount(double amount) {
            mAmount = amount;
            return this;
        }

        public WalletBuilder setCreatedAt(Date createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public WalletBuilder setUpdatedAt(Date updatedAt) {
            mUpdatedAt = updatedAt;
            return this;
        }

        // main build
        public Wallet build() {
            return new Wallet(this);
        }
    }
}
