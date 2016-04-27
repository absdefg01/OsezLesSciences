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
@WebServlet(name = "confirmModifier2", urlPatterns = {"/confirmModifier2"})
public class confirmModifier2 extends HttpServlet {
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
            String id = request.getParameter("id");
            String mention = request.getParameter("mention");
            String nomMatiere = request.getParameter("nom_matiere");
            String nomEnseignant = request.getParameter("nom_enseignant");
            String prenomEnseignant = request.getParameter("prenom_enseignant");
            String date = request.getParameter("date");
            String heureDebut = request.getParameter("heureDebut");
            String heureFin = request.getParameter("heureFin");
            String nbMax = request.getParameter("nbMax");
            
            
            //transtypage de date en sql date
            Date dateC = null;
            java.sql.Date dateSql;
            try {
                dateC = new SimpleDateFormat("dd-mm-yy").parse(date);
                
            } catch (ParseException ex) {
                Logger.getLogger(confirmCreation1.class.getName()).log(Level.SEVERE, null, ex);
            }
            dateSql = new java.sql.Date(dateC.getTime());   
            
            //transtypage de time en sql time
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat("HH:mm").parse(heureDebut);
                date2 = new SimpleDateFormat("HH:mm").parse(heureFin);
            }
            catch (ParseException e) {
                request.setAttribute("time_error", "Please enter time in format HH:mm");
            } 
            java.sql.Time time1 = new Time(date1.getTime());
            java.sql.Time time2 = new Time(date2.getTime());
            
            //transtypage de nbEleveMax en int
            int nbM = Integer.parseInt(nbMax);
            
            
            //transtypage de idCréneau en int
            int idCreneau = Integer.parseInt(id);
            
            //obtenir l'idEnseignant
            ResultSet rs = stmt.executeQuery("select *"
                            + "from enseignant "
                            + "where upper(nomEnseignant) = upper('"+nomEnseignant+"')"
                            + "and upper(prenomEnseignant) = upper('"+prenomEnseignant+"')");
            rs.next();
            int idEnseignant = rs.getInt(1);
            
            //obtenir l'idMatiere
            rs = stmt.executeQuery("select *"
                            + "from matiere "
                            + "where upper(nomMatiere) = upper('"+nomMatiere+"')");
            rs.next();
            int idMatiere = rs.getInt(1);

            //obtenir idMention
            rs = stmt.executeQuery("select * from mention where nomMention = '"+mention+"'");
            rs.next();
            int idMention = rs.getInt(1);

            
             PreparedStatement editStatement = conn.prepareStatement(
                        "update creneau set dateCreneau = ?, heureDebut = ?, heureFin = ?, nbEleveMax = ?, idMatiere = ?, idEnseignant = ? where idCreneau = ?");
            editStatement.setDate(1, dateSql);
            editStatement.setTime(2, time1);
            editStatement.setTime(3, time2);
            editStatement.setInt(4, nbM);
            editStatement.setInt(5, idMatiere);
            editStatement.setInt(6, idEnseignant);
            editStatement.setInt(7, idCreneau);
            
            editStatement.executeUpdate();
            editStatement.close();
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet confirmModifier2</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet confirmModifier2 at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            response.sendRedirect("../OsezLesSciences/modifierCreneau");
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
