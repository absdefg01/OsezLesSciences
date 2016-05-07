/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.forms;

import beans.Eleve;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
public class ConnexionEleveForm {
    private static final String CHAMP_EMAIL  = "mail";
    private static final String CHAMP_PASS   = "mdp";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    
        // On définit la configuration d'acces au serveur SQL
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    Connection connexion = null;
    Statement statement = null;
    ResultSet resultatSet = null;

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Eleve connecterEleve(HttpServletRequest request) {
        /* Récupération des champs du formulaire */
        String mail = getValeurChamp(request, CHAMP_EMAIL);
        String motDePasse = getValeurChamp(request, CHAMP_PASS);

        Eleve eleve = new Eleve();
        eleve.setMail(mail);
        eleve.setMotDePasse(motDePasse);
        
        /* Validation du champ email. */
        try {
            validationCompte(mail,motDePasse);
        } catch (Exception e) {
            setErreur("etat", e.getMessage());
        }
        
        return eleve;
    }

    /**
     * Valide le compte saisi.
     */
    private void validationCompte(String mail, String motDePasse) throws Exception {
        if (mail != null && mail.trim().length() != 0 ) {
            if (motDePasse != null && motDePasse.trim().length() != 0 ) {
                /* Chargement du driver JDBC pour MySQL */
                try {
                    Class.forName( "com.mysql.jdbc.Driver" );
                } catch ( ClassNotFoundException e ) {}

                try {
                    connexion = DriverManager.getConnection( URL, USERNAME, PASSWORD );

                    /* Création de l'objet gérant les requêtes */
                    statement = connexion.createStatement();

                    ResultSet resultatSet = statement.executeQuery("Select * from Eleve Where passwordEleve = MD5('"+motDePasse+"') And mailEleve = '"+mail+"';");
                    
                    if(!resultatSet.next()){
                        throw new Exception("Mail ou mot de passe incorrect.");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ConfirmInscription.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }else{
                throw new Exception("Merci de saisir votre mot de passe.");
            }
        }else{
            throw new Exception("Merci de saisir votre adresse mail.");
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse(String motDePasse) throws Exception {
        if (motDePasse == null || motDePasse.trim().length() == 0 ) {
            throw new Exception("Merci de saisir votre mot de passe.");
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur;
        }
    }
}
