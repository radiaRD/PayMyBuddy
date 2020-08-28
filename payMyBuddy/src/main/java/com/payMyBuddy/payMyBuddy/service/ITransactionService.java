package com.payMyBuddy.payMyBuddy.service;


import com.payMyBuddy.payMyBuddy.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ITransactionService {
    Logger logger = LogManager.getLogger(ITransactionService.class);

    void depot(int id, double montant);

    void retrait(int id, double montant);

    void virement(int id1, int id2, double montant, Transaction transaction);

    void prelevement(int id, double montant, Transaction transaction);

}
