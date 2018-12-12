<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Chiffre d'affaire</title>
    <!-- On charge JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- On charge l'API Google -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(doAjax);

        function drawTable(dataArray) {
            var data = new google.visualization.DataTable(dataArray);
            data.addColumn('string', 'Name');
            data.addColumn('number', 'Ventes');
            for (var i=1; i<dataArray.length;i++){
                data.addRows([[dataArray[i][0],dataArray[i][1]]]);
            }

            var table = new google.visualization.Table(document.getElementById('table_div'));

            table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
        }

        // Afficher les ventes par client
        function doAjax() {
            $.ajax({
                url: "salesCustomer",
                dataType: "json",
                success: // La fonction qui traite les résultats
                        function (result) {
                            // On reformate le résultat comme un tableau
                            var chartData = [];
                            // On met le descriptif des données
                            chartData.push(["Client", "Ventes"]);
                            for (var client in result.records) {
                                chartData.push([client, result.records[client]]);
                            }
                            // On dessine le graphique
                            drawTable(chartData);
                        },
                error: showError
            });
        }

        // Fonction qui traite les erreurs de la requête
        function showError(xhr, status, message) {
            alert("Erreur: " + status + " : " + message);
        }

    </script>
</head>
<body>
    <h1> Chiffres d'affaires des clients.</h1>
    <a href='salesByCustomer' target="_blank">Voir les données brutes</a><br>
    <!-- Le graphique apparaît ici -->
    <div id="table_div" style="width: 900px; height: 500px;"></div>
</body>