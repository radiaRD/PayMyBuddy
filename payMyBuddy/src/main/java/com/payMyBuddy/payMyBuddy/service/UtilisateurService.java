package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UtilisateurService implements IUtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;


    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtilisateur(int id, Utilisateur utilisateurDetail) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        utilisateur.setNom(utilisateurDetail.getNom());
        utilisateur.setPrenom(utilisateurDetail.getPrenom());
        utilisateur.setDateDeNaissance(utilisateurDetail.getDateDeNaissance());
        utilisateur.setTelephone(utilisateurDetail.getTelephone());
        utilisateur.setAdresse(utilisateurDetail.getAdresse());
        utilisateur.setVille(utilisateurDetail.getVille());
        utilisateur.setCodePostal(utilisateurDetail.getCodePostal());
        utilisateur.setEmail(utilisateurDetail.getEmail());
        utilisateur.setMotDePasse(utilisateurDetail.getMotDePasse());
        utilisateur.setStatutConnexion(utilisateurDetail.isStatutConnexion());
        utilisateur.setSoldeDisponible(utilisateurDetail.getSoldeDisponible());

        return utilisateurRepository.save(utilisateur);
    }


    public ResponseEntity<?> deleteUtilisateur(int id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
        utilisateurRepository.delete(utilisateur);
        return ResponseEntity.ok().build();

    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        Optional<Utilisateur> utilisateurFound = utilisateurRepository.findByEmail(email);
        if (Boolean.FALSE.equals(utilisateurFound.isPresent())) {
            throw new ResourceNotFoundException("Utilisateur", "email", email);
        }
        return utilisateurFound;

    }


    @Override
    public Optional<Utilisateur> findByEmailAndMotDePasse(String email, String motDePasse) { // get the user with his email and password
        Optional<Utilisateur> findUtilisateur = utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
        if (Boolean.FALSE.equals(findUtilisateur.isPresent())) {
            throw new ResourceNotFoundException("Email ou mot de passe erroné");
        }
        return findUtilisateur;
    }


    public Set<Utilisateur> findAllContact(int id) { // find all contact with utilisateur id
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
        return utilisateur.getContact();
    }

    public Utilisateur addContact(int id, Utilisateur contact) { //add a user in the list of contact
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        if (utilisateur.getId() == contact.getId()) {
            throw new ResourceNotFoundException("Cet utilisateur ne peut pas être dans les contacts");
        } else utilisateur.getContact().add(contact);
        return contact;

    }

    public ResponseEntity<?> deleteContact(int id, int destinataireId) { //delete a user from the list of contact
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
        Utilisateur contact = utilisateurRepository.findById(destinataireId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", destinataireId));
        if (utilisateur.getContact().contains(contact))
            utilisateur.getContact().remove(contact);
        else throw new ResourceNotFoundException("Cet utilisateur ne fait pas partie des contact");
        return ResponseEntity.ok().build();

    }

}
