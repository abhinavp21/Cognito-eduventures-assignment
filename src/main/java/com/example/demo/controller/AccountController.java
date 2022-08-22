package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

//    @Autowired
//    CustomerRepository customerRepository;

    @PostMapping("/v1/api/account")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

}
