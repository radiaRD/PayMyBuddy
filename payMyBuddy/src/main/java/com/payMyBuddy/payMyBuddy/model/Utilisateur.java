package com.payMyBuddy.payMyBuddy.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "utilisateur")
@JsonIdentityInfo(scope = Utilisateur.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Utilisateur implements Serializable {
    private static final Logger logger = LogManager.getLogger(Utilisateur.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String dateDeNaissance;
    private String telephone;
    private String adresse;
    private String ville;
    private int codePostal;
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    private String motDePasse;
    private boolean statutConnexion;
    private double soldeDisponible;
    @NotBlank
    private String role;

    @ManyToMany
    @JoinTable(
            name = "utilisateur_contact",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "destinataire_id"))
    Set<Utilisateur> contact = new HashSet<>();

    public Utilisateur() {
    }


    public Utilisateur(int id, String nom, String prenom, String dateDeNaissance, String telephone, String adresse, String ville, int codePostal, @NotBlank String email, @NotBlank String motDePasse, @NotBlank boolean statutConnexion, double soldeDisponible, @NotBlank String role, Set<Utilisateur> contact) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.email = email;
        this.motDePasse = motDePasse;
        this.statutConnexion = statutConnexion;
        this.soldeDisponible = soldeDisponible;
        this.role = role;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isStatutConnexion() {
        return statutConnexion;
    }

    public void setStatutConnexion(boolean statutConnexion) {
        this.statutConnexion = statutConnexion;
    }

    public double getSoldeDisponible() {
        return soldeDisponible;
    }

    public void setSoldeDisponible(double soldeDisponible) {
        this.soldeDisponible = soldeDisponible;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Utilisateur> getContact() {
        return contact;
    }

    public void setContact(Set<Utilisateur> contact) {
        this.contact = contact;
    }


}
