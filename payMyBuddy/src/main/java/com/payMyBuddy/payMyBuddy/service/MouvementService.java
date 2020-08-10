package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.Mouvement;
import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.repository.MouvementRepository;
import com.payMyBuddy.payMyBuddy.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MouvementService implements IMouvementService {

    @Autowired
    MouvementRepository mouvementRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public List<Mouvement> getAllMouvement() {
        return mouvementRepository.findAll();
    }

    public Mouvement getMouvementByNumero(int operationId) {
        return mouvementRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Mouvement", " operationId", operationId));
    }

    public Mouvement createMouvement(Mouvement mouvement) {
        return mouvementRepository.save(mouvement);
    }

    public Utilisateur consulterSolde(int id) {
        return utilisateurRepository.findById(id)  // find the user by id
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    @Override
    public void depot(int id, double montant) {
        Utilisateur utilisateur = consulterSolde(id);
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() + montant);  // transfer an amount from the bank account to the user account
        utilisateurRepository.save(utilisateur);

    }

    @Override
    public void retrait(int id, double montant) {
        Utilisateur utilisateur = consulterSolde(id);
        if (utilisateur.getSoldeDisponible() < montant) {
            throw new RuntimeException("Impossible de faire un virement, solde insuffisant");
        }
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() - (montant)); // transfer an amount from user account the  to the bank account
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void depotCompte(int id, double montant, Mouvement mouvement) {
        depot(id, montant);
    }

    public void RetraitCompte(int id, double montant, Mouvement mouvement) {
        retrait(id, montant);
    }

    public ResponseEntity<?> deleteMouvement(int operationId) {
        Mouvement mouvement = mouvementRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Mouvement", " operationId", operationId));
        mouvementRepository.delete(mouvement);
        return ResponseEntity.ok().build();
    }
}
