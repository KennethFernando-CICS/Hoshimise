<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/nav-bar.css" />
        <link rel="icon" type="image/x-icon" href="logo/icon.png"/>
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Progma", "no-cache");
            response.setHeader("Expires", "0");
        %>
        <header>
            <div class="container">
                <a href="index.jsp"><img src="resources/logo/logo.png" alt="logo" class="logo" /></a>
                <nav>
                    <ul>
                        <li><a href="index.jsp">Home</a></li>
                        <li><a href="cart.jsp">Cart</a></li>    
                        <li>
                            <c:if test="${sortType eq 'product'}">
                              <a href="">Categories</a> 
                            </c:if>
                            <c:if test="${sortType eq 'anime'}">
                              <a href="">Anime</a>  
                            </c:if>                          
                            <div class="sub-menu">
                                <ul class="dropdown">                                   
<!--                                    <li><a href="">Product A</a></li>
                                    <li><a href="">Product B</a></li>
                                    <li><a href="">Product C</a></li>-->
                                    
                                    <c:forEach var="cat" items="${categories}">
                                        <li><a href="index.jsp?sortBy=${cat}">${cat}</a></li>
                                    </c:forEach>
                                </ul>
                           </div>
                        </li>
                        <li><a href="register.jsp"">Register</a></li>
                        <li><a href="login.jsp"">Login</a></li>
                    </ul>
                </nav>
            </div>
        </header>
    </body>
</html>
