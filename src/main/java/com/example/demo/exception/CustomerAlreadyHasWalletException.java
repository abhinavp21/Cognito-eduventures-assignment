package com.example.demo.exception;

import com.example.demo.model.Customer;

public class CustomerAlreadyHasWalletException extends Exception {
    public CustomerAlreadyHasWalletException(Customer customer) {
        super("Customer "+customer.getFname()+" "+customer.getLname()+" already owns a wallet : "+customer.getWallet().getWalletId());
    }
}
