package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Prelevement;
import com.payMyBuddy.payMyBuddy.service.PrelevementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrelevementController {

    @Autowired
    PrelevementService prelevementService;

    @GetMapping("/prelevement")
    public List<Prelevement> getAllPrelevement() {
        return prelevementService.getAllPrelevement();
    }

    @GetMapping("/prelevement/{numeroPrelevement}")
    public Prelevement getPrelevementByNumero(@PathVariable(value = "numeroPrelevement") int numeroPrelevement) {
        return prelevementService.getPrelevementByNumero(numeroPrelevement);
    }

    @DeleteMapping("/prelevement/{numeroPrelevement}")
    public ResponseEntity<?> deletePrelevement(@PathVariable(value = "numeroPrelevement") int numeroPrelevement) {
        prelevementService.deletePrelevement(numeroPrelevement);
        return ResponseEntity.ok().build();
    }


}
