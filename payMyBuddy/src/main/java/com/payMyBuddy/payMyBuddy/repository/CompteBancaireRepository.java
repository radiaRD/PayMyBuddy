package com.payMyBuddy.payMyBuddy.repository;

import com.payMyBuddy.payMyBuddy.model.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Integer> {
    Optional <List<CompteBancaire>> findByUtilisateurId(int utilisateurId);
}
