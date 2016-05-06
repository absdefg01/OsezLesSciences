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
@WebServlet(name = "confirmModifier", urlPatterns = {"/confirmModifier"})
public class confirmModifier extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
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
        
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            /* Gérer les éventuelles erreurs ici. */
        }
        
        try{
            //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();
            
            //checkbox contient l   'idCreneau
            String[] checkbox = request.getParameterValues("choix1");
            int value = Integer.parseInt(checkbox[0]);
            out.println(value);
            ResultSet rs = stmt.executeQuery("SELECT * FROM  creneau c INNER JOIN  matiere m ON c.IDMATIERE = m.IDMATIERE inner join enseignant e on c.IDENSEIGNANT = e.IDENSEIGNANT where c.IDCRENEAU = '"+value+"'");
            rs.next();
            
            String idCreneauH = rs.getString(1);
            String nomMatiereH = rs.getString(9);
            String nomEnseignantH = rs.getString(12);
            String prenomEnseignantH = rs.getString(13);
            String dateCreneauH = rs.getString(2);
            Time heureDebutH = rs.getTime(3);
            Time heureFinH = rs.getTime(4);
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
            out.println("<title>Modification des créneaux</title>"); 
            out.println("<meta charset=\"utf-8\" />");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
"		<link rel=\"stylesheet\" href=\"assets2/css/main.css\" />");
            out.println("</head>");
            
            out.println("<body class=\"homepage\">\n");
            
            out.println("<div id=\"page-wrapper\">");
            out.println("<div id=\"header-wrapper\">\n" +
"					<header id=\"header\" class=\"container\">\n" +
"						<!-- Logo -->\n" +
"							<div id=\"logo\">\n" +
"								<h1>Osez les sciences</h1>\n" +
"                                                        </div>\n" +
"						<!-- Nav -->\n" +
"							<nav id=\"nav\">\n" +
"								<ul>\n" +
"									<li><a href=\"gererCreneau.html\">Gérer des créneaux</a></li>\n" +
"									<li><a href=\"connexionAdmin.html\">Déconnectez vous</a></li>\n" +
"								</ul>\n" +
"							</nav>\n" +
"					</header>\n" +
"				</div>");
            out.println("</div>");
            
            out.println("<!-- Main -->" +
"				<div id=\"main-wrapper\">" +
"					<div class=\"container\">" +
                    "<div class=\"8u 12u(medium) important(medium)\">" +
"								<!-- Content -->\n" +
"                                                                <div id=\"content\" style=\"margin-left: 270px\">\n" +
"										<section class=\"last\">\n"); 
            
            out.println("<h1 style='margin-left:130px'>modifier des créneaux</h1>");
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
            out.println("date de Creneau <input type=\"date\" name=\"date\" value='"+dateCreneauN+"'><br><br>\n");
            out.println("heure de début <input type=\"text\"  name=\"heureDebut\" value='"+heureDebutH+"' ><br>\n");
            out.println(" heure de fin <input type=\"text\"  name=\"heureFin\" value='"+heureFinH+"'><br>\n");
            out.println("nombre d'éleves maximum <input type=\"text\" name=\"nbMax\" value='"+nbEleveMaxH+"'><br>\n");
            out.println("<input type=\"submit\" value=\"Suivant\" style='margin-left:45px'>\n");
            out.println("<input type=\"reset\" value=\"Effacer\">\n");
            
            out.println("</form>");
            
            out.println("</section></div></div></div></div></div>");

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
