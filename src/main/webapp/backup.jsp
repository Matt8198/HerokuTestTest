<%-- 
    Document   : vue_client
    Created on : 23 nov. 2018, 22:16:12
    Author     : Utilisateur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="style_main_test.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

        <script src="editabletable/jquery.tabledit.js"></script>


        <title>JSP Page</title>

        <script>
            $(document).ready(
                    $("#state").change(showCustomersInState);

//                    function showDataPerso() {
//                        // On fait un appel AJAX pour chercher les codes
//                        $.ajax({
//                            url: "AllPersoInfo",
//                            dataType: "json",
//                            error: showError,
//                            success: // La fonction qui traite les résultats
//                                    function (result) {
//                                        console.log(result.datasPerso.customerid);
//                                        $('input[name="ID"]').val(result.datasPerso.name);
//                                        $('output[name="ID"]').val(result.datasPerso.name);
//                                    }
//                        });
//                    });

            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
            ;

            function showCustomersInState() {
                // Quel est l'état sélectionné ?
                var selectedState = $("#state").val();
                // On fait un appel AJAX pour chercher les clients de cet état
                $.ajax({
                    url: "customersInJSON",
                    data: {"state": selectedState},
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#customerTemplate').html();
                                // On combine le template avec le résultat de la requête
                                console.log(result);
                                var processedTemplate = Mustache.to_html(template, {records: result.records});
                                // On affiche le résultat dans la page
                                $('#customerD').html(processedTemplate);
                            },
                    error: showError
                });
            }


        </script>
    </head>

    <body>
        <h1>Bienvenue ${sessionScope.name} </h1>


        <div id="customerD">
            <table id="example-2">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nom</th>
                        <th>Ville</th>
                    </tr>
                </thead>
                <tbody>
                <script id="customerTemplate" type="text/template">
                    {{#records}}
                        <TR><TH score="row">{{customerid}}</TH><TD>{{name}}</TD><TD>{{city}}</TD></TR>
                    {{/records}}
                </script>
                <!--                
                <tr>
                <th scope="row">1</th>
                <td>Mark</td>
                <td>Otto</td>
                <td>@mdo</td>
                </tr>
                <tr>
                <th scope="row">2</th>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>@fat</td>
                </tr>
                <tr>
                <th scope="row">3</th>
                <td>Larry</td>
                <td>the Bird</td>
                <td>@twitter</td>
                </tr>-->
                </tbody>
            </table>

            <script>
                $('#example-2').Tabledit({
                    columns: {
                        identifier: [0, 'id'],
                        editable: [[1, 'Nom'], [2, 'email']]
                    }
                });
                //            $('#example-2').append('<tr><th scope="row">4</th><td>Larry</td><td>the Bird</td><td>@twitter</td></tr>');

                $('#example-3').Tabledit({
                    columns: {
                        identifier: [0, 'id'],
                        editable: [[1, 'Nom'], [2, 'email'], [3, 'Ville'], [4, 'Adresse'], [5, 'Telephone'], [6, 'Fax'], [7, 'State']]
                    }
                });
            </script>


            <input type="text" name="ID"> 
            <output type="text" name="ID"> </output>

            <p id="demo"></p>
            <form method="POST">
                <input type="SUBMIT" name="action" value="Deconnexion">
            </form>       
            <br>
            <br>
            <br>
            </body>
            </html>
