<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/nav-bar.css" />
        <link rel="stylesheet" href="css/user-forms.css" />
        <link rel="icon" type="image/x-icon" href="images/icon.png"/>
        <title>Login</title>
    </head>
    <body>
        <header>
            <div class="container">
                <!--setup proper link to homepage-->
                <a href="index.jsp"><img src="images/logo.png" alt="logo" class="logo" /></a>
            </div>
        </header>

        <div class="login-container">
            <h1>LOGIN</h1>
            <!-- Pakiayos nalang kung may mas maayos kang implementation -->
            <%try{
                boolean succ = request.getAttribute("succ").equals("true");
            } catch(NullPointerException npe){
                request.setAttribute("succ", false);
                request.getRequestDispatcher("login.jsp").forward(request, response); //forward request to addRecord.jsp
            }%>
            <c:if test="${succ}">
                <p class="prompt">Wrong username or password!</p>
            </c:if>
            <div class="form-container">
                <form action="loginServlet" method="POST">
                    <fieldset>
                        <label for="email">Email: </label><br>
                        <input placeholder="Email" class="input" name="email" type="text" required /><br>
                        <label for="password">Password:</label><br>
                        <input placeholder="Password" class="input" name="password" type="password" required /><br>
                        <button class="login"> Login </button>
                        <!--setup links for below-->
                        <h4>Forgot your <a href="">username</a> or <a href="">password</a>?</h4>
                        <h4>New to Hoshimise? <a href="register.jsp">Register</a>
                    </fieldset>
                </form>
            </div>

            <div class="others-container">
                <div class="or">
                    <div class="bar"></div>
                    <h3>or you can use other credentials</h3>
                    <div class="bar"></div>
                </div>
                <div class="other-buttons">
                        <button class="others"> Facebook </button>
                        <button class="others"> Gmail </button>
                        <button class="others"> Twitter </button>
                </div>
            </div>
        </div>
    </body>
</html>
