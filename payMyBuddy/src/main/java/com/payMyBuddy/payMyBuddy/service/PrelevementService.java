package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.Prelevement;
import com.payMyBuddy.payMyBuddy.repository.PrelevementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PrelevementService {

    @Autowired
    PrelevementRepository prelevementRepository;

    public List<Prelevement> getAllPrelevement() {
        return prelevementRepository.findAll();
    }

    public Prelevement getPrelevementByNumero(int numeroPrelevement) {
        return prelevementRepository.findById(numeroPrelevement)
                .orElseThrow(() -> new ResourceNotFoundException("Prelevement", "numero de prélèvement", numeroPrelevement));
    }

    public ResponseEntity<?> deletePrelevement(int numeroPrelevement) {
        Prelevement prelevement = prelevementRepository.findById(numeroPrelevement)
                .orElseThrow(() -> new ResourceNotFoundException("Prelevement", "numero prélevement", numeroPrelevement));
        prelevementRepository.delete(prelevement);
        return ResponseEntity.ok().build();
    }
}
