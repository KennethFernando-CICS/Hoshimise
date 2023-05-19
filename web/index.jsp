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
            <jsp:include page="/ProductLoad?count=10&${sortBy}"></jsp:include>
        </div>
    </body>
    <style>
        .item-container:hover{
            transform: scale(1.25,1.25);
        }
    </style>
</html>
