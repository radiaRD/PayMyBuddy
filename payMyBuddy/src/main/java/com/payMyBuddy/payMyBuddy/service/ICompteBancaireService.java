package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.model.CompteBancaire;


import java.util.List;
import java.util.Optional;

public interface ICompteBancaireService {
    Optional <List<CompteBancaire>> findByUtilisateurId(int utilisateurId);
}
