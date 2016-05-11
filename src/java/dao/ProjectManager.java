/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VIC
 */

public class ProjectManager {
    public String InsertMessage(Connection connection, HttpServletRequest request,
    HttpServletResponse response) throws Exception {return null;
    //Previous Post
    }

    public ArrayList GetCreneaux(Connection connection, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
        ArrayList creneaux = null;
        try {
            // Here you can validate before connecting DAO class, eg. User session condition
            Project project= new Project();
            creneaux=project.GetMessages(connection, request, response);
        } 
        catch (Exception e) {
            throw e;
        }
        return creneaux;
    }

}
