package com.thailam.piggywallet.data.source.local.entry;

public interface TransactionEntry {
    //transactions table entries
    String TBL_NAME_TRANS = "transactions";
    String ID = "id";
    String NOTE = "note";
    String AMOUNT = "amount";
    String DATE = "date";
    String FOR_CAT_ID = "category_id";
    String FOR_WALLET_ID = "wallet_id";
}
