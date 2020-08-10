package com.payMyBuddy.payMyBuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "prelevement")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"datePrelevement"},
        allowGetters = true)
public class Prelevement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroPrelevement;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date datePrelevement;

    private double montantPrelevement;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    public Prelevement() {
    }

    ;

    public Prelevement(int numeroPrelevement, Date datePrelevement, double montantPrelevement, Utilisateur utilisateur) {
        this.numeroPrelevement = numeroPrelevement;
        this.datePrelevement = datePrelevement;
        this.montantPrelevement = montantPrelevement;
        this.utilisateur = utilisateur;
    }

    public int getNumeroPrelevement() {
        return numeroPrelevement;
    }

    public void setNumeroPrelevement(int numeroPrelevement) {
        this.numeroPrelevement = numeroPrelevement;
    }

    public Date getDatePrelevement() {
        return datePrelevement;
    }

    public void setDatePrelevement(Date datePrelevement) {
        this.datePrelevement = datePrelevement;
    }

    public double getMontantPrelevement() {
        return montantPrelevement;
    }

    public void setMontantPrelevement(double montantPrelevement) {
        this.montantPrelevement = montantPrelevement;
    }

    public int getUtilisateur() {
        return utilisateur.getId();
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
