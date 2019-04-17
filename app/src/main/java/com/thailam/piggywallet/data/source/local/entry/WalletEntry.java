package com.thailam.piggywallet.data.source.local.entry;

/**
 * Contains col names of wallet table
 */
public interface WalletEntry {
    String TBL_NAME_WALLET = "wallets";
    String ID = "id";
    String TITLE = "title";
    String SUBTITLE = "subtitle";
    String AMOUNT = "amount";
    String INFLOW = "inflow";
    String OUTFLOW = "outflow";
    String ICON = "icon_url";
    String CREATED_AT = "created_at";
    String UPDATED_AT = "updated_at";
}
