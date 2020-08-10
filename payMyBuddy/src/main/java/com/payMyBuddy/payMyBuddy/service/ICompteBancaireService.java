package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.model.CompteBancaire;


import java.util.Optional;

public interface ICompteBancaireService {
    Optional<CompteBancaire> findByUtilisateurId(int utilisateurId);
}
