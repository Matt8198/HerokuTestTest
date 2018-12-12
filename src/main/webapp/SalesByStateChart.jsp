<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Chiffre d'affaire</title>
    <!-- On charge JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- On charge l'API Google -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {
        'packages':['geochart'],
        // Note: you will need to get a mapsApiKey for your project.
        // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
        'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
      });
      google.charts.setOnLoadCallback(doAjax2);

        function drawRegionsMap(dataArray) {
            var data = google.visualization.arrayToDataTable(dataArray);

            var options = {
                region: 'US', 
                colorAxis: {colors: ['#00853f', 'black', '#e31b23']},
                resolution: 'provinces',
                backgroundColor: '#81d4fa',
                datalessRegionColor: '#f8bbd0',
                defaultColor: '#f5f5f5',
            };

            var chart = new google.visualization.GeoChart(document.getElementById('geochart-colors'));
            chart.draw(data, options);
        }
        ;

        // Afficher les ventes par client
        function doAjax2() {
            $.ajax({
                url: "salesState",
                dataType: "json",
                success: // La fonction qui traite les résultats
                        function (result) {
                            // On reformate le résultat comme un tableau
                            var chartData = [];
                            // On met le descriptif des données
                            chartData.push(["State", "Ventes"]);
                            for (var client in result.records) {
                                chartData.push([client, result.records[client]]);
                            }
                            // On dessine le graphique
                            drawRegionsMap(chartData);
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
    <h1> Chiffres d'affaires par état.</h1>
    <a href='SalesByState' target="_blank">Voir les données brutes</a><br>
    <!-- Le graphique apparaît ici -->
    <div id="geochart-colors" style="width: 900px; height: 500px;"></div>
</body>