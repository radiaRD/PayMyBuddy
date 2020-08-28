package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.Prelevement;
import com.payMyBuddy.payMyBuddy.repository.PrelevementRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PrelevementService {
    private static final Logger logger = LogManager.getLogger(PrelevementService.class);

    @Autowired
    PrelevementRepository prelevementRepository;

    public List<Prelevement> getAllPrelevement() {
        logger.info("Get all bank fees");
        return prelevementRepository.findAll();
    }

    public Prelevement getPrelevementByNumero(int numeroPrelevement) {
        logger.info("Get bank fees by numeroPrelevement : " + numeroPrelevement);
        return prelevementRepository.findById(numeroPrelevement)
                .orElseThrow(() -> new ResourceNotFoundException("Prelevement", "numero de prélèvement", numeroPrelevement));
    }

    public ResponseEntity<?> deletePrelevement(int numeroPrelevement) {
        logger.info("Delete bank fees by numeroPrelevement : " + numeroPrelevement);
        Prelevement prelevement = prelevementRepository.findById(numeroPrelevement)
                .orElseThrow(() -> new ResourceNotFoundException("Prelevement", "numero prélevement", numeroPrelevement));
        prelevementRepository.delete(prelevement);
        return ResponseEntity.ok().build();
    }
}
