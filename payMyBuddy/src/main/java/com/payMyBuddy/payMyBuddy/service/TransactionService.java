package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.Prelevement;
import com.payMyBuddy.payMyBuddy.model.Transaction;
import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.repository.PrelevementRepository;
import com.payMyBuddy.payMyBuddy.repository.TransactionRepository;
import com.payMyBuddy.payMyBuddy.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionService implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PrelevementRepository prelevementRepository;


    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionByNumero(int numeroTransaction) {
        return transactionRepository.findById(numeroTransaction)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "numeroTransaction", numeroTransaction));
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public ResponseEntity<?> deleteTransaction(int numeroTransaction) {
        Transaction transaction = transactionRepository.findById(numeroTransaction)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "numeroTransaction", numeroTransaction));
        transactionRepository.delete(transaction);
        return ResponseEntity.ok().build();
    }

    public Utilisateur consulterSolde(int id) {
        return utilisateurRepository.findById(id)  // find the user by id
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    @Override
    public void depot(int id, double montant) {
        Utilisateur utilisateur = consulterSolde(id);
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() + montant);// deposit of the transaction amount
        utilisateurRepository.save(utilisateur);

    }

    @Override
    public void retrait(int id, double montant) {
        Utilisateur utilisateur = consulterSolde(id);
        if (utilisateur.getSoldeDisponible() < montant) {
            throw new RuntimeException("Impossible de faire un virement, solde insuffisant");
        }
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() - (montant + montant * 0.05)); //withdrawal of the amount and fees of the transaction
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void prelevement(int id, double montant, Transaction transaction) {
        Utilisateur utilisateur = consulterSolde(id);
        Prelevement prelevement = new Prelevement();
        prelevement.setMontantPrelevement(montant * 0.05);  // 5% fees for each transaction
        prelevement.setUtilisateur(utilisateur);
        prelevementRepository.save(prelevement);
        transaction.setPrelevement(prelevement);
    }

    @Override
    public void virement(int id1, int id2, double montant, Transaction transaction) {
        if (id1 == id2) {
            throw new RuntimeException("Impossible de faire un virement sur le même compte");
        }
        Utilisateur utilisateur = consulterSolde(id1);
        Utilisateur utilisateur1 = consulterSolde(id2);

        if (!utilisateur.getContact().contains(utilisateur1)) {
            throw new RuntimeException("Impossible de faire un virement, l'utilisateur ne fait pas partie des contacts");
        }
        depot(id2, montant);
        retrait(id1, montant);
        prelevement(id1, montant, transaction);
    }
}
