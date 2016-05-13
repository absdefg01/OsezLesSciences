<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="" />
        <meta name="keywords" content="" />
        <link rel="stylesheet" type="text/css" href="static/css/base.css">
        <title>Connexion</title>
    </head>
    <body>
    
    <div id="content">
        <h2>Faire sa demande : </h2>

        <a href="ConfirmInscription">Choisir ses créneaux.</a> 

        <h2>J'ai déjà  fait ma demande : </h2>
         
        <form id="eleveConnexionForm" method="post" action="ConnexionEleve">
            <label for="mail">Adresse mail : </label> <input type="text" id="mail" name="mail" value="" maxlength="60" /><br>
            
            <label for="mdp">Mot de passe : </label> <input type="password" id="mdp" name="mdp" maxlength="20"><br>
                
            <input type="submit" value="Valider"><br>
            
            <p> <span class="erreur">${form.erreurs['etat']}</span></p>
        </form>
         
        <h2>Connexion administrative </h2>
        <form method="post" action="connexionAdmin.html">
            <input type="submit" value="Connexion">
        </form>
    </div>
    
    
    </body>
</html>
