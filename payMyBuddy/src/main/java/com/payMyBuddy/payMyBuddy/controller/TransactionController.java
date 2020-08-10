package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Transaction;
import com.payMyBuddy.payMyBuddy.service.TransactionService;
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


    @Autowired
    TransactionService transactionService;


    @GetMapping("/transaction")
    public List<Transaction> getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/transaction/{numeroTransaction}")
    public Transaction getTransactionByNumero(@PathVariable(value = "numeroTransaction") int numeroTransaction) {
        return transactionService.getTransactionByNumero(numeroTransaction);
    }

    @PostMapping("/transaction")
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
        transactionService.virement(transaction.getUtilisateur(), transaction.getDestinataire(), transaction.getMontantTransaction(), transaction);
        return transactionService.createTransaction(transaction);
    }


    @DeleteMapping("/transaction/{numeroTransaction}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(value = "numeroTransaction") int numeroTransaction) {
        transactionService.deleteTransaction(numeroTransaction);
        return ResponseEntity.ok().build();
    }

}
