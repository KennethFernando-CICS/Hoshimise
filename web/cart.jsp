<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="navigation_bar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/x-icon" href="resources/logo/logo.png"/>
        <link rel="stylesheet" href="css/cart-box.css" />
        <title>Cart</title>
    </head>
    <body>
        <div class="content-container">
            <div class="selected">
                <span class="delete-btn"></span>
                <input type="hidden" name="checkbox_hidden" value="0"><input type="checkbox" class="ch-box" onclick="this.previousSibling.value = 1 - this.previousSibling.value">
            </div>
            <div class="image">
                <img src="resources/images/aot_bowl.jpg" />
            </div>
            <div class="information">
                <div class="item-information">
                    <div class="item-name">
                        <h1>Item Name</h1>
                    </div>
                    <div class="other-info">
                        <h4>Size: <span>Size</span></h4>
                        <h4>Price: <span>$0.00</span></h4>
                        <h4>Number of Stocks left: <span>000</span></h4>
                        <label for="quantity">Quantity: <input type="number" value="0" min="1" name="quantity"></label>
                    </div>
                </div>                       
            </div>
        </div>
        <div class="buttons">
                <h2 class="total">Total price: <span class="total-price">$0.00</span></h2>
                <button form="purchase">
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
                <button form="purchase" onClick="remove();">                    
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
    </body>
</html>