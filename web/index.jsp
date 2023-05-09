<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="navigation_bar.jsp" %>
<!DOCTYPE html>
<html lang="en">
    
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/nav-bar.css" />
        <link rel="stylesheet" href="css/item-box.css" />
        <title>Index</title>
    </head>
    <body>
        <div class="item-grid">
<!--        <div class="item-container">
            <div class="temp-image">
                
            </div>
            <h1 class="item-name">Sample Item Name</h1>
            <p class="price">$1.00</p>
        </div>-->
        <jsp:include page="/ProductLoad?count=10"></jsp:include>
        </div>
    </body>
</html>