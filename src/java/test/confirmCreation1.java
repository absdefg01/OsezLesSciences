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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "confirmCreation1", urlPatterns = {"/confirmCreation1"})
public class confirmCreation1 extends HttpServlet {
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
        PrintWriter out = response.getWriter();  
        try{
            //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();
            //String mention = request.getParameter("mention");
            
            /**
             * enseignant
             */
            String nom_enseignant = request.getParameter("nom_enseignant");
            String prenom_enseignant = request.getParameter("prenom_enseignant");
            //vérifier si enseignant est dans BBD
            //si non, ajouter dans BDD
            ResultSet rs = stmt.executeQuery("select count(nomEnseignant) as nb "
                    + "from enseignant where upper(nomEnseignant) = upper('"+nom_enseignant+"')");
            rs.next();
            //nb de noms correspondants dans BDD
            int nbN = rs.getInt("nb");
            rs = stmt.executeQuery("select count(prenomEnseignant) as nbP "
                    + "from enseignant where upper(prenomEnseignant) = upper('"+prenom_enseignant+"')");
            rs.next();
            //nb de prénom correspondants dans BDD
            int nbP = rs.getInt("nbP");
            
            //l'identifiant de l'enseignant
            int id;
            int idEnseignant;
            if(nbN == 0 && nbP == 0){
                //retourner l'id d'enseignant
                rs = stmt.executeQuery("select count(*) as nb from enseignant where upper(prenomEnseignant) = upper('"+prenom_enseignant+"')");
                rs.next();
                idEnseignant = rs.getInt("nb");
                idEnseignant++;
                
                //ajouter nouveau enseignant dans BDD
                PreparedStatement editStatement = conn.prepareStatement(
                                "INSERT into Enseignant VALUES (?,?,?)");
                editStatement.setInt(1, idEnseignant);
                editStatement.setString(2, nom_enseignant);
                editStatement.setString(3, prenom_enseignant);
                
                editStatement.executeUpdate();
                editStatement.close();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet DataBaseAccess</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println(idEnseignant);
                out.println("</body>");
                out.println("</html>");
            }else{
                rs = stmt.executeQuery("select * "
                    + "from enseignant "
                        + "where upper(nomEnseignant) = upper('"+nom_enseignant+"')"
                        + "and upper(prenomEnseignant) = upper('"+prenom_enseignant+"')");
                rs.next();
                
                id = rs.getInt(1);
                
                out.println(id);
                out.println(nom_enseignant);
                out.println(prenom_enseignant);
                
            }
            
            /**
             * matiere
             */
            
            //On ferme la connection avec le serveur SQL
            rs.close();
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
