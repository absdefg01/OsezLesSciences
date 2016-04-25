/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhaomengzi
 */
@WebServlet(name = "modifierCreneau", urlPatterns = {"/modifierCreneau"})
public class modifierCreneau extends HttpServlet {
    // On définit la configuration d'accès au serveur SQL
    
    private static final String URL = "jdbc:derby://localhost:1527/oserlessciences";
    private static final String USERNAME = "mengzi";
    private static final String PASSWORD = "397949844";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();  
        
        try{
            //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();

            /**
             * afficher des liste de créneau
             */
            //obtenir le nb de créneau
            ResultSet rs = stmt.executeQuery("select count(*) as nb from creneau ");
            rs.next();
            int nbDeCreneau = rs.getInt("nb");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DataBaseAccess</title>");            
            out.println("</head>");
            
            out.println("<body>");
            out.println("<h1>liste des créneau</h1>");
            
            out.println("<table border=\"1\">");
                out.println("<tr>");
                    out.println("<th>IdCreneau</th>");
                    out.println("<th>Date</th>");
                    out.println("<th>HeureDebut</th>");
                    out.println("<th>HeureFin</th>");
                    out.println("<th>NbEleveMax</th>");
                    out.println("<th>Matiere</th>");
                    out.println("<th>NomEnseignant</th>");
                    out.println("<th>PrenomEnseignant</th>");
                    out.println("<th>case à cocher</th>");
                 out.println("</tr>");
                 
                 for(int i = 1; i<= nbDeCreneau; i++){
                    //obtenir les colonne de créneau
                    rs = stmt.executeQuery("select * from creneau "
                            + "where idCreneau = cast('"+i+"' as Integer)");
                    rs.next();
                    String idCreneauH = rs.getString(1);
                    String dateCreneauH = rs.getString(2);
                    String heureDebutH = rs.getString(3);
                    String heureFinH = rs.getString(4);
                    String nbEleveMaxH = rs.getString(5);
                    //visibilite : 1 - supprimer 2 - non supprimer
                    rs = stmt.executeQuery("SELECT * FROM matiere m, creneau c\n" +
                        "where m.IDMATIERE = c.IDMATIERE\n" +
                        "and c.IDCRENEAU = cast('"+i+"' as Integer)"
                            + "and c.visibilite = 0");
                    rs.next();
                    String nomMatiereH = rs.getString(2);
                    
                    rs = stmt.executeQuery("SELECT * FROM enseignant e, creneau c\n" +
                        "where e.idEnseignant = c.idEnseignant\n" +
                        "and c.IDCRENEAU = cast('"+i+"' as Integer)"
                            + "and c.visibilite = 0");
                    rs.next();
                    String nomEnseignantH = rs.getString(2);
                    String prenomEnseignantH = rs.getString(3);
                    
                out.println("<tr>");
                    out.println("<td>"+idCreneauH+"</td>");
                    out.println("<td>"+dateCreneauH+"</td>");
                    out.println("<td>"+heureDebutH+"</td>");
                    out.println("<td>"+heureFinH+"</td>");
                    out.println("<td>"+nbEleveMaxH+"</td>");
                    out.println("<td>"+nomMatiereH+"</td>");
                    out.println("<td>"+nomEnseignantH+"</td>");
                    out.println("<td>"+prenomEnseignantH+"</td>");
                    out.println("<td><INPUT type=\"checkbox\" name=\"choix1\" value=\"1\"></td>");
                out.println("</tr>");
                }
            out.println("</table>");
            
            out.println("<form method='post' action='confirmModifier.java'>");
            out.println("<input type='submit' name='modifier' value='modifier'>");
            out.println("</form>");
            
            out.println("<form method='post' action='gererCreneau.html'>");
            out.println("<input type='submit' name='retourner' value='retourner'>");
            out.println("</form>");
                    
            conn.close();
            
            
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
    }// </editor-fold>

}
