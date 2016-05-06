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
import java.sql.Time;
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
@WebServlet(name = "supprimerCreneau", urlPatterns = {"/supprimerCreneau"})
public class supprimerCreneau extends HttpServlet {
    // On définit la configuration d'accès au serveur SQL
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

            /**
             * afficher des liste de créneau
             */
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Suppression des créneaux</title>");
            out.println("<meta charset=\"utf-8\" />\n" +
"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
"		<link rel=\"stylesheet\" href=\"assets4/css/main.css\" />");
            out.println("</head>");
            
            out.println("<body class=\"homepage\">");
            out.println("<div id=\"page-wrapper\">");
            out.println("<header id=\"header\">\n" +
"					<div class=\"logo container\">\n" +
"						<div>\n" +
"							<h1>Suppression des créneaux</h1>\n" +
"						</div>\n" +
"					</div>\n" +
"				</header>");

            
            out.println("<nav id=\"nav\">\n" +
"					<ul>\n" +
"						<li class=\"current\"><a href=\"gererCreneau.html\">Gérer des créneaux</a></li>\n" +
"						<li>\n" +
"							<a href=\"connexionAdmin.html\">Déconnectez-vous</a>\n" +
"						</li>\n" +
"                                        </ul>\n" +
"				</nav>");
            
            
            out.println("<table border=\"1\" border='2' align='center' cellpadding='15' cellspacing='10' width='150%'>");
                out.println("<tr style='border:solid;'>");
                    out.println("<th style='border:solid;' align='center'>IdCreneau</th>");
                    out.println("<th style='border:solid;' align='center'>Date</th>");
                    out.println("<th style='border:solid;' align='center'>HeureDebut</th>");
                    out.println("<th style='border:solid;' align='center'>HeureFin</th>");
                    out.println("<th style='border:solid;' align='center'>NbEleveMax</th>");
                    out.println("<th style='border:solid;' align='center'>Matiere</th>");
                    out.println("<th style='border:solid;' align='center'>NomEnseignant</th>");
                    out.println("<th style='border:solid;' align='center'>PrenomEnseignant</th>");
                    out.println("<th style='border:solid;' align='center'>case à cocher</th>");
                 out.println("</tr>");
                 
                 //obtenir les colonne de créneau
                 ResultSet rs = stmt.executeQuery("SELECT * FROM  creneau c \n" +
"    INNER JOIN  matiere m ON c.IDMATIERE = m.IDMATIERE \n" +
"    inner join enseignant e on c.IDENSEIGNANT = e.IDENSEIGNANT");
                
                    
                while(rs.next()){
                    String idCreneauH = rs.getString(1);
                    String dateCreneauH = rs.getString(2);
                    Time heureDebutH = rs.getTime(3);
                    Time heureFinH = rs.getTime(4);
                    String nbEleveMaxH = rs.getString(5);
                    String nomMatiereH = rs.getString(9);
                    String nomEnseignantH = rs.getString(12);
                    String prenomEnseignantH = rs.getString(13);

                out.println("<tr style='border:solid;'>");
                    out.println("<td style='border:solid;' align='center'>"+idCreneauH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+dateCreneauH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+heureDebutH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+heureFinH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nbEleveMaxH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nomMatiereH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nomEnseignantH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+prenomEnseignantH+"</td>");
                    out.println("<form method='post' action='confirmSupprimer'>");
                    out.println("<td style='border:solid;' align='center'><INPUT type='checkbox' name='choix1' value='"+idCreneauH+"'></td>");

                out.println("</tr>");
                }
            out.println("</table>");
            
            out.println("<input type='submit' name='supprimer' value='supprimer' style='margin-left:550px'>");
            out.println("</form>");
            out.println("<br>");
            out.println("<br>");
            
            out.println("<form method='post' action='gererCreneau.html'>");
            out.println("<input type='submit' name='retourner' value='retourner' style='margin-left:550px'>");
            out.println("</form>");
            out.println("</div>");
            
            out.println("<div id=\"copyright\" style='margin-left:450px'><ul class=\"menu\">\n" +
"			<li>&copy; ZHAO Mengzi WANG Li. All rights reserved</li></ul></div>");
            
            out.println("<!-- Scripts -->\n" +
"			<script src=\"assets/js/jquery.min.js\"></script>\n" +
"			<script src=\"assets/js/jquery.dropotron.min.js\"></script>\n" +
"			<script src=\"assets/js/skel.min.js\"></script>\n" +
"			<script src=\"assets/js/skel-viewport.min.js\"></script>\n" +
"			<script src=\"assets/js/util.js\"></script>\n" +
"			<!--[if lte IE 8]><script src=\"assets/js/ie/respond.min.js\"></script><![endif]-->\n" +
"			<script src=\"assets/js/main.js\"></script>");
            out.println("</body>");
            out.println("</html>");
            
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
