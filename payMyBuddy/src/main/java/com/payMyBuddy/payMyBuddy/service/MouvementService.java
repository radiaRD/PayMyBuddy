package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.Mouvement;
import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.repository.MouvementRepository;
import com.payMyBuddy.payMyBuddy.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MouvementService implements IMouvementService {
    private static final Logger logger = LogManager.getLogger(MouvementService.class);

    @Autowired
    MouvementRepository mouvementRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public List<Mouvement> getAllMouvement() {
        logger.info("Get all bank movements");
        return mouvementRepository.findAll();
    }

    public Mouvement getMouvementByNumero(int operationId) {
        logger.info("Get a bank movement by operationId : " + operationId);
        return mouvementRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Mouvement", " operationId", operationId));
    }

    public Mouvement createMouvement(Mouvement mouvement) {
        logger.info("Creation of the banking movement ");
        return mouvementRepository.save(mouvement);
    }

    public Utilisateur consulterSolde(int id) {
        logger.info("Find the user account by user id : " + id);
        return utilisateurRepository.findById(id)  // find the user by id
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    @Override
    public void depot(int id, double montant) {
        logger.info("Deposit an amount of : " + montant + " in the user account with id : " + id);
        Utilisateur utilisateur = consulterSolde(id);
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() + montant);  // transfer an amount from the bank account to the user account
        utilisateurRepository.save(utilisateur);

    }

    @Override
    public void retrait(int id, double montant) {
        logger.info("Withdraw an amount of : " + montant + " from the user account with id : " + id);
        Utilisateur utilisateur = consulterSolde(id);
        if (utilisateur.getSoldeDisponible() < montant) {
            throw new RuntimeException("Impossible de faire un virement, solde insuffisant");
        }
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() - (montant)); // transfer an amount from user account the  to the bank account
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void depotCompte(int id, double montant, Mouvement mouvement) {
        logger.info("Creation of a bank deposit ");
        depot(id, montant);
    }

    public void RetraitCompte(int id, double montant, Mouvement mouvement) {
        logger.info("Creation of a bank withdrawal ");
        retrait(id, montant);
    }

    public void deleteMouvement(int operationId) {
        logger.info("Delete a bank movement with operationId :  " + operationId);
        Mouvement mouvement = mouvementRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Mouvement", " operationId", operationId));
        mouvementRepository.delete(mouvement);

    }
}
