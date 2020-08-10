package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.CompteBancaire;
import com.payMyBuddy.payMyBuddy.repository.CompteBancaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CompteBancaireService implements ICompteBancaireService {

    @Autowired
    CompteBancaireRepository compteBancaireRepository;

    public List<CompteBancaire> getAllCompteBancaire() {
        return compteBancaireRepository.findAll();
    }

    public CompteBancaire getCompteBancaireBycompteBancaireId(int compteBancaireId) {
        return compteBancaireRepository.findById(compteBancaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte Bancaire", "compteBancaireId", compteBancaireId));
    }

    public CompteBancaire createCompteBancaire(CompteBancaire compteBancaire) {
        return compteBancaireRepository.save(compteBancaire);
    }


    public CompteBancaire updateCompteBancaire(int compteBancaireId, CompteBancaire compteDetail) {

        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteBancaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte Bancaire", "compteBancaireId", compteBancaireId));

        compteBancaire.setIBAN(compteDetail.getIBAN());
        return compteBancaireRepository.save(compteBancaire);
    }

    public void deleteCompteBancaireBycompteBancaireId(int compteBancaireId) {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteBancaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte Bancaire", "compteBancaireId", compteBancaireId));
        compteBancaireRepository.delete(compteBancaire);
        ResponseEntity.ok().build();
    }


    @Override
    public Optional<CompteBancaire> findByUtilisateurId(int utilisateurId) { // find the bank account by the user id
        Optional<CompteBancaire> findCompteBancaireById = compteBancaireRepository.findByUtilisateurId(utilisateurId);
        if (Boolean.FALSE.equals(findCompteBancaireById.isPresent())) {
            throw new ResourceNotFoundException("Ce compte bancaire n'existe pas");
        }
        return findCompteBancaireById;

    }


}
