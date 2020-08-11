package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/utilisateur")
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/utilisateur/{id}")
    public Utilisateur getUtilisateurById(@PathVariable(value = "id") int id) {
        return utilisateurService.getUtilisateurById(id);
    }

    @PostMapping("/utilisateur")
    public Utilisateur createUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @PutMapping("/utilisateur/{id}")
    public Utilisateur updateUtilisateur(@PathVariable(value = "id") int id,
                                         @Valid @RequestBody Utilisateur utilisateur) {
        return utilisateurService.updateUtilisateur(id, utilisateur);
    }


    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<?> deleteUtilisateur(@PathVariable(value = "id") int id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/utilisateurs/{email}")
    public Optional<Utilisateur> getUtilisateurByEmail(@PathVariable(value = "email") String email) {
        return utilisateurService.findByEmail(email);
    }

    @GetMapping("/utilisateurs/{email}/{motDePasse}") // get the user with his email and password
    public Optional<Utilisateur> findByEmailAndMotDePasse(@PathVariable(value = "email") String email, @PathVariable(value = "motDePasse") String motDePasse) {
        return utilisateurService.findByEmailAndMotDePasse(email, motDePasse);
    }

    @PutMapping("/utilisateurContact/{id}") //add a user in the list of contact
    public Utilisateur addContact(@PathVariable(value = "id") int id,
                                  @Valid @RequestBody Utilisateur contact) {
        return utilisateurService.addContact(id, contact);
    }

    @DeleteMapping("/utilisateurContact/{id}/{destinataireId}") //delete a user from the list of contact
    public ResponseEntity<?> deleteContact(@PathVariable(value = "id") int id, @PathVariable(value = "destinataireId") int destinataireId) {
        utilisateurService.deleteContact(id, destinataireId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/utilisateurContact/{id}") // find all contact with utilisateur id
    public Set<Utilisateur> getContactById(@PathVariable(value = "id") int id) {
        return utilisateurService.findAllContact(id);
    }
}
