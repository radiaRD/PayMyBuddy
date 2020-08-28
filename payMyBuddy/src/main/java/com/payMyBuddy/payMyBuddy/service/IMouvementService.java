package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.model.Mouvement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IMouvementService {
    Logger logger = LogManager.getLogger(IMouvementService.class);

    void depot(int id, double montant);

    void retrait(int id, double montant);

    void depotCompte(int id, double montant, Mouvement mouvement);

    void RetraitCompte(int id, double montant, Mouvement mouvement);
}
