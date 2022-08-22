package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.AccountNotAssociatedWithWalletException;
import com.example.demo.exception.CustomerAlreadyHasWalletException;
import com.example.demo.exception.CustomerDoesNotExistException;
import com.example.demo.exception.InsufficientBalanceInWalletException;
import com.example.demo.exception.WalletIdDoesNotExistException;
import com.example.demo.model.Account;
import com.example.demo.model.BankTransaction;
import com.example.demo.model.ServiceResponse;
import com.example.demo.model.Wallet;
import com.example.demo.service.WalletService;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    // Create a new wallet for a user. Constraint : A user can have only one wallet
    @PostMapping("/v1/api/wallet/{customerId}")
    public ResponseEntity<ServiceResponse> createWallet(@PathVariable("customerId") int customerId)
            throws CustomerAlreadyHasWalletException {

        ServiceResponse response = new ServiceResponse();

        try {
            Wallet newWallet = walletService.createWallet(customerId);
            response.setStatus("200");
            response.setDescription("Wallet created successfully!");
            response.setData(newWallet);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(CustomerDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(CustomerAlreadyHasWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }

    // Return current account balance - balance in wallet (wallet can have multiple accounts)
    @GetMapping("/v1/api/wallet/{walletId}/account/{accountId}/balance")
    public  ResponseEntity<ServiceResponse>  getAccountBalance (
            @PathVariable("walletId") int walletId,
            @PathVariable("accountId") int accountId){



        ServiceResponse response = new ServiceResponse();

        try {
            Float balance = walletService.getAccountBalanceForCurrentWallet(walletId, accountId);
            response.setStatus("200");
            response.setDescription("Balance fetched successfully!!");
            response.setData(balance);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WalletIdDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(AccountNotAssociatedWithWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PostMapping("/v1/api/wallet/{walletId}/account/{accountId}/withdraw/{amount}")
    public ResponseEntity<ServiceResponse> withdraw(
            @PathVariable("walletId") int walletId,
            @PathVariable("accountId") int accountId,
            @PathVariable("amount") float amount) {

        ServiceResponse response = new ServiceResponse();

        try {
            Account ac = walletService.withdrawFromAccount(walletId, accountId, amount,"WITHDRAW");
            response.setStatus("200");
            response.setDescription("Amount "+ amount+ " withdrawn successfully!!");
            response.setData(ac);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WalletIdDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(AccountNotAssociatedWithWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        catch(InsufficientBalanceInWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PostMapping("/v1/api/wallet/{walletId}/account/{accountId}/deposit/{amount}")
    public ResponseEntity<ServiceResponse> deposit(@PathVariable("walletId") int walletId,
                         @PathVariable("accountId") int accountId,
                         @PathVariable("amount") float amount) {


        ServiceResponse response = new ServiceResponse();

        try {
            Account ac = walletService.depositToAccount(walletId, accountId, amount, "DEPOSIT");
            response.setStatus("200");
            response.setDescription("Amount "+ amount+ " deposited successfully!!");
            response.setData(ac);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WalletIdDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(AccountNotAssociatedWithWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/v1/api/wallet/{fromWalletId}/account/{fromAccountId}/transfer/wallet/{toWalletId}/account/{toAccountId}/amount/{amount}")
    public ResponseEntity<ServiceResponse> transfer(@PathVariable ("fromWalletId") int fromWalletId,
                          @PathVariable ("fromAccountId") int fromAccountId,
                          @PathVariable ("toWalletId") int toWalletId,
                          @PathVariable ("toAccountId") int toAccountId,
                          @PathVariable ("amount") float amount) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

        ServiceResponse response = new ServiceResponse();

        try {
            walletService.transferToAccount(fromWalletId, fromAccountId,toWalletId, toAccountId, amount);
            response.setStatus("200");
            response.setDescription("Amount "+ amount+ " transferred successfully!!");
            response.setData(amount);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WalletIdDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(AccountNotAssociatedWithWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        catch(InsufficientBalanceInWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }


    }

    @GetMapping("/v1/api/wallet/{walletId}/account/{accountId}/lastNTransactions/{n}")
    public ResponseEntity<ServiceResponse> getStatement(@PathVariable ("walletId") int walletId,
                                        @PathVariable ("accountId") int accountId,
                                        @PathVariable ("n") int n) {

        ServiceResponse response = new ServiceResponse();

        try {
            List<BankTransaction> lb = walletService.getStatement(walletId, accountId, n);
            response.setStatus("200");
            response.setDescription("Statement fetched successfully!!");
            response.setData(lb);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WalletIdDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(AccountNotAssociatedWithWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }

}
