<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/nav-bar.css" />
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Progma", "no-cache");
            response.setHeader("Expires", "0");
        %>
        <header>
            <div class="container">
                <a href="/USTORE"><img src="images/logo.png" alt="logo" class="logo" /></a>
                <nav>
                    <ul>
                        <li><a href="/USTORE">Home</a></li>
                        <li><a href="cart.jsp">Cart</a></li>    
                        <li><a href="">Categories</a>
                            <div class="sub-menu">
                                <ul class="dropdown">
                                    <li><a href="">Product A</a></li>
                                    <li><a href="">Product B</a></li>
                                    <li><a href="">Product C</a></li>
                                </ul>
                           </div>
                        </li>
                        <li><a href=""">Register</a></li>
                        <li><a href=""">Login</a></li>
                    </ul>
                </nav>
            </div>
        </header>
    </body>
</html>
