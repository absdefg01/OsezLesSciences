/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "confirmModifier", urlPatterns = {"/confirmModifier"})
public class confirmModifier extends HttpServlet {
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
            
            //checkbox contient l   'idCreneau
            String[] checkbox = request.getParameterValues("choix1");
            int value = Integer.parseInt(checkbox[0]);
            ResultSet rs = stmt.executeQuery("SELECT * FROM  creneau c INNER JOIN  matiere m ON c.IDMATIERE = m.IDMATIERE inner join enseignant e on c.IDENSEIGNANT = e.IDENSEIGNANT where c.IDCRENEAU = cast('"+value+"' as Integer)");
            rs.next();
            
            String idCreneauH = rs.getString(1);
            String nomMatiereH = rs.getString(9);
            String nomEnseignantH = rs.getString(12);
            String prenomEnseignantH = rs.getString(13);
            String dateCreneauH = rs.getString(2);
            String heureDebutH = rs.getString(3);
            String heureFinH = rs.getString(4);
            String nbEleveMaxH = rs.getString(5);
            
            
            Date date = null;
            try {
                date = new SimpleDateFormat("yy-mm-dd").parse(dateCreneauH);
            } catch (ParseException ex) {
                Logger.getLogger(confirmModifier.class.getName()).log(Level.SEVERE, null, ex);
            }
            
	    SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
            String dateCreneauN = format.format(date);
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DataBaseAccess</title>"); 
            
            out.println("</head>");
            
            out.println("<body>");
            out.println("<center>");

            out.println("<h1>modifier des créneaux</h1>");
            out.println("<form method=\"post\" action=\"confirmModifier2\">\n");
            
            out.println("Mention <SELECT name=\"mention\" size=\"1\">\n" +
"                <OPTION>Informatique</option>\n" +
"                <OPTION>Mathématique</option>\n" +
"                <OPTION>ElectroniqueEnergieélectriqueetAutomatique</OPTION>   \n" +
"                <option>Physique - Chimie</option>\n" +
"                <option>Science de la vie</option>\n" +
"            </SELECT>\n" +
"            <br>");
            out.println("<input type='hidden' name='id' value='"+idCreneauH+"' >");
            out.println("Matiere <input type=\"text\"  name=\"nom_matiere\"  value='"+nomMatiereH+"' onchange='javascript:this.value=this.value.toUpperCase();'><br>");
            out.println("Nom de l'enseignant <input type=\"text\" name=\"nom_enseignant\" value='"+nomEnseignantH+"'  onchange='javascript:this.value=this.value.toUpperCase();'><br>\n");
            out.println("Prenom de l'enseignant <input type=\"text\" name=\"prenom_enseignant\" value='"+prenomEnseignantH+"' onchange='javascript:this.value=this.value.toUpperCase();'><br>\n");
            out.println("date de Creneau <input type=\"date\" name=\"date\" value='"+dateCreneauN+"'><br>\n");
            out.println("heure de début <input type=\"text\"  name=\"heureDebut\" value='"+heureDebutH+"' ><br>\n");
            out.println(" heure de fin <input type=\"text\"  name=\"heureFin\" value='"+heureFinH+"'><br>\n");
            out.println("nombre d'éleves maximum <input type=\"text\" name=\"nbMax\" value='"+nbEleveMaxH+"'><br>\n");
            out.println("<input type=\"submit\" value=\"Suivant\">\n");
            out.println("<input type=\"reset\" value=\"Effacer\">\n");
            
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
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
