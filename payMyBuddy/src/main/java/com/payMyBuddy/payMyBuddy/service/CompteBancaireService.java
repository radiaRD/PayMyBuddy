package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.CompteBancaire;
import com.payMyBuddy.payMyBuddy.repository.CompteBancaireRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CompteBancaireService {
    private static final Logger logger = LogManager.getLogger(CompteBancaireService.class);

    @Autowired
    CompteBancaireRepository compteBancaireRepository;

    public List<CompteBancaire> getAllCompteBancaire() {
        logger.info("Get all bank accounts");
        return compteBancaireRepository.findAll();
    }

    public CompteBancaire getCompteBancaireBycompteBancaireId(int compteBancaireId) {
        logger.info("Get a bank account with compteBancaireId : " + compteBancaireId);
        return compteBancaireRepository.findById(compteBancaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte Bancaire", "compteBancaireId", compteBancaireId));
    }

    public CompteBancaire createCompteBancaire(CompteBancaire compteBancaire) {
        logger.info(" Create a new bank account");
        return compteBancaireRepository.save(compteBancaire);
    }


    public CompteBancaire updateCompteBancaire(int compteBancaireId, CompteBancaire compteDetail) {
        logger.info(" Update the bank account with compteBancaireId : " + compteBancaireId);
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteBancaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte Bancaire", "compteBancaireId", compteBancaireId));

        compteBancaire.setIBAN(compteDetail.getIBAN());
        return compteBancaireRepository.save(compteBancaire);
    }

    public void deleteCompteBancaireBycompteBancaireId(int compteBancaireId) {
        logger.info(" Delete the bank account with compteBancaireId : " + compteBancaireId);
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteBancaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte Bancaire", "compteBancaireId", compteBancaireId));
        compteBancaireRepository.delete(compteBancaire);
        ResponseEntity.ok().build();
    }


    public List<CompteBancaire> findByUtilisateurId(int utilisateurId) { // find the bank account by the user id
        logger.info(" Get bank accounts with utilisateurId : " + utilisateurId);

        List<CompteBancaire> compteBancaireById = compteBancaireRepository.findByUtilisateurId(utilisateurId);
        if (compteBancaireById == null) {
            throw new ResourceNotFoundException("Cet utilisateur n'a pas de compte bancaire");
        }
        return compteBancaireById;

    }


}
