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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="style_main_test.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="editabletable/jquery.tabledit.js"></script>

        <title>JSP Page</title>
        <script>
            $(document).ready(
                    function () {
                        showDataPerso();
                        showCustomersInState();
                    }
            );
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
            function showDataPerso() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "AllPersoInfo",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                console.log(result.datasPerso.customerid);
                                $('input[name="ID"]').val(result.datasPerso.name);
                                $('output[name="ID"]').val(result.datasPerso.name);
                            }
                });
            }
            function showCustomersInState() {
                $.ajax({
                    url: "AllPersoInfo",
//                    data: {"state": selectedState},
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                var template = $('#customerTemplate').html();
                                console.log("diego", result.datasPerso);
                                var processedTemplate = Mustache.to_html(template, {records: result.datasPerso});
                                $('#customerD').html(processedTemplate);
                            },
                    error: showError
                });
            }
        </script>
    </head>

    <body>
        <h1>Bienvenue ${sessionScope.name} </h1>



        <table class="table table-striped table-bordered" id="example-2">
            <thead>
                <tr>
                    <th>#</th>
                    <th>First</th>
                    <th>Last</th>
                    <th>Nickname</th>
                </tr>
            </thead>
            <tbody>
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
                </tr>
            </tbody>
        </table>

        <input type="text" name="ID"> 
        <output type="text" name="ID"> </output>


        <p id="demo"></p>
        <form method="POST">
            <input type="SUBMIT" name="action" value="Deconnexion">
        </form>       
        <br>
        <br>
        <br>

        <h2>Ca devrait être ici</h2>
        <div id="customerD">

        </div>

        <table class="table table-striped table-bordered" id="example-4">
            <thead>
                <tr>
                    <th>#</th>
                    <th>First</th>
                    <th>Last</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                </tr>
                <tr>
                    <th scope="row">3</th>
                    <td>Larry</td>
                    <td>the Bird</td>
                </tr>
            </tbody>
        </table>


        <script id="customerTemplate" type="text/template">
            <h2>Customers datas</h2>
            <table class="table table-striped table-bordered" id="example-3">
            <thead>
            <tr>
            <th>id</th>
            <th>nom</th>
            <th>ville</th>
            </tr>
            </thead>
            <tbody>                    
            {{#records}}
            <tr><th score="row">{{customerid}}</th><td>{{name}}</td><td>{{city}}</td></tr>
            {{/records}}
            </tbody>                    
            </table>
        </script>

        <script>
            $('#example-2').Tabledit({
                columns: {
                    identifier: [0, 'id'],
                    editable: [[1, 'Nom'], [2, 'email'], [3, 'Ville'], [4, 'Adresse'], [5, 'Telephone'], [6, 'Fax'], [7, 'State']]
                }
            });
            $('#example-3').Tabledit({
                columns: {
                    identifier: [0, 'id'],
                    editable: [[1, 'nom'], [2, 'ville']]
                }
            });
            $('#example-4').Tabledit({
                columns: {
                    identifier: [0, 'id'],
                    editable: [[1, 'nom'], [2, 'ville']]
                }
            });
        </script>
    </body>
</html>