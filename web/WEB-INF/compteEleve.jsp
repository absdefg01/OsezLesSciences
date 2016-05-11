<%-- 
    Document   : choisirCreneauEleve
    Created on : 24 avr. 2016, 14:34:11
    Author     : VIC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
	
        <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
        <script src="static/bootstrap/js/bootstrap.js"></script>
        <link rel="stylesheet" type="text/css" href="static/css/base.css">
        <title>Mon compte</title>
    </head>
    <body>
        <div id="content">
            <h2>Liste des créneaux demandés :</h2>

            <div class= "row">
		<fieldset>
					<legend id="choix"> <p>Votre Emploi du Temps </p>
					</legend>
		<table class="table" > <!-- Tableau de proposition de Crénaux  -->
			<tr>             <!-- Les jours dans la semaine  -->
				<th>Horaire</th>
				<th>Lundi</th>  
				<th>Mardi</th>
				<th>Mercredi</th>
				<th>Jeudi</th>
				<th>Vendredi</th>
			</tr>
		
			<tr id="1">
				<td  class="heure" >  08h00-10h00 </td>
				<td class="lundi"> </td>
				<td class="mardi"></td>
				<td class="mercredi"> </td>
				<td class="jeudi"></td>
				<td class="vendredi"> </td>
			</tr>
			
			
			<tr id="2">
				<td class="heure"> 10h00-12h00 </td>
				<td class="lundi"> </td>
				<td class="mardi"> </td>
				<td class="mercredi"></td>
				<td class="jeudi"> </td>
				<td class="vendredi"> </td>
			</tr>
			
			<tr >
				<td >  </td>
				<td class="lundi"> </td>
				<td class="mardi"> </td>
				<td class="mercredi"></td>
				<td class="jeudi"> </td>
				<td class="vendredi"> </td>
			</tr>
			
			
			
			<tr id="3">
				<td class="heure"> 13h30-15h30 </td>
				<td class="lundi"> </td>
				<td class="mardi"> </td>
				<td class="mercredi"> </td>
				<td class="jeudi"> </td>
				<td class="vendredi"> </td> 
			</tr>
			
			<tr id="4">
				<td class="heure"> 15h30-17h30 </td>
				<td class="lundi"> </td>
				<td class="mardi"></td>
				<td class="mercredi"> </td>
				<td class="jeudi"> </td>
				<td class="vendredi"> </td>
			</tr>
				</table>
				
			<p>  Nb: Notez que sont indiqués dans votre l'emploi  que les crenaux horaires auxquels vous avez  été accepté  </p>	
	</div>
                
            <p><label for="prenom"> Prénom : </label><span>t</span></p>

            <p><label for="nom">Nom : </label><span>t</span></p>

            <p><label for="mail">Adresse mail : </label><span>t</span></p>
        </div>
    </body>
</html>
