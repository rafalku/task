package com.ing.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    @PostMapping(value = "/transactions/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> reportTransactions(@RequestBody Transaction[] transactions) {
        return new ResponseEntity(new TransactionsReporter(transactions).generateReport(), HttpStatus.OK);
    }

}
