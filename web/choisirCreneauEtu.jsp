<%-- 
    Document   : choisirCreneauEtu
    Created on : 24 avr. 2016, 14:34:11
    Author     : VIC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="static/css/base.css">
        <title>Sélection de créneau</title>
    </head>
    <body>
        <div id="content">
            <h2>Veuiller sélectionner un ou plusieurs créneaux :</h2>

            <div class="tabCreneaux"> A faire ...</div>
            
            <form id="inscriptionForm" method="post" action="confirmInscription">
                
                <label for="prenom"> Prénom : </label><input type="text" id="prenom">
                <span class="erreur">${erreurs['prenom']}</span><br>
                <label for="nom">Nom : </label><input type="text" id="nom">
                <span class="erreur">${erreurs['nom']}</span><br>
                
                <label for="mail">Adresse mail : </label><input type="text" id="mail">
                <span class="erreur">${erreurs['mail']}</span><br>
                <label for="mdp">Mot de passe : </label><input type="password" id="mdp">
                <span class="erreur">${erreurs['mdp']}</span><br>
                <label for="confirmation">Confirmer le mot de passe : </label><input type="password" id="confirmation"><br>
                
                <input type="submit" value="Valider">
            </form>
        </div>
    </body>
</html>