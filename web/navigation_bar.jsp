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
        <header>
            <div class="container">
                <a href="index.jsp"><img src="resources/logo/logo.png" alt="logo" class="logo" /></a>
                <nav>
                    <ul>
                        <c:if test="${testingMode == true}">
                           <li><a href="LoginServlet?email=dummy1@gmail.com&password=testpassword">TestLogin</a></li> 
                        </c:if>                        
                        <li><a href="index.jsp">Home</a></li>
                        <li><a href="cart.jsp">Cart</a></li>    
                        <li>
                            <a href="">Categories</a>                         
                            <div class="sub-menu">
                                <ul class="dropdown">                                   
                                    <c:forEach var="cat" items="${categories}">
                                        <li><a href="index.jsp?sortBy=${cat}">${cat}</a></li>
                                    </c:forEach>
                                </ul>
                           </div>
                        </li>
                        <c:if test="${username == null}">
                            <li><a href="register.jsp">Register</a></li>
                            <li><a href="login.jsp">Login</a></li>
                        </c:if>
                        <c:if test="${username != null}">
                        <li><a>Hello, ${username}</a>
                            <div class="sub-menu">
                                <ul class="dropdown">                                   
                                    <li><a href="">Profile</a></li>
                                    <li><a href="">Settings</a></li>
                                    <li><a href="LogoutServlet">Log Out</a></li>                                    
                                </ul>
                           </div>
                        </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </header>
    </body>
</html>
