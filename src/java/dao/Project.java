/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Creneau;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VIC
 */
public class Project {
    public String InsertMessage(Connection connection, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
    // Previous Post
        return null;
    }

    public ArrayList GetMessages(Connection connection,HttpServletRequest request,HttpServletResponse response) throws Exception{
        ArrayList creneauData = new ArrayList();
        try{
            PreparedStatement ps = connection.prepareStatement("Select idCreneau,DATE_FORMAT(dateCreneau,'%a'), Hour(heuredebut), e.nomEnseignant, me.nomMention, ma.nomMatiere\n" +
                                                                "From Creneau c, Enseignant e,Matiere ma, Mention me \n" +
                                                                "Where ma.idMatiere = c.idMatiere\n" +
                                                                "And me.idMention = ma.idMention\n" +
                                                                "And e.idEnseignant = c.idEnseignant;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Creneau creneau = new Creneau();
                creneau.setIdCreneau(rs.getString(1));
                creneau.setDateCreneau(rs.getString(2));
                creneau.setHeure(rs.getString(3));
                creneau.setEnseignant(rs.getString(4));
                creneau.setMention(rs.getString(5));
                creneau.setMatiere(rs.getString(6));
                creneau.setConfirmation(false);
                
                creneauData.add(creneau);
            }
            return creneauData;
        }
        catch(Exception e){
            throw e;
        }
    }

}
