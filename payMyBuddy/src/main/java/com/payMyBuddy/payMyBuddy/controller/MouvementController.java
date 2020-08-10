package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Mouvement;
import com.payMyBuddy.payMyBuddy.service.MouvementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class MouvementController {
    @Autowired
    MouvementService mouvementService;

    @GetMapping("/mouvement")
    public List<Mouvement> getAllHistorique() {
        return mouvementService.getAllMouvement();
    }

    @GetMapping("/mouvement/{operationId}")
    public Mouvement getMouvementByOperationId(@PathVariable(value = "operationId") int operationId) {
        return mouvementService.getMouvementByNumero(operationId);
    }

    @PostMapping("/mouvement/depot")
    public Mouvement doDepot(@Valid @RequestBody Mouvement mouvement) {
        mouvementService.depotCompte(mouvement.getUtilisateur(), mouvement.getMontant(), mouvement);
        return mouvementService.createMouvement(mouvement);
    }

    @PostMapping("/mouvement/retrait")
    public Mouvement doRetrait(@Valid @RequestBody Mouvement mouvement) {
        mouvementService.RetraitCompte(mouvement.getUtilisateur(), mouvement.getMontant(), mouvement);
        return mouvementService.createMouvement(mouvement);
    }

    @DeleteMapping("/mouvement/{operationId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(value = "operationId") int operationId) {
        mouvementService.deleteMouvement(operationId);
        return ResponseEntity.ok().build();
    }

}
