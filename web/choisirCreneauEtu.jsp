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
            
            <form id="inscriptionForm" method="post" action="demandeInscription">
                
                <label for="prenom"> Prénom : </label><input type="text" id="prenom"><br>
                <label for="nom">Nom : </label><input type="text" id="nom"><br>
                
                <label for="mail">Adresse mail : </label><input type="text" id="mail"><br>
                <label for="mdp">Mot de passe : </label><input type="password" id="mdp"><br>
                
                <input type="submit" value="Valider">
            </form>
        </div>
    </body>
</html>
