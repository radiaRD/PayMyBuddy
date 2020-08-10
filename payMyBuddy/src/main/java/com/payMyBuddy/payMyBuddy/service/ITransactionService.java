package com.payMyBuddy.payMyBuddy.service;


import com.payMyBuddy.payMyBuddy.model.Transaction;

public interface ITransactionService {

    void depot(int id, double montant);

    void retrait(int id, double montant);

    void virement(int id1, int id2, double montant, Transaction transaction);

    void prelevement(int id, double montant, Transaction transaction);

}
