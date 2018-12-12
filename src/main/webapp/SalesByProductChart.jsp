<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Chiffre d'affaire</title>
    <!-- On charge JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- On charge l'API Google -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {packages: ['corechart', 'bar']});
        google.charts.setOnLoadCallback(doAjax);

        function drawBasic(dataArray) {

            var data = new google.visualization.DataTable(dataArray);
            data.addColumn('string', 'Produits');
            data.addColumn('number', 'Ventes');

            for (var i = 1; i < dataArray.length; i++) {
                data.addRows([[dataArray[i][0], dataArray[i][1]]]);
            }

            var options = {
                title: "Chiffre d'affaires par produits",
                hAxis: {
                    title: 'Produits',
                    viewWindow: {
                        min: [7, 30, 0],
                        max: [17, 30, 0]
                    }
                },
                vAxis: {
                    title: 'Ventes en Euros'
                }
            };

            var chart = new google.visualization.ColumnChart(
                    document.getElementById('chart_div'));

            chart.draw(data, options);
        }

        // Afficher les ventes par client
        function doAjax() {
            $.ajax({
                url: "salesProduct",
                dataType: "json",
                success: // La fonction qui traite les résultats
                        function (result) {
                            // On reformate le résultat comme un tableau
                            var chartData = [];
                            // On met le descriptif des données
                            chartData.push(["Produits", "Ventes"]);
                            for (var client in result.records) {
                                chartData.push([client, result.records[client]]);
                            }
                            // On dessine le graphique
                            drawBasic(chartData);
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
    <a href='salesByProduct' target="_blank">Voir les données brutes</a><br>
    <!-- Le graphique apparaît ici -->
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
</body>