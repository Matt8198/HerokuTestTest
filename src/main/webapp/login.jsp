<%-- 
    Document   : login
    Created on : 23 nov. 2018, 22:10:12
    Author     : Utilisateur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style_main_test.css">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    </head>
    <body>
        <form method="POST">
            <label> Login : <input name="name" value="jumboeagle@example.com"></label> <br>
            <label> Mot de passe : <input name="password" value="x"></label>
            <input name="action" value="Connexion" type="SUBMIT">
        </form>  
    </body>
</html>

