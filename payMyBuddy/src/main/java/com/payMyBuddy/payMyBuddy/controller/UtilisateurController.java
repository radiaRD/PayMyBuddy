package com.payMyBuddy.payMyBuddy.controller;

import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class UtilisateurController {
    private static final Logger logger = LogManager.getLogger(UtilisateurController.class);

    @Resource(name = "authenticationManager")
    private AuthenticationManager authManager;

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/utilisateur")
    public List<Utilisateur> getAllUtilisateur() {
        logger.info("Get all users");
        return utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/utilisateur/{id}")
    public Utilisateur getUtilisateurById(@PathVariable(value = "id") int id) {
        logger.info("Get a user by id : " + id);
        return utilisateurService.getUtilisateurById(id);
    }

    @PostMapping("/utilisateur")
    public Utilisateur createUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        logger.info("Create a new user");
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @PutMapping("/utilisateur/{id}")
    public Utilisateur updateUtilisateur(@PathVariable(value = "id") int id,
                                         @Valid @RequestBody Utilisateur utilisateur) {
        logger.info("Update the user by his id : " + id);
        return utilisateurService.updateUtilisateur(id, utilisateur);
    }


    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<?> deleteUtilisateur(@PathVariable(value = "id") int id) {
        logger.info("Delete the user by his id : " + id);
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/utilisateurs/{email}")
    public Utilisateur getUtilisateurByEmail(@PathVariable(value = "email") String email) {
        logger.info("Get the user by the email : " + email);
        return utilisateurService.findByEmail(email);

    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestParam(value = "email", required = false) final String email, @RequestParam(value = "motDePasse", required = false) final String motDePasse, final HttpServletRequest request) {
        utilisateurService.login(email, motDePasse, request);
    }


    @PutMapping("/utilisateurContact/{id}") //add a user in the contact list
    public Utilisateur addContact(@PathVariable(value = "id") int id,
                                  @Valid @RequestBody Utilisateur contact) {
        logger.info("Add a user in contact list by the user id: " + id);
        return utilisateurService.addContact(id, contact);
    }

    @DeleteMapping("/utilisateurContact/{id}/{destinataireId}") //delete a user from the contact list
    public ResponseEntity<?> deleteContact(@PathVariable(value = "id") int id, @PathVariable(value = "destinataireId") int destinataireId) {
        logger.info("Delete the user from the contact list by the user id and the contact id : " + id + "\t" + " and " + destinataireId);
        utilisateurService.deleteContact(id, destinataireId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/utilisateurContact/{id}") // find contacts with user id
    public Set<Utilisateur> getContactById(@PathVariable(value = "id") int id) {
        logger.info("Find contacts by the user id: " + id);
        return utilisateurService.findAllContact(id);
    }
}
