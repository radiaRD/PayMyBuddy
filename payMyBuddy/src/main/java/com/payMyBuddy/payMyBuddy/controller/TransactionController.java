package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Transaction;
import com.payMyBuddy.payMyBuddy.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;


    @GetMapping("/transaction")
    public List<Transaction> getAllTransaction() {
        logger.info("Get all bank transactions");
        return transactionService.getAllTransaction();
    }

    @GetMapping("/transaction/{numeroTransaction}")
    public Transaction getTransactionByNumero(@PathVariable(value = "numeroTransaction") int numeroTransaction) {
        logger.info("Get a transaction by numeroTransaction :" + numeroTransaction);
        return transactionService.getTransactionByNumero(numeroTransaction);
    }

    @PostMapping("/transaction")
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
        logger.info("Create a new transaction");
        transactionService.virement(transaction.getUtilisateur(), transaction.getDestinataire(), transaction.getMontantTransaction(), transaction);
        return transactionService.createTransaction(transaction);
    }


    @DeleteMapping("/transaction/{numeroTransaction}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(value = "numeroTransaction") int numeroTransaction) {
        logger.info(" Delete the transaction by numeroTransaction :" + numeroTransaction);
        transactionService.deleteTransaction(numeroTransaction);
        return ResponseEntity.ok().build();
    }

}
