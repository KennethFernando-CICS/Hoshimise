<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="navigation_bar.jsp" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/nav-bar.css" />
        <link rel="stylesheet" href="css/item-page-style.css" />
        <title>Content</title>
    </head>

    <body>
        <div class="content-container">
            <div class="image">
                <img src="images/unknown.png" alt="Black T-shirt"/>
            </div>
            <div class="information">
                <div class="item-information">
                    <form action="addToCart" id="addToCart">
                        <div class="item-name">
                            <h1>${name}</h1>
                        </div>                       
                        <div class="other-info">
                            <h2>Price: <span>$${price}</span></h2>
                            <div class="quantity-container">
                                <label for="quantities">Quantity: <input name="quantity" type="number" min="1" value="1" max="stock" required>
                                    ${stock} available items</span></label>
                            </div>
                            <div class="selections">
                                <h4>Size Options:</h4>
                                
                                <input name="productId" type="hidden" value="${id}">                                
                                <select name="selectedSize" required>
                                    <option value="" disabled selected>Select a size:</option>
                                    <!--loop sizes-->
                                    <option value="${size}">Size</option>
                                    <!--till here-->
                                </select>                                
                            </div>
                        </div>                        
                        <div class="button">
                            <button><span class="button_top">Add to Cart</span></button>
                        </div>
                    </form>
                </div>
                <div class="recommendations">
                    <h1>Other items:</h1>
                    <div class="recommendations-container"> 
                        <!-- loop recommended -->
                        <div class="product">
                            <a href="content.jsp?productId=0">    
                                <img alt="product image" src="images/unknown.png%>">
                                <a href="content.jsp?productId=0" id="pName">name</a>
                                <p id="pPrice">$price%></p>
                                <p id="pStock">In Stock:stock%></p>
                            </a>
                        </div>
                        <!--until here-->
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>