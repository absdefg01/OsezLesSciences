package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VIC
 */
@WebServlet(name = "confirmInscription", urlPatterns = {"/confirmInscription"})
public class confirmInscription extends HttpServlet {
    public static final String VUE = "/WEB-INF/choisirCreneauEtu.jsp";
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";

    // On définit la configuration d'accès au serveur SQL
    
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    Connection connexion = null;
    Statement statement = null;
    ResultSet resultat = null;
    
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )   throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }
    
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        String resultat;
        Map<String, String> erreurs = new HashMap<String, String>();
        
        /* Récupération des champs du formulaire. */
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        String confirmation = request.getParameter("confirmation");
        
        /* Validation du champ nom. */
        try {
            validationNom(nom);
        } catch (Exception e) {
            erreurs.put("nom", e.getMessage());
        }
        
        /* Validation du champ prenom. */
        try {
            validationPrenom(prenom);
        } catch (Exception e) {
            erreurs.put("prenom", e.getMessage());
        }
        
        /* Validation du champ email. */
        try {
            validationEmail(mail);
        } catch (Exception e) {
            erreurs.put("mail", e.getMessage());
        }
        
        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotsDePasse(mdp, confirmation);
        } catch (Exception e) {
            erreurs.put("mdp", e.getMessage());
        }
        
        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
            
        } else {
            resultat = "Échec de l'inscription.";
        }
        
        /* Stockage du résultat et des messages d'erreur dans l'objet request */
        request.setAttribute(ATT_ERREURS, erreurs);
        request.setAttribute(ATT_RESULTAT, resultat);

        /* Transmission de la paire d'objets request/response à notre JSP */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    /**
     * Valide le nom.
     */
    private void validationNom(String nom) throws Exception {
        if ( nom == null) {
            throw new Exception("Merci de saisir un nom.");
        }
    }
    
    /**
     * Valide le prenom.
     */
    private void validationPrenom( String prenom ) throws Exception{
        if ( prenom == null) {
            throw new Exception("Merci de saisir un prenom.");
        }
    }
    
    /**
    * Valide l'adresse mail saisie.
    */
    private void validationEmail(String email) throws Exception {
       if ( email != null && email.trim().length() != 0 ) {
           if ( !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)") ) {
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
