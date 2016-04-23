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
            //String mention = request.getParameter("mention");
            
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
                rs = stmt.executeQuery("select *"
                            + "from enseignant "
                            + "where upper(nomEnseignant) = upper('"+nom_enseignant+"')"
                            + "and upper(prenomEnseignant) = upper('"+prenom_enseignant+"')");
                rs.next();
                idEnseignant = rs.getInt(1);
                out.println("idEnseignant : "+idEnseignant);
                out.println("nom Enseignant : "+nom_enseignant);
                out.println("prenom enseigant : " + prenom_enseignant);
                
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
            out.println("idMention : " + idMention);
            
            
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
                
                out.println("idMatiere : "+id_matiere);
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
                out.println("idMatiere : "+ id_matiere);
                out.println("nom matiere : "+nom_matiere);
            }
            

            /**
             * date
             */
            String dateCreneau = request.getParameter("date");
            Date dateC = null;
            java.sql.Date dateSql;
            try {
                dateC = new SimpleDateFormat("dd-mm-yy").parse(dateCreneau);
                out.println("date : "+dateC);
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
            int i = 1;
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
            
            
            
            rs = stmt.executeQuery("select count(*) as nb from creneau");
            rs.next();
            
            //nb de matieres correspondants dans BDD
            int nbCreneau = rs.getInt("nb");
            out.println(nbCreneau);

            //id de nouveau matiere qu'on veut ajouter
            id_creneau = nbCreneau + 1;
                
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
            response.sendRedirect("listerCreneau.html");
            
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
