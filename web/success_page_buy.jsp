<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/success-page.css" />
        <link rel="stylesheet" href="css/nav-bar.css" />
        <title>Successful Purchase</title>
    </head>
    <body>
        <header>
            <div class="container">
                <a href="/Hoshimise"><img src="resources/logo/logo.png" alt="logo" class="logo" /></a>
            </div>
        </header>
        <div class="content-container">            
            <h1>The purchase is successful!</h1>
            <h3>The following items were purchased:</h3>
            <table style="table-layout: fixed;">
                <tr>
                    <th>PURCHASED ITEM</th>
                    <th>QUANTITY</th>
                    <th>PRICE/$</th>
                </tr>
                <c:forEach var="item" items="${sessionScope.boughtMap}"> 
                    <tr>
                        <th style="text-align: left;" width="50%">${item.key}</th>
                        <c:forEach var="itemVal" items="${item.value}"> 
                            <th width="25%">${itemVal.key}</th>
                            <th width="25%">${itemVal.value}</th>
                        </c:forEach>
                    </tr>               
                </c:forEach>
            </table>               
            <h3>The total amount is: $<span class="total">${sessionScope.selectedTotalPrice}</span></h3>
            <h2>Thank you for purchasing our merchandise, we hope to see you again!</h2>
            <h2>To view your receipt, please <a href=""> click here. </a></h2> <!-- to show the receipt to the user -->
            <h2>To return to the homepage, please <a href="index.jsp">click here.</a></h2> <!--setup the link back to homepage-->
        </div>
    </body>
</html>