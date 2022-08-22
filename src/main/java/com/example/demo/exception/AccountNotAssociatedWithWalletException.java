package com.example.demo.exception;

public class AccountNotAssociatedWithWalletException extends Exception {

    public AccountNotAssociatedWithWalletException(int walletId, int accountId) {
        super("Wallet with walledId : "+walletId+" is not associated with accountId : "+accountId);
    }
}
