package com.ing.task;

import lombok.Getter;


@Getter
public class Transaction {
    private String debitAccount;
    private String creditAccount;
    private String amount;
}
