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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();  
        try{
            //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();

            
            /**
             * enseignant
             */
            String nom_enseignant = request.getParameter("nom_enseignant");
            String prenom_enseignant = request.getParameter("prenom_enseignant");
            //vérifier si enseignant est dans BBD
            //si non, ajouter dans BDD
            ResultSet rs = stmt.executeQuery("select count(*) as nb "
                        + "from enseignant "
                            + "where upper(nomEnseignant) = upper('"+nom_enseignant+"')"
                            + "and upper(prenomEnseignant) = upper('"+prenom_enseignant+"')");
            rs.next();
            
            
            //nb de prénom correspondants dans BDD
            int nbEnseignant = rs.getInt("nb");
            
            //l'identifiant de l'enseignant
            int idEnseignant;
            if(nbEnseignant == 0){
                //retourner l'id d'enseignant
                rs = stmt.executeQuery("select count(*) as nb from enseignant");
                rs.next();
                //nb d'enseignants dans BBD
                idEnseignant = rs.getInt("nb");
                //id de nouveau enseignant qu'on veut ajouter
                idEnseignant = idEnseignant + 1;
                
                //ajouter nouveau enseignant dans BDD
                PreparedStatement editStatement = conn.prepareStatement(
                                "INSERT into Enseignant VALUES (?,?,?)");
                editStatement.setInt(1, idEnseignant);
                editStatement.setString(2, nom_enseignant);
                editStatement.setString(3, prenom_enseignant);
                
                editStatement.executeUpdate();
                editStatement.close();
                
            }else{
                rs = stmt.executeQuery("select *"
                            + "from enseignant "
                            + "where upper(nomEnseignant) = upper('"+nom_enseignant+"')"
                            + "and upper(prenomEnseignant) = upper('"+prenom_enseignant+"')");
                rs.next();
                idEnseignant = rs.getInt(1);
                
            }
            
            /**
             * mention
             * 1 - stocker les mentions existant dans la BDD
             * 2 - obtenir leurs leur IDs en utilisant leurs nom
             */
            String nom_mention = request.getParameter("mention");
            //retourner l'id de la mention
            rs = stmt.executeQuery("select idMention from mention "
                    + "where upper(nommention) = upper('"+nom_mention+"')");
            rs.next();
            //id Mention
            int idMention = rs.getInt(1);
           
            
            /**
             * matiere
             */
            int id_matiere;
            String nom_matiere = request.getParameter("nom_matiere");
            //vérifier si matiere est dans BBD
            //si non, ajouter dans BDD
            rs = stmt.executeQuery("select count(nomMatiere) as nb "
                    + "from matiere where upper(nomMatiere) = upper('"+nom_matiere+"')");
            rs.next();
            //nb de matieres correspondants dans BDD
            int nbM = rs.getInt("nb");
            
            //s'il n'y a pas
            if(nbM==0){
                //retourner l'id de matiere
                rs = stmt.executeQuery("select count(*) as nb from matiere");
                rs.next();
                //nb total de matières existant dans BDD
                id_matiere = rs.getInt("nb");
                //id de nouveau matiere qu'on veut ajouter
                id_matiere = id_matiere + 1;
                
                //ajouter nouveau enseignant dans BDD
                PreparedStatement editStatement = conn.prepareStatement(
                                "INSERT into matiere VALUES (?,?,?)");
                editStatement.setInt(1, id_matiere);
                editStatement.setString(2, nom_matiere);
                editStatement.setInt(3, idMention);
                
                editStatement.executeUpdate();
                editStatement.close();
            }else{
                rs = stmt.executeQuery("select * "
                    + "from matiere "
                        + "where upper(nomMatiere) = upper('"+nom_matiere+"')");
                rs.next();
                
                id_matiere = rs.getInt(1);
                
            }
            

            /**
             * date
             */
            String dateCreneau = request.getParameter("date");
            Date dateC = null;
            java.sql.Date dateSql;
            try {
                dateC = new SimpleDateFormat("dd-mm-yy").parse(dateCreneau);
                
            } catch (ParseException ex) {
                Logger.getLogger(confirmCreation1.class.getName()).log(Level.SEVERE, null, ex);
            }
            dateSql = new java.sql.Date(dateC.getTime());            
            

            /**
             * heureDebut heureFin
             */
            String timeDebut = request.getParameter("heureDebut");
            String timeFin = request.getParameter("heureFin");
            
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat("HH:mm").parse(timeDebut);
                date2 = new SimpleDateFormat("HH:mm").parse(timeFin);
            }
            catch (ParseException e) {
                request.setAttribute("time_error", "Please enter time in format HH:mm");
            } 
            java.sql.Time time1 = new Time(date1.getTime());
            java.sql.Time time2 = new Time(date2.getTime());
            
            
            /**
             * nb eleve max
             */
            String nbMax = request.getParameter("nbMax");
            int nbMaxE = Integer.parseInt(nbMax);
            
            /**
             * creneau
             */
            int id_creneau;
            //vérifier si matiere est dans BBD
            //si non, ajouter dans BDD
            //SELECT id, prenom, nom, CAST( date_ajout AS DATE ) AS date_ajout_cast, budget 
