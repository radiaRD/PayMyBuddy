package com.payMyBuddy.payMyBuddy.model;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "compte_bancaire")
public class CompteBancaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int compteBancaireId;

    private String IBAN;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    public CompteBancaire() {
    }



    public CompteBancaire(int compteBancaireId, String IBAN, Utilisateur utilisateur) {
        this.compteBancaireId = compteBancaireId;
        this.IBAN = IBAN;
        this.utilisateur = utilisateur;
    }

    public int getCompteBancaireId() {
        return compteBancaireId;
    }

    public void setCompteBancaireId(int compteBancaireId) {
        this.compteBancaireId = compteBancaireId;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int getUtilisateur() {
        return utilisateur.getId();
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
