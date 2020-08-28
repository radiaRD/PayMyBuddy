package com.payMyBuddy.payMyBuddy.controller;


import com.payMyBuddy.payMyBuddy.model.CompteBancaire;
import com.payMyBuddy.payMyBuddy.service.CompteBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class CompteBancaireController {
    private static final Logger logger = LogManager.getLogger(CompteBancaireController.class);

    @Autowired
    CompteBancaireService compteBancaireService;

    @GetMapping("/compteBancaire")
    public List<CompteBancaire> getAllCompteBancaire() {
        logger.info("Get all bank accounts");
        return compteBancaireService.getAllCompteBancaire();
    }

    @GetMapping("/compteBancaire/{compteBancaireId}")
    public CompteBancaire getCompteBycompteBancaireId(@PathVariable(value = "compteBancaireId") int compteBancaireId) {
        logger.info("Get a bank account with compteBancaireId : " + compteBancaireId);
        return compteBancaireService.getCompteBancaireBycompteBancaireId(compteBancaireId);
    }

    @PostMapping("/compteBancaire")
    public CompteBancaire createCompteBancaire(@Valid @RequestBody CompteBancaire compteBancaire) {
        logger.info(" Create a new bank account");
        return compteBancaireService.createCompteBancaire(compteBancaire);
    }

    @PutMapping("/compteBancaire/{compteBancaireId}")
    public CompteBancaire updateCompteBancaire(@PathVariable(value = "compteBancaireId") int compteBancaireId,
                                               @Valid @RequestBody CompteBancaire compteBancaire) {
        logger.info(" Update the bank account with compteBancaireId : " + compteBancaireId);
        return compteBancaireService.updateCompteBancaire(compteBancaireId, compteBancaire);
    }

    @DeleteMapping("/compteBancaire/{compteBancaireId}")
    public ResponseEntity<?> deleteCompteBancaireBycompteBancaireId(@PathVariable(value = "compteBancaireId") int compteBancaireId) {
        logger.info(" Delete the bank account with compteBancaireId : " + compteBancaireId);
        compteBancaireService.deleteCompteBancaireBycompteBancaireId(compteBancaireId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/compteBancaireUtilisateurId/{utilisateurId}")
    public List<CompteBancaire> findByUtilisateurId(@PathVariable(value = "utilisateurId") int utilisateurId) {
        logger.info(" Get bank accounts with utilisateurId : " + utilisateurId);
        return compteBancaireService.findByUtilisateurId(utilisateurId);
    }
}
