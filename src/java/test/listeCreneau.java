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
@WebServlet(name = "confirmCreation2", urlPatterns = {"/confirmCreation2"})
public class listeCreneau extends HttpServlet {
    
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
            PreparedStatement editStatement = conn.prepareStatement("INSERT into Creneau VALUES (null,?,?,?,?,?,?,?)");

           
            /**
             * date
             */
            String dateString = request.getParameter("date");
            //String to java date
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            } catch (ParseException ex) {
                Logger.getLogger(listeCreneau.class.getName()).log(Level.SEVERE, null, ex);
            }
            //java date to sql date
            //2 est index de parametre
            editStatement.setDate(2, new java.sql.Date(date.getTime()));
            
            
            /**
             * heureDebut et heureFin
             */
            String heureDebutS = request.getParameter("heureDebut");
            String heureFinS = request.getParameter("heureFin");
            Date date2 = null;
            Date date3 = null;
            
            try {
                date2 = new SimpleDateFormat("HH:mm").parse(heureDebutS);
                date3 = new SimpleDateFormat("HH:mm").parse(heureFinS);
            }
            catch (ParseException e) {
                request.setAttribute("time_error", "Please enter time in format HH:mm");
            }
            
            editStatement.setTime(3, new Time(date2.getTime()));
            editStatement.setTime(4, new Time(date3.getTime()));
            
            
            /**
             * nbEleveMax
             */
            String nbEleveMax = request.getParameter("nbMax");
            editStatement.setInt(5, Integer.parseInt(nbEleveMax));
            
            /**
             * idMatiere
             * 1 - vérifier si le matière existe dans la BD
             * 2 - si oui, retourner l'idMatiere
             *     si non, ajouter le matiere dans la BDD et retourner l'idMatiere
             */
            String nom_Matiere = request.getParameter("matiere");
            ResultSet rs = stmt.executeQuery("select count(nomMatiere) as nb"
                    + "from Matiere where nomMatiere = nom_Matiere");
            int nb = rs.getInt("nb");
            if(nb==0){
                //insérer le mati
                //insérer le matiere dans BDD
                PreparedStatement editStatement2 = conn.prepareStatement("INSERT into Matiere VALUES (null,?,?)");
                
           
            }else{
            
            }
            
            /**
             * idEnseignant
             */
            
            editStatement.executeUpdate();

            
            conn.close();

            editStatement.close();
            
                     
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
