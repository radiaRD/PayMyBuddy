package com.payMyBuddy.payMyBuddy.service;

        import com.payMyBuddy.payMyBuddy.exception.ResourceNotFoundException;
        import com.payMyBuddy.payMyBuddy.model.Prelevement;
        import com.payMyBuddy.payMyBuddy.model.Transaction;
        import com.payMyBuddy.payMyBuddy.model.Utilisateur;
        import com.payMyBuddy.payMyBuddy.repository.PrelevementRepository;
        import com.payMyBuddy.payMyBuddy.repository.TransactionRepository;
        import com.payMyBuddy.payMyBuddy.repository.UtilisateurRepository;
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;


        import javax.transaction.Transactional;
        import java.util.List;

@Service
@Transactional
public class TransactionService implements ITransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PrelevementRepository prelevementRepository;


    public List<Transaction> getAllTransaction() {
        logger.info("Get all bank transactions");
        return transactionRepository.findAll();
    }

    public Transaction getTransactionByNumero(int numeroTransaction) {
        logger.info("Get a transaction by numeroTransaction : " + numeroTransaction);
        return transactionRepository.findById(numeroTransaction)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "numeroTransaction", numeroTransaction));
    }

    public Transaction createTransaction(Transaction transaction) {
        logger.info("Create a new transaction");
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(int numeroTransaction) {
        logger.info(" Delete the transaction by numeroTransaction : " + numeroTransaction);
        Transaction transaction = transactionRepository.findById(numeroTransaction)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "numeroTransaction", numeroTransaction));
        transactionRepository.delete(transaction);
    }

    public Utilisateur consulterSolde(int id) {
        logger.info("Find the user account by user id : " + id);
        return utilisateurRepository.findById(id)  // find the user by id
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    @Override
    public void depot(int id, double montant) {
        logger.info("Deposit an amount of : " + montant + " in the user account with id : " + id);
        Utilisateur utilisateur = consulterSolde(id);
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() + montant);// deposit of the transaction amount
        utilisateurRepository.save(utilisateur);

    }

    @Override
    public void retrait(int id, double montant) {
        logger.info("Withdraw an amount of : " + montant + " from the user account with id : " + id);
        Utilisateur utilisateur = consulterSolde(id);
        if (utilisateur.getSoldeDisponible() < montant) {
            throw new RuntimeException("Impossible de faire un virement, solde insuffisant");
        }
        utilisateur.setSoldeDisponible(utilisateur.getSoldeDisponible() - (montant + montant * 0.05)); //withdrawal of the amount and fees of the transaction
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void prelevement(int id, double montant, Transaction transaction) {
        logger.info("Withdraw an amount of : " + (montant * 0.5) + " from the user account with id : " + id);
        Utilisateur utilisateur = consulterSolde(id);
        Prelevement prelevement = new Prelevement();
        prelevement.setMontantPrelevement(montant * 0.05);  // 5% fees for each transaction
        prelevement.setUtilisateur(utilisateur);
        prelevementRepository.save(prelevement);
        transaction.setPrelevement(prelevement);
    }

    @Override
    public void virement(int id1, int id2, double montant, Transaction transaction) {
        logger.info("Make a bank transfer of : " + montant + " from user account with id : " + id1 + " to user account with id : " + id2);
        if (id1 == id2) {
            throw new RuntimeException("Impossible de faire un virement sur le même compte");
        }
        Utilisateur utilisateur = consulterSolde(id1);
        Utilisateur utilisateur1 = consulterSolde(id2);

        if (!utilisateur.getContact().contains(utilisateur1)) {
            throw new RuntimeException("Impossible de faire un virement, l'utilisateur ne fait pas partie des contacts");
        }
        depot(id2, montant); // deposit the amount of the transaction in contact account
        retrait(id1, montant); // withdrawal the amount of the transaction and fees for the user account
        prelevement(id1, montant, transaction); // create a new "prelevement"
    }
}
