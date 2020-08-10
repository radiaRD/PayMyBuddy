package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.model.Utilisateur;

import java.util.Optional;

public interface IUtilisateurService {
    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByEmailAndMotDePasse(String email, String motDePasse);
}
