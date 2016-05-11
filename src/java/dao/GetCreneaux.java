/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import test.ConfirmInscription;

/**
 *
 * @author VIC
 */
@WebServlet(name = "GetCreneaux", urlPatterns = {"/GetCreneaux"})
public class GetCreneaux  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // On définit la configuration d'acces au serveur SQL
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    Connection connexion = null;
    Statement statement = null;
    ResultSet resultat = null;

    public GetCreneaux() {
    super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            
            /* Chargement du driver JDBC pour MySQL */
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch ( ClassNotFoundException e ) {}

            try {
                connexion = DriverManager.getConnection( URL, USERNAME, PASSWORD );          
            } catch (SQLException ex) {
                Logger.getLogger(ConfirmInscription.class.getName()).log(Level.SEVERE, null, ex);
            }

            ProjectManager projectManager= new ProjectManager();
            ArrayList creneauxData = null;

            creneauxData = projectManager.GetCreneaux(connexion, request, response);
            Gson gson = new Gson();
            String messages = gson.toJson(creneauxData);
            out.println(messages);
        }
        catch (Exception ex){
        out.println("Error: " + ex.getMessage());
        }
        finally{
        out.close();
        }
    }
}