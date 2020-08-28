package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Prelevement;
import com.payMyBuddy.payMyBuddy.service.PrelevementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrelevementController {

    private static final Logger logger = LogManager.getLogger(PrelevementController.class);

    @Autowired
    PrelevementService prelevementService;

    @GetMapping("/prelevement")
    public List<Prelevement> getAllPrelevement() {
        logger.info("Get all bank fees");
        return prelevementService.getAllPrelevement();
    }

    @GetMapping("/prelevement/{numeroPrelevement}")
    public Prelevement getPrelevementByNumero(@PathVariable(value = "numeroPrelevement") int numeroPrelevement) {
        logger.info("Get bank fees by numeroPrelevement : " + numeroPrelevement);
        return prelevementService.getPrelevementByNumero(numeroPrelevement);
    }

    @DeleteMapping("/prelevement/{numeroPrelevement}")
    public ResponseEntity<?> deletePrelevement(@PathVariable(value = "numeroPrelevement") int numeroPrelevement) {
        logger.info("Delete bank fees by numeroPrelevement : " + numeroPrelevement);
        prelevementService.deletePrelevement(numeroPrelevement);
        return ResponseEntity.ok().build();
    }


}
