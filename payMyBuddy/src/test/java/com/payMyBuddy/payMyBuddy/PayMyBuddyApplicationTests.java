package com.payMyBuddy.payMyBuddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.payMyBuddy.controller.MouvementController;
import com.payMyBuddy.payMyBuddy.controller.TransactionController;
import com.payMyBuddy.payMyBuddy.model.*;
import com.payMyBuddy.payMyBuddy.service.CompteBancaireService;
import com.payMyBuddy.payMyBuddy.service.TransactionService;
import com.payMyBuddy.payMyBuddy.service.UtilisateurService;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import javax.transaction.Transactional;
import java.sql.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PayMyBuddyApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    CompteBancaireService compteBancaireService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    MouvementController mouvementController;
    @Autowired
    TransactionController transactionController;

    @Test
    void getAllUtilisateurTest() throws Exception {

        this.mockMvc.perform(get("/api/utilisateur")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void getUtilisateurByIdTest() throws Exception {
        this.mockMvc.perform(get("/api/utilisateur/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nom", is("Boyd")))
                .andExpect(jsonPath("$.prenom", is("Sophia")))
                .andExpect(jsonPath("$.email", is("soboyd@email.com")));
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void createUtilisateurTest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/utilisateur")
                .content(asJsonString(new Utilisateur(6, "Foster", "Jamie", "02/08/1994", "841-874-7462", "908 73rd St", "Culver", 97451, "jamief1@gmail.com", "jamief", true, 150, "utilisateur", Sets.newHashSet())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/utilisateur")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    public void updateUtilisateurTest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/utilisateur/2")
                .content(asJsonString(new Utilisateur(2, "Boyd", "Lily", "02/08/1996", "851-800-6500", "1509 Culver St", "Culver", 97451, "lyboyd@email.com", "Blily", true, 440.0, "utilisateur", Sets.newHashSet())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/utilisateur/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.dateDeNaissance", is("02/08/1996")));
    }

    @Test
    @Transactional
    public void deleteUtilisateurTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/utilisateur/5"));
        this.mockMvc.perform(get("/api/utilisateur")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void getUtilisateurByEmailTest() throws Exception {
        this.mockMvc.perform(get("/api/utilisateurs/soboyd@email.com")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nom", is("Boyd")))
                .andExpect(jsonPath("$.prenom", is("Sophia")));
    }


    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/api/login?email=lyboyd@email.com&motDePasse=Blily").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void addContactTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/utilisateurContact/1")
                .content(asJsonString(new Utilisateur(2, "Boyd", "Lily", "12/01/2000", "851-800-6500", "1509 Culver St", "Culver", 97451, "lyboyd@email.com", "Blily", true, 440.0, "utilisateur", Sets.newHashSet())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/utilisateurContact/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    public void deleteContactTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/utilisateurContact/1/3"));
        this.mockMvc.perform(get("/api/utilisateurContact/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getAllCompteBancaireTest() throws Exception {

        this.mockMvc.perform(get("/api/compteBancaire")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getCompteBycompteBancaireIdTest() throws Exception {
        this.mockMvc.perform(get("/api/compteBancaire/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.iban", is("FR1400001700")))
                .andExpect(jsonPath("$.utilisateur", is(1)));
    }

    @Test
    @Transactional
    public void createCompteBancaireTest() throws Exception {
        CompteBancaire compteBancaire = new CompteBancaire();
        compteBancaire.setCompteBancaireId(4);
        compteBancaire.setIBAN("FR1400001706");
        compteBancaire.setUtilisateur(new Utilisateur(2, "Boyd", "Lily", "12/01/2000", "851-800-6500", "1509 Culver St", "Culver", 97451, "lyboyd@email.com", "Blily", true, 440.0, "utilisateur", Sets.newHashSet()));
        compteBancaireService.createCompteBancaire(compteBancaire);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/compteBancaire")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/compteBancaireUtilisateurId/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void updateCompteBancaireTest() throws Exception {
        CompteBancaire compteBancaire = new CompteBancaire();
        compteBancaire.setIBAN("FR1400001709");
        compteBancaireService.updateCompteBancaire(3, compteBancaire);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/compteBancaire/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/compteBancaire/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.iban", is("FR1400001709")));
    }

    @Test
    @Transactional
    public void deleteCompteBancaireBycompteBancaireIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/compteBancaire/3"));
        this.mockMvc.perform(get("/api/compteBancaire")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAllTransactionTest() throws Exception {

        this.mockMvc.perform(get("/api/transaction")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getTransactionByNumeroTest() throws Exception {
        this.mockMvc.perform(get("/api/transaction/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroTransaction", is(1)))
                .andExpect(jsonPath("$.montantTransaction", is(20.0)))
                .andExpect(jsonPath("$.utilisateur", is(2)));
    }

    @Test
    @Transactional
    public void createTransactionTest() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setNumeroTransaction(4);
        Date date = Date.valueOf("2020-08-24");
        transaction.setDateTransaction(date);
        transaction.setMontantTransaction(20.0);
        transaction.setUtilisateur(new Utilisateur(2, "Boyd", "Lily", "12/01/2000", "851-800-6500", "1509 Culver St", "Culver", 97451, "lyboyd@email.com", "Blily", true, 420.0, "utilisateur", Sets.newHashSet()));
        transaction.setDestinataire(new Utilisateur(1, "Boyd", "Sophia", "02/12/1983", "851-700-7000", "1509 Culver St", "Culver", 97451, "soboyd@email.com", "Boyd83", true, 179.0, "utilisateur", Sets.newHashSet()));
        transaction.setPrelevement(new Prelevement(2, date, 1.0, new Utilisateur(2, "Boyd", "Lily", "12/01/2000", "851-800-6500", "1509 Culver St", "Culver", 97451, "lyboyd@email.com", "Blily", true, 420.0, "utilisateur", Sets.newHashSet())));
        transactionController.createTransaction(transaction);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/transaction")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        this.mockMvc.perform(get("/api/prelevement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Transactional
    public void deleteTransactionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/transaction/1"));
        this.mockMvc.perform(get("/api/transaction")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getAllPrelevementTest() throws Exception {

        this.mockMvc.perform(get("/api/prelevement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getPrelevementByNumeroTest() throws Exception {
        this.mockMvc.perform(get("/api/prelevement/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroPrelevement", is(1)))
                .andExpect(jsonPath("$.montantPrelevement", is(1.0)))
                .andExpect(jsonPath("$.utilisateur", is(2)));
    }

    @Test
    @Transactional
    public void deletePrelevementTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/prelevement/3"));
        this.mockMvc.perform(get("/api/prelevement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllMouvementTest() throws Exception {

        this.mockMvc.perform(get("/api/mouvement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getMouvementByOperationIdTest() throws Exception {
        this.mockMvc.perform(get("/api/mouvement/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.operationId", is(1)))
                .andExpect(jsonPath("$.typeOperation", is("depot")))
                .andExpect(jsonPath("$.montant", is(51.0)));
    }

    @Test
    @Transactional
    public void depotTest() throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setOperationId(3);
        mouvement.setTypeOperation("depot");
        Date date = Date.valueOf("2020-08-24");
        mouvement.setDate(date);
        mouvement.setMontant(20.0);
        mouvement.setUtilisateur(new Utilisateur(1, "Boyd", "Sophia", "02/12/1983", "851-700-7000", "1509 Culver St", "Culver", 97451, "soboyd@email.com", "Boyd83", true, 179.0, "utilisateur", Sets.newHashSet()));
        mouvement.setCompteBancaire(new CompteBancaire(1, "FR1400001700", new Utilisateur(1, "Boyd", "Sophia", "02/12/1983", "851-700-7000", "1509 Culver St", "Culver", 97451, "soboyd@email.com", "Boyd83", true, 179.0, "utilisateur", Sets.newHashSet())));
        mouvementController.depot(mouvement);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mouvement/depot")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/mouvement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Transactional
    public void retraitTest() throws Exception {
        Mouvement mouvement = new Mouvement();
        mouvement.setOperationId(4);
        mouvement.setTypeOperation("retrait");
        Date date = Date.valueOf("2020-08-19");
        mouvement.setDate(date);
        mouvement.setMontant(20.0);
        mouvement.setUtilisateur(new Utilisateur(1, "Boyd", "Sophia", "02/12/1983", "851-700-7000", "1509 Culver St", "Culver", 97451, "soboyd@email.com", "Boyd83", true, 179.0, "utilisateur", Sets.newHashSet()));
        mouvement.setCompteBancaire(new CompteBancaire(1, "FR1400001700", new Utilisateur(1, "Boyd", "Sophia", "02/12/1983", "851-700-7000", "1509 Culver St", "Culver", 97451, "soboyd@email.com", "Boyd83", true, 179.0, "utilisateur", Sets.newHashSet())));
        mouvementController.retrait(mouvement);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mouvement/retrait")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/api/mouvement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Transactional
    public void deleteMouvementTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/mouvement/2"));
        this.mockMvc.perform(get("/api/mouvement")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}