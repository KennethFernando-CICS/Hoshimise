<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/nav-bar.css" />
        <link rel="stylesheet" href="css/user-forms.css" />
        <link rel="icon" type="image/x-icon" href="logo/icon.png"/>
        <title>Register Page</title>
    </head>
    <body>
        <header>
            <div class="container">
                <!--setup the proper link to redirect to homepage-->
                <a href="index.jsp"><img src="resources/logo/logo.png" alt="logo" class="logo" /></a>
            </div>
        </header>

        <div class="login-container"> 
            <h1>REGISTER</h1>
            <div class="form-container">
                <!-- setup links for privacy agreement and user agreement -->
                <p>By filling out this form you agree to the <a href="">Privacy Agreement</a> and <a href="">User Agreement</a></p>           
                <form action="Register" method="POST">
                    <fieldset>
                        <label for="username">Username: <input placeholder="Username" class="input" name="username" type="text" required /></label>
                        <label for="email">Email Address: <input placeholder="Email" class="input" name="email" type="text" required /></label>
                        <label for="password">Password: <input placeholder="Password" class="input" name="password" type="password" required /></label>
                        <input type="checkbox" />By clicking this button, you agree to have your information stored in our database
                        <br>
                        <button class="register"> Register </button>
                        <h4>Already have an account? <a href="login.jsp">Click Here!</a></h4>
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
