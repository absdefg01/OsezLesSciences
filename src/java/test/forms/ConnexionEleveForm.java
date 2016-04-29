/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.forms;

import beans.Eleve;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author VIC
 */
public class ConnexionEleveForm {
    private static final String CHAMP_EMAIL  = "mail";
    private static final String CHAMP_PASS   = "mdp";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

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

        /* Validation du champ email. */
        try {
            validationEmail(mail);
        } catch (Exception e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }

        eleve.setMail(mail);

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse(motDePasse);
        } catch (Exception e) {
            setErreur(CHAMP_PASS, e.getMessage());
        }

        eleve.setMotDePasse(motDePasse);

        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de la connexion.";
        } else {
            resultat = "Échec de la connexion.";
        }

        return eleve;
    }

    /**
     * Valide l'adresse email saisie.
     */
    private void validationEmail(String mail) throws Exception {
        if (mail == null) {
            throw new Exception("Merci de saisir votre adresse mail.");
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse(String motDePasse) throws Exception {
        if (motDePasse == null) {
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
