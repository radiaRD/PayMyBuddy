package com.payMyBuddy.payMyBuddy.repository;

import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Logger logger = LogManager.getLogger(UtilisateurRepository.class);

    Utilisateur findByEmailAndMotDePasse(String email, String motDePasse);

    Utilisateur findByEmail(String email);

}
