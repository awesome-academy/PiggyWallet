package com.thailam.piggywallet.data.source.local.entry;

public interface WalletTransactionEntry {
    String TBL_NAME_WALLET_TRANS = "wallets_transactions";
    String ID = "id";
    // foreign keys
    String FOR_WAL_ID = "wallet_id";
    String FOR_TRANS_ID = "transaction_id";
}
