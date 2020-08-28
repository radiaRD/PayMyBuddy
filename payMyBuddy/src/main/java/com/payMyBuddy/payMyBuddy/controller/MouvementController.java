package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Mouvement;
import com.payMyBuddy.payMyBuddy.service.MouvementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class MouvementController {

    private static final Logger logger = LogManager.getLogger(MouvementController.class);

    @Autowired
    MouvementService mouvementService;

    @GetMapping("/mouvement")
    public List<Mouvement> getAllMouvement() {
        logger.info("Get all bank movements");
        return mouvementService.getAllMouvement();
    }

    @GetMapping("/mouvement/{operationId}")
    public Mouvement getMouvementByOperationId(@PathVariable(value = "operationId") int operationId) {
        logger.info("Get a bank movement by operationId : " + operationId);
        return mouvementService.getMouvementByNumero(operationId);
    }

    @PostMapping("/mouvement/depot")
    public Mouvement depot(@Valid @RequestBody Mouvement mouvement) {
        logger.info("Creation of a bank deposit ");
        mouvementService.depotCompte(mouvement.getUtilisateur(), mouvement.getMontant(), mouvement);
        return mouvementService.createMouvement(mouvement);
    }

    @PostMapping("/mouvement/retrait")
    public Mouvement retrait(@Valid @RequestBody Mouvement mouvement) {
        logger.info("Creation of a bank withdrawal ");
        mouvementService.RetraitCompte(mouvement.getUtilisateur(), mouvement.getMontant(), mouvement);
        return mouvementService.createMouvement(mouvement);
    }

    @DeleteMapping("/mouvement/{operationId}")
    public ResponseEntity<?> deleteMouvement(@PathVariable(value = "operationId") int operationId) {
        logger.info("Delete a bank movement with operationId :  " + operationId);
        mouvementService.deleteMouvement(operationId);
        return ResponseEntity.ok().build();
    }
}
