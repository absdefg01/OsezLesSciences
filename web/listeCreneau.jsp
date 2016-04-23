<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!-- On definit la configuration d'acces a  la base -->
<sql:setDataSource 
    driver="org.apache.derby.jdbc.ClientDriver"
    url="jdbc:derby://localhost:1527/oserlessciences"
    user="mengzi" password="397949844"
    />

<!DOCTYPE html>

<!-- On definit des requêtes SQL -->
<sql:query var = "creneau" >
    SELECT * FROM creneau
</sql:query>			
    
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>liste des créneaux</title>
    </head>
    <body>
    <center>
        <h1>liste des créneaux</h1>
        <table border="1">
            <!-- Les en-tetes de colonnes -->
            <tr>
                <th>IdCreneau</th>
                <th>Date</th>
                <th>HeureDebut</th>
                <th>HeureFin</th>
                <th>NbEleveMax</th>
                <th>Matiere</th>
                <th>Enseignant</th>
                <th>case à cocher</th>
            </tr>      
            
            <!-- Pour chaque enregistrement de la requête "orders" -->
            <c:forEach var="list" items="${creneau.rows}">
                <!-- On met une ligne dans la  table -->
                <!-- On met une ligne dans la  table -->
                <tr>
                    <td>${list.idCreneau}</td>
                    <td>${list.dateCreneau}</td>
                    <td>${list.heureDebut}</td>
                    <td>${list.heureFin}</td>
                    <td>${list.nbEleveMax}</td>
                    <td>${list.idMatiere}</td>
                    <td>${list.idEnseignant}</td> 
                    <td> <INPUT type="checkbox" name="choix" value="1"></td> 
                    
                </tr>
            </c:forEach>		
        </table>
        
        
                <!--
                La servlet fait : request.setAttribute("errorMessage", "idCreneaux inexistant");
                La JSP récupère cette valeur dans ${requestScope.errorMessage}
                -->
		<div style="color:red">${requestScope.errorMessage}</div>
                <form method="POST" action="afficheCreneau.jsp"> <!-- l'action par défaut est l'URL courant, qui va rappeler la servlet -->
			idCreneau : <input name='login'><br>
			<input type='submit' name='action' value='valider'>
		</form>
    </center>
    </body>
</html>
