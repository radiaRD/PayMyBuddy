package com.payMyBuddy.payMyBuddy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mouvement")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"date"},
        allowGetters = true)
public class Mouvement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operationId;
    @NotBlank
    private String typeOperation;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date date;
    private double montant;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "compte_Bancaire_id")
    private CompteBancaire compteBancaire;


    public Mouvement() {
    }

    public Mouvement(int operationId, @NotBlank String typeOperation, Date date, double montant, Utilisateur utilisateur, CompteBancaire compteBancaire) {
        this.operationId = operationId;
        this.typeOperation = typeOperation;
        this.date = date;
        this.montant = montant;
        this.utilisateur = utilisateur;
        this.compteBancaire = compteBancaire;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int OperationId) {
        this.operationId = operationId;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getUtilisateur() {
        return utilisateur.getId();
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }
}
