package test;

import beans.Eleve;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import test.forms.InscriptionEleveForm;

/**
 *
 * @author VIC
 */
@WebServlet(name = "confirmInscription", urlPatterns = {"/ConfirmInscription"})
public class ConfirmInscription extends HttpServlet {
    public static final String VUE = "/WEB-INF/choisirCreneauEleve.jsp";
    
    // On définit la configuration d'acces au serveur SQL
    
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
        /* Objet du formulaire */
        InscriptionEleveForm form = new InscriptionEleveForm();
        
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Eleve eleve = form.inscrireEleve(request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( "form", form );
        request.setAttribute( "eleve", eleve );

        /* Transmission de la paire d'objets request/response à notre JSP */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
