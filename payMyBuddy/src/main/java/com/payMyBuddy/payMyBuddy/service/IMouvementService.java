package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.model.Mouvement;

public interface IMouvementService {
    void depot(int id, double montant);

    void retrait(int id, double montant);

    void depotCompte(int id, double montant, Mouvement mouvement);

    void RetraitCompte(int id, double montant, Mouvement mouvement);
}
