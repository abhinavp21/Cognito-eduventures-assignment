package com.example.demo.exception;

import com.example.demo.model.Customer;
import com.example.demo.model.Wallet;

public class WalletDoesNotBelongToCustomer extends Exception {
    public WalletDoesNotBelongToCustomer(Customer customer, Wallet wallet) {
        super("Customer with id"+customer.getUserId()+" does not have associated walletId : "+wallet.getWalletId());
    }
}
