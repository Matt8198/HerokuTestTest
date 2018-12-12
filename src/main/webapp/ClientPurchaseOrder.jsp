<!DOCTYPE html>

<html>
    
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projet J2E - PMD</title>
        
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        
        <script>
            
            $(document).ready(
                function () {
                    showCodesPO();
                }
            );

            function showCodesPO() {
                $.ajax({
                    url: "allPO",
                    dataType: "json",
                    error: showError,
                    success:
                        function (result) {
                            console.log(result);
                            var template = $('#codesTemplate1').html();
                            var processedTemplate = Mustache.to_html(template, result);
                            $('#affPO').html(processedTemplate);
                        }
                });
            }
            
            function showCodesP() {
                $.ajax({
                    url: "allP",
                    dataType: "json",
                    error: showError,
                    success:
                        function (result) {
                            var template = $('#codesTemplate2').html();
                            var processedTemplate = Mustache.to_html(template, result);
                            $('#affP').html(processedTemplate);
                        }
                });
            }
            
            function addPO(id) {
                $.ajax({
                    url: "addPO",
                    data: {"id":id},
                    dataType: "json",
                    success:
                        function (result) {
                            showCodesPO();
                        },
                    error: showError
                });
                return false;
            }
            
            function deleteCode(code) {
                $.ajax({
                    url: "deletePO",
                    data: {"code": code},
                    dataType: "json",
                    success: 
                            function (result) {
                                showCodesPO();
                            },
                    error: showError
                });
                return false;
            }

            function showError(xhr, status, message) {
                /*alert(JSON.parse(xhr.responseText).message);*/
                console.log("ok");
            }
            
        </script>
        
    </head>
    
    <body>
           
        <div id="affPO"></div>
        <a href='#' onclick='showCodesP()'>Passer une nouvelle commande</a>
        <div id="affP"></div>
        

        <script id="codesTemplate1" type="text/template">
            
            <table>
            
                <tr>
                    <th>Numero de commande</th>
                    <th>Numero de client</th>
                    <th>Numero du produit</th>
                    <th>Quantité</th>
                    <th>Prix</th>
                    <th>Date de vente</th>
                    <th>Date de livraison</th>
                    <th>Action</th>
                </tr>
                
                {{#records}}
                    <tr>
                        <td>{{order_num}}</td>
                        <td>{{customer_id}}</td>
                        <td>{{product_id}}</td>
                        <td>{{quantity}}</td>
                        <td>{{shipping_cost}}</td>
                        <td>{{sales_date}}</td>
                        <td>{{shipping_date}}</td>
                        <th>
                            <button onclick="deleteCode('{{order_num}}')">Supprimer</button>
                        </th>
                    </tr>
                {{/records}}
            
            </table>
            
        </script>
        
        <script id="codesTemplate2" type="text/template">
            
            <table>
            
                <tr>
                    <th>Numero du produit</th>
                    <th>Numero du fournisseur</th>
                    <th>Code du produit</th>
                    <th>Prix</th>
                    <th>Quantité disponible</th>
                    <th>Balisage</th>
                    <th>Disponible</th>
                    <th>Description</th>
                    <th>Action</th>
                    <th></th>
                </tr>
                
                {{#records}}
                    <tr>
                        <td>{{Product_ID}}</td>
                        <td>{{Manufacturer_ID}}</td>
                        <td>{{Product_Code}}</td>
                        <td>{{Purchase_Cost}}</td>
                        <td>{{Quantity_on_hand}}</td>
                        <td>{{markup}}</td>
                        <td>{{available}}</td>
                        <td>{{Description}}</td>
                        <th>
                            <button onclick="addPO('{{Product_ID}}')">Commander</button>
                        </th>
                    </tr>
                {{/records}}
            
            </table>
            
        </script>

    </body>
    
</html>

