package com.payMyBuddy.payMyBuddy.service;

import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
import com.payMyBuddy.payMyBuddy.model.MyUtilisateurPrincipal;
import com.payMyBuddy.payMyBuddy.model.Utilisateur;
import com.payMyBuddy.payMyBuddy.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UtilisateurService implements UserDetailsService {

    @Autowired
    DaoAuthenticationProvider authProvider;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UtilisateurService() {
    }

    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }


    public Utilisateur createUtilisateur(Utilisateur utilisateur) {

        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) != null) {
            throw new ResourceNotFoundException("Cet utilisateur existe déjà");
        } else utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
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
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurDetail.getMotDePasse()));
        utilisateur.setStatutConnexion(utilisateurDetail.isStatutConnexion());
        utilisateur.setSoldeDisponible(utilisateurDetail.getSoldeDisponible());

        return utilisateurRepository.save(utilisateur);
    }


    public void deleteUtilisateur(int id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
        utilisateurRepository.delete(utilisateur);
    }


    public Utilisateur findByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur == null) {
            throw new ResourceNotFoundException("Utilisateur", "email", email);
        }
        return utilisateur;

    }


//    public Utilisateur findByEmailAndMotDePasse(String email, String motDePasse) { // get the user with his email and password
//        Utilisateur utilisateur = utilisateurRepository.findByEmailAndMotDePasse(email,passwordEncoder.encode(motDePasse));
//        if (utilisateur == null) {
//            throw new ResourceNotFoundException("Email ou mot de passe erroné");
//        }
//        return utilisateur;
//    }

    public void login(String email, String motDePasse, final HttpServletRequest request) { // get the user with his email and password
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(email, motDePasse);
        Authentication auth = authProvider.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
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

    public void deleteContact(int id, int destinataireId) { //delete a user from the list of contact
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
        Utilisateur contact = utilisateurRepository.findById(destinataireId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", destinataireId));
        if (utilisateur.getContact().contains(contact))
            utilisateur.getContact().remove(contact);
        else throw new ResourceNotFoundException("Cet utilisateur ne fait pas partie des contacts");

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur == null) {
            throw new UsernameNotFoundException(email);
        }
        return new MyUtilisateurPrincipal(utilisateur);
    }

}
