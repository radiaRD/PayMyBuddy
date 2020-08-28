package com.payMyBuddy.payMyBuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"dateTransaction"},
        allowGetters = true)
public class Transaction implements Serializable {
    private static final Logger logger = LogManager.getLogger(Transaction.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroTransaction;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date dateTransaction;

    private double montantTransaction;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
    })
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "destinataire_id", referencedColumnName = "id"),})
    private Utilisateur destinataire;

    @OneToOne
    @JoinColumn(name = "numero_prelevement")
    private Prelevement prelevement;

    public Transaction() {
    }

    public Transaction(int numeroTransaction, Date dateTransaction, double montantTransaction, Utilisateur utilisateur, Utilisateur destinataire, Prelevement prelevement) {
        this.numeroTransaction = numeroTransaction;
        this.dateTransaction = dateTransaction;
        this.montantTransaction = montantTransaction;
        this.utilisateur = utilisateur;
        this.destinataire = destinataire;
        this.prelevement = prelevement;
    }


    public int getNumeroTransaction() {
        return numeroTransaction;
    }

    public void setNumeroTransaction(int numeroTransaction) {
        this.numeroTransaction = numeroTransaction;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public double getMontantTransaction() {
        return montantTransaction;
    }

    public void setMontantTransaction(double montantTransaction) {
        this.montantTransaction = montantTransaction;
    }

    public int getUtilisateur() {
        return utilisateur.getId();
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getDestinataire() {
        return destinataire.getId();
    }

    public void setDestinataire(Utilisateur destinataire) {
        this.destinataire = destinataire;
    }

    public int getPrelevement() {
        return prelevement.getNumeroPrelevement();
    }

    public void setPrelevement(Prelevement prelevement) {
        this.prelevement = prelevement;
    }


}
