package com.ing.task;


import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Getter
class Account {
    private final String account;
    private int creditCount = 0;
    private int debitCount = 0;
    private BigDecimal balance = new BigDecimal(0);

    public Account(String accountNumber) {
        this.account = accountNumber;
    }

    public void increaseBalance(String amount) {
        this.balance = this.balance.add(new BigDecimal(amount));
    }

    public void decreaseBalance(String amount) {
        this.balance = this.balance.subtract(new BigDecimal(amount));
    }

    public void incrementCreditCount() {
        this.creditCount++;
    }

    public void incrementDebitCount() {
        this.debitCount++;
    }
}


public class TransactionsReporter {

    private final Transaction[] transactions;
    private final Map<String, Account> accounts;

    public TransactionsReporter(Transaction[] transactions) {
        this.transactions = transactions;
        this.accounts = new HashMap<>(transactions.length * 2);
    }

    public Collection<Account> generateReport() {
        String debitAccountNumber, creditAccountNumber;
        String amount;
        Account account;
        for (Transaction transaction : transactions) {
            debitAccountNumber = transaction.getDebitAccount();
            creditAccountNumber = transaction.getCreditAccount();
            amount = transaction.getAmount();

            account = accounts.get(debitAccountNumber);
            if (account == null) {
                account = new Account(debitAccountNumber);
                accounts.put(debitAccountNumber, account);
            }
            account.decreaseBalance(amount);
            account.incrementDebitCount();

            account = accounts.get(creditAccountNumber);
            if (account == null) {
                account = new Account(creditAccountNumber);
                accounts.put(creditAccountNumber, account);
            }
            account.increaseBalance(amount);
            account.incrementCreditCount();
        }

        Map<String, Account> sortedAccounts = new TreeMap<>(accounts);
        return sortedAccounts.values();
    }
}
