package com.payMyBuddy.payMyBuddy.repository;

import com.payMyBuddy.payMyBuddy.model.CompteBancaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Integer> {
    Logger logger = LogManager.getLogger(CompteBancaireRepository.class);

    List<CompteBancaire> findByUtilisateurId(int utilisateurId);
}
