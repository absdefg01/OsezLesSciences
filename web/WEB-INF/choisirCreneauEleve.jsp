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
        
        
        <link href="static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet">
        <script src="static/js/jquery-latest.min.js"></script>
        <script src="static/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/afficheCreneaux.js"></script>
        
        <link rel="stylesheet" type="text/css" href="static/css/base.css">
        <title>Sélection de créneau</title>
    </head>
    <body>
        <div id="content">
            <h2>Veuiller sélectionner un ou plusieurs créneaux :</h2>

            <div class= "row">
		<fieldset>
					<legend id="choix">1ere Etape: Choisissez vos UE et Horaires</legend>
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
				<td class="Mon"> </td>
				<td class="Tue"></td>
				<td class="Wed"> </td>
				<td class="Thu"></td>
				<td class="Fri"> </td>
			</tr>
			
			
			<tr id="2">
				<td class="heure"> 10h00-12h00 </td>
				<td class="Mon"> </td>
				<td class="Tue"> </td>
				<td class="Wed"></td>
				<td class="Thu"> </td>
				<td class="Fri"> </td>
			</tr>
			
			<tr >
				<td >  </td>
				<td class="Mon"> </td>
				<td class="Tue"> </td>
				<td class="Wed"></td>
				<td class="Thu"> </td>
				<td class="Fri"> </td>
			</tr>
			
			
			
			<tr id="3">
				<td class="heure"> 13h30-15h30 </td>
				<td class="Mon"> </td>
				<td class="Tue"> </td>
				<td class="Wed"> </td>
				<td class="Thu"> </td>
				<td class="Fri"> </td>
			</tr>
			
			<tr id="4">
				<td class="heure"> 15h30-17h30 </td>
				<td class="Mon"> </td>
				<td class="Tue"></td>
				<td class="Wed"> </td>
				<td class="Thu"> </td>
				<td class="Fri"> </td>
			</tr>
				</table>
	</div>
            
            <form id="inscriptionForm" method="post" action="ConfirmInscription">
                
                <label for="prenom"> Prénom : </label>
                <input type="text" id="prenom" name="prenom" value="" maxlength="20" />
                <span class="erreur">${form.erreurs['prenom']}</span><br>
                
                <label for="nom">Nom : </label>
                <input type="text" id="nom" name="nom" value="" maxlength="20" />
                <span class="erreur">${form.erreurs['nom']}</span><br>
                
                <label for="mail">Adresse mail : </label>
                <input type="text" id="mail" name="mail" value="" maxlength="60" />
                <span class="erreur">${form.erreurs['mail']}</span><br>
                
                <label for="mdp">Mot de passe : </label>
                <input type="password" id="mdp" name="mdp" maxlength="20">
                <span class="erreur">${form.erreurs['mdp']}</span><br>
                
                <label for="confirmation">Confirmer le mot de passe : </label>
                <input type="password" id="confirmation" name="confirmation" maxlength="20"><br>
                
                <input id="valid" type="submit" value="Valider"><br>
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </form>
        </div>
    </body>
</html>
