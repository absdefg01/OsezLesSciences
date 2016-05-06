/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La configuration de l'applet est faite par des annotations de code au lieu de modifier le fichier web.xml
 * 
 */
@WebServlet(name = "confirmConnexion", urlPatterns={"/confirmConnexion"})
public class confirmConnexion extends HttpServlet {

    // On définit la configuration d'accès au serveur SQL
    
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "397949844";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            /* Gérer les éventuelles erreurs ici. */
        }
        
        try {
            //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();
            /* TODO output your page here. You may use following sample code. */
            // Recupere la session
            ResultSet rs = stmt.executeQuery("Select * from Gestionnaire");
            rs.next();
            
            //login dans la base de données
            String loginBD = rs.getString(2);
            //mot de passe dans la base de données
            String mdpBD = rs.getString(3);
            //login entrée par l'utilisateur
            String login = request.getParameter("login");
            //mot de passe entrée par l'utilisateur;
            String mdp = request.getParameter("mdp");
            conn.close();
            
            /**
             * connexion de gestionnaire
             */
            //si login et mdp entrées par l'utilisateur sont corrects
            //connexion réussie
            //sinon reentrer les identifiants
            if(login.equals(loginBD) && mdp.equals(mdpBD)){
                response.sendRedirect("gererCreneau.html");
            }else{
                response.setContentType("text/html");  
                out.println("<script type=\"text/javascript\">");  
                out.println("alert('Identifiants Incorrects!!');");  
                out.println("window.location.href = '../OsezLesSciences/connexionAdmin.html';");
                out.println("</script>");
            }
            
            //On ferme la connection avec le serveur SQL
            rs.close();
                
        }catch(SQLException ex){
            // On logge un message sur le serveur d'applicatiob
            Logger.getLogger(confirmConnexion.class.getName()).log(Level.SEVERE, null, ex);
            // On renvoie un message d'erreur au client
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());        
        }finally{
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
// </editor-fold>

}
