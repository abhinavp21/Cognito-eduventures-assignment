package com.example.demo.comparator;

import java.util.Comparator;

import com.example.demo.model.BankTransaction;

public class BankTransactionSortingComparator implements Comparator<BankTransaction> {

    public int compare(BankTransaction t1, BankTransaction t2) {
        return t2.getTimestamp().compareTo(t1.getTimestamp());
    }
}
