<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="navigation_bar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/x-icon" href="resources/logo/logo.png"/>
        <link rel="stylesheet" href="css/cart-box.css" />
        <link rel="stylesheet" href="css/popup.css">
        <title>Cart</title>
    </head>
    <%
        if (session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Progma", "no-cache");
        response.setHeader("Expires", "0");
    %>
    <body ><div id="blur">       
        <form id="selected" action="" method="post">
        <div class="cart-grid">           
                <jsp:include page="/CartLoader"></jsp:include>           
        </div>
        <input type="hidden" id="selectedTotalPrice" name="selectedTotalPrice" value="0.00">
        <input type="hidden" placeholder="capAns" id="captchaAnswer" name="captchaAnswer" value="">
        </form>
        <div class="buttons">
            <h2 class="total">Selected Total price: <span class="total-price">$</span><span class="total-price" id="selectedTotal">0.00</span></h2>
            <button onClick="showCaptcha()">
                <div class="svg-wrapper-1">
                    <div class="svg-wrapper">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
                        <path fill="none" d="M0 0h24v24H0z"></path>
                        <path fill="currentColor" d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"></path>
                        </svg>
                    </div>
                </div>
                <span>Purchase</span>
            </button>
            <button form="selected" formaction="CartTakeOut?dest=cart.jsp">                    
                <div class="svg-wrapper-1">
                    <div class="svg-wrapper">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
                        <path fill="none" d="M0 0h24v24H0z"></path>
                        <path fill="currentColor" d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"></path>
                        </svg>
                    </div>
                </div>
                <span>Remove</span>
            </button>            
        </div>       
    </div>
    <div>
        <div id="popupClose" onClick="showCaptcha()"></div>
        <div id="popup">
            <h2>BEFORE PURCHASING, PLEASE ANSWER THIS CAPTCHA:</h2>
            <!--CAPTCHA-->
            <div class="capdiv" style="display:flex; flex-direction: column;justify-content: center;">
                <img id="captchaImage" src="SimpleCaptchaServlet" alt="Captcha: NULL"/>
                <br>
                <input type="text" name="captchaInput" id="captchaInput" placeholder="Captcha Answer" required>
                <br>
                <button onClick="submitCaptcha()">
                    <div class="svg-wrapper-1">
                        <div class="svg-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
                            <path fill="none" d="M0 0h24v24H0z"></path>
                            <path fill="currentColor" d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"></path>
                            </svg>
                        </div>
                    </div>
                    <span>Submit</span>
                </button>
            </div> 
        </div>  
    </div>
    </body>
     
    <script src="js/cartFunctions.js"></script>
</html>