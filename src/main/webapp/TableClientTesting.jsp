<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body>
        
        <table>
            <thead>
                <tr>
                    <th>№</th>
                    <th>Nom</th>
                    <th>Adresse 01</th>
                    <th>Ville</th>
                    <th>Etat</th>
                    <th>Téléphone</th>
                    <th>Fax</th>
                    <th>Email</th>
                    <th>Modifier</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${CustomerList}" >
                <tr>
                    <td style="text-align:center;">${customer.customerId}</td>
                    <td style="text-align:center;">${customer.name}</td>
                    <td style="text-align:center;">${customer.addressLine1}</td>
                    <td style="text-align:center;">${customer.city}</td>
                    <td style="text-align:center;">${customer.state}</td>
                    <td style="text-align:center;">${customer.phone}</td>
                    <td style="text-align:center;">${customer.fax}</td>
                    <td style="text-align:center;">${customer.email}</td>
                    <%--<td style="text-align:center;">${customer.credit}</td>--%>
                    <td style="text-align:center;">
                        <button>Modifier</button>
                    </td>
                </tr> 
                </c:forEach>
            </tbody>
        </table>
        
        <%--VOIR AJAX--%>
        
    </body>
    
</html>
