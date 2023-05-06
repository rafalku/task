package com.ing.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    @PostMapping(value = "/atms/calculateOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public String calculateOrder(@RequestBody AtmServiceOrder[] orders) {
        return new AtmServiceCalculator(orders).calculate();
    }

    @PostMapping(value = "/onlinegame/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Clan> calculateOrder(@RequestBody Game game) {
        return new ResponseEntity(new GameCalculator(game).calculate(), HttpStatus.OK);
    }

    @PostMapping(value = "/transactions/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> reportTransactions(@RequestBody Transaction[] transactions) {
        return new ResponseEntity(new TransactionsReporter(transactions).generateReport(), HttpStatus.OK);
    }

}
