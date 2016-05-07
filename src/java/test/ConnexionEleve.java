/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import beans.Eleve;
import test.forms.ConnexionEleveForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author VIC
 */
@WebServlet(name = "ConnexionEleve", urlPatterns = {"/ConnexionEleve"})
public class ConnexionEleve extends HttpServlet {
    public static final String VUE = "/WEB-INF/index.jsp";
    public static final String URL_REDIRECTION = "CompteEleve";
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ConnexionEleveForm form = new ConnexionEleveForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Eleve eleve = form.connecterEleve(request);

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( "sessionEleve", eleve );
        } else {
            session.setAttribute( "sessionEleve", null );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( "form", form );
        request.setAttribute( "eleve", eleve );
        
        if ( form.getErreurs().isEmpty() ) {
            response.sendRedirect( URL_REDIRECTION );
        }else{
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
