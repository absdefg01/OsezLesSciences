/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.forms;

import beans.Eleve;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import test.ConfirmInscription;

/**
 *
 * @author VIC
 */
public class InscriptionEleveForm {
    private static final String CHAMP_NOM    = "nom";
    private static final String CHAMP_PRENOM = "prenom";
    private static final String CHAMP_MAIL  = "mail";
    private static final String CHAMP_PASS   = "mdp";
    private static final String CHAMP_CONF   = "confirmation";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    
    // On définit la configuration d'acces au serveur SQL
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    Connection connexion = null;
    Statement statement = null;

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Eleve inscrireEleve(HttpServletRequest request){
        /* Récupèration des champs du formulaire. */
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        String confirmation = request.getParameter("confirmation");
        
        Eleve eleve = new Eleve();
        
        /* Validation du champ nom. */
        try {
            validationNom(nom);
        } catch (Exception e) {
            erreurs.put("nom", e.getMessage());
        }
        eleve.setNom(nom);
        
        /* Validation du champ prenom. */
        try {
            validationPrenom(prenom);
        } catch (Exception e) {
            erreurs.put("prenom", e.getMessage());
        }
        eleve.setPrenom(prenom);
        
        /* Validation du champ email. */
        try {
            validationEmail(mail);
        } catch (Exception e) {
            erreurs.put("mail", e.getMessage());
        }
        eleve.setMail(mail);
        
        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotsDePasse(mdp, confirmation);
        } catch (Exception e) {
            erreurs.put("mdp", e.getMessage());
        }
        eleve.setMotDePasse(mdp);
        
        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
            
            /* Chargement du driver JDBC pour MySQL */
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch ( ClassNotFoundException e ) {}
            
            try {
                connexion = DriverManager.getConnection( URL, USERNAME, PASSWORD );
                
                /* Création de l'objet gérant les requêtes */
                statement = connexion.createStatement();
                
                statement.executeUpdate( "INSERT INTO Eleve VALUES (NULL, '"+nom+"', '"+prenom+"', '"+mail+"', MD5('"+mdp+"'));" );
            } catch (SQLException ex) {
                Logger.getLogger(ConfirmInscription.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            resultat = "Echec de l'inscription.";
        }
        
        return eleve;
    }
    
    /**
     * Valide le nom.
     */
    private void validationNom(String nom) throws Exception {
        if ( nom == null || nom.trim().length() == 0) {
            throw new Exception("Merci de saisir un nom.");
        }
    }
    
    /**
     * Valide le prenom.
     */
    private void validationPrenom( String prenom ) throws Exception{
        if ( prenom == null || prenom.trim().length() == 0 ) {
            throw new Exception("Merci de saisir un prenom.");
        }
    }
    
    /**
    * Valide l'adresse mail saisie.
    */
    private void validationEmail(String mail) throws Exception {
       if ( mail != null && mail.trim().length() != 0 ) {
           if ( !mail.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)") ) {
               throw new Exception("Merci de saisir une adresse mail valide.");
           }
       } else {
           throw new Exception("Merci de saisir une adresse mail.");
       }
   }
   
    /**
    * Valide les mots de passe saisis.
    */
    private void validationMotsDePasse(String motDePasse, String confirmation) throws Exception{
       if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
           if (!motDePasse.equals(confirmation)) {
               throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
           } else if (motDePasse.trim().length() < 3) {
               throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
           }
       } else {
           throw new Exception("Merci de saisir et confirmer votre mot de passe.");
       }
   }
}