//FROM client
//            rs = stmt.executeQuery("select count(*) as nb, idCreneau,"
//                    + "cast(dateCreneau as date) as date,heureDebut,"
//                    + "heureFin,cast(nbEleveMax as int) as nbMaxE"
//                    + " from creneau "
//                    + "where date = cast('"+dateSql+"' as date)"
//                    + " and heureDebut = '"+time1+"'"
//                    + "and heureFin = '"+time2+"' "
//                    + "and nbMaxE = cast('"+nbMaxE+"' as int");
//            rs.next();
//            out.println("<br>");
//            int nbb = rs.getInt("nb");
//            out.println(nbb);
            
            
            
            rs = stmt.executeQuery("select max(idCreneau)  from creneau");
            rs.next();
            
            int idCreneauMax = rs.getInt(1);
            //id de nouveau matiere qu'on veut ajouter
            id_creneau = idCreneauMax + 1;
                
            //ajouter nouveau enseignant dans BDD
            PreparedStatement editStatement = conn.prepareStatement(
                        "INSERT into creneau VALUES (?,?,?,?,?,?,?)");
            editStatement.setInt(1, id_creneau);
            editStatement.setDate(2, dateSql);
    //      editStatement.setTime(3, new Time(date1.getTime()));
    //      editStatement.setTime(4, new Time(date2.getTime()));
            editStatement.setTime(3, time1);
            editStatement.setTime(4, time2);
            editStatement.setInt(5, nbMaxE);
            editStatement.setInt(6, id_matiere);
            editStatement.setInt(7, idEnseignant);
            editStatement.executeUpdate();
            editStatement.close();
//            response.sendRedirect("listeCreneau.jsp");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("listeCreneau.java");
//            dispatcher.forward(request, response);
            //On ferme la connection avec le serveur SQL
            
            
            
            /**
             * afficher des liste de créneau
             */
            //obtenir le nb de créneau
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Liste des créneaux</title>"); 
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
"                                                                <div id=\"content\" style=\"margin-left: 170px\">\n" +
"										<section class=\"last\">\n"); 
	out.println("<h1 style='margin-left:280px'>liste des créneau</h1>");
            
            out.println("<table border='2' align='center' cellpadding='15' cellspacing='10' width='150%'>");
                out.println("<tr style='border:solid;'>");
                    out.println("<th style='border:solid;' align='center'>IdCreneau</th>");
                    out.println("<th style='border:solid;'>Date</th>");
                    out.println("<th style='border:solid;'>HeureDebut</th>");
                    out.println("<th style='border:solid;'>HeureFin</th>");
                    out.println("<th style='border:solid;'>NbEleveMax</th>");
                    out.println("<th style='border:solid;'>Matiere</th>");
                    out.println("<th style='border:solid;'>NomEnseignant</th>");
                    out.println("<th style='border:solid;'>PreomEnseignant</th>");
                out.println("</tr>");

                //obtenir les colonne de créneau
                    rs = stmt.executeQuery("SELECT * FROM  creneau c \n" +
"    INNER JOIN  matiere m ON c.IDMATIERE = m.IDMATIERE \n" +
"    inner join enseignant e on c.IDENSEIGNANT = e.IDENSEIGNANT");
                
                    
                while(rs.next()){
                    String idCreneauH = rs.getString(1);
                    String dateCreneauH = rs.getString(2);
                    String heureDebutH = rs.getString(3);
                    String heureFinH = rs.getString(4);
                    String nbEleveMaxH = rs.getString(5);
                    String nomMatiereH = rs.getString(9);
                    String nomEnseignantH = rs.getString(12);
                    String prenomEnseignantH = rs.getString(13);

                out.println("<tr style='border:solid;' align='center'>");
                    out.println("<td style='border:solid;' align='center'>"+idCreneauH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+dateCreneauH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+heureDebutH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+heureFinH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nbEleveMaxH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nomMatiereH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nomEnseignantH+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+prenomEnseignantH+"</td>");
                out.println("</tr>");
                }
            out.println("</table>");
            out.println("<form method='post' action='../OsezLesSciences/modifierCreneau' style='margin-left:260px;'>");
            out.println("<input type='submit' name='modifier' value='modifier'>");
            out.println("</form>");
            out.println("<br>");
            
            out.println("<form method='post' action='gererCreneau.html' style='margin-left:255px;'>");
            out.println("<input type='submit' name='retourner' value='retourner'>");
            out.println("</form>");
            
										out.println("</section>\n" +
"									</div>\n" +
"\n" +
"							</div>\n" +
"						</div>\n" +
"					</div>\n" +
"				</div>");
            
            
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