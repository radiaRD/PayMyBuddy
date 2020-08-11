package com.payMyBuddy.payMyBuddy.controller;


import com.payMyBuddy.payMyBuddy.model.CompteBancaire;
import com.payMyBuddy.payMyBuddy.service.CompteBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CompteBancaireController {


    @Autowired
    CompteBancaireService compteBancaireService;

    @GetMapping("/compteBancaire")
    public List<CompteBancaire> getAllCompteBancaire() {
        return compteBancaireService.getAllCompteBancaire();
    }

    @GetMapping("/compteBancaire/{compteBancaireId}")
    public CompteBancaire getCompteBycompteBancaireId(@PathVariable(value = "compteBancaireId") int compteBancaireId) {
        return compteBancaireService.getCompteBancaireBycompteBancaireId(compteBancaireId);
    }

    @PostMapping("/compteBancaire")
    public CompteBancaire createCompteBancaire(@Valid @RequestBody CompteBancaire compteBancaire) {
        return compteBancaireService.createCompteBancaire(compteBancaire);
    }

    @PutMapping("/compteBancaire/{compteBancaireId}")
    public CompteBancaire updateCompteBancaire(@PathVariable(value = "compteBancaireId") int compteBancaireId,
                                               @Valid @RequestBody CompteBancaire compteBancaire) {
        return compteBancaireService.updateCompteBancaire(compteBancaireId, compteBancaire);
    }

    @DeleteMapping("/compteBancaire/{compteBancaireId}")
    public ResponseEntity<?> deleteCompteBancaireBycompteBancaireId(@PathVariable(value = "compteBancaireId") int compteBancaireId) {
        compteBancaireService.deleteCompteBancaireBycompteBancaireId(compteBancaireId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/compteBancaireUtilisateurId/{utilisateurId}")
    public Optional<List<CompteBancaire>> findByUtilisateurId(@PathVariable(value = "utilisateurId") int utilisateurId) {
        return compteBancaireService.findByUtilisateurId(utilisateurId);
    }
}
