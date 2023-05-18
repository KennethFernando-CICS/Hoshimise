<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="navigation_bar.jsp" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/nav-bar.css" />
        <link rel="stylesheet" href="css/item_information.css" />
        <title>${name}</title>
    </head>

    <body>
        <div class="content-container">
            <div class="image">
                <img src="resources/images/${img}" alt="Product Image"/>
            </div>
            <div class="information">
                <div class="item-information">
                    <form action="AddToCart" id="addToCart">                        
                        <div class="item-name">
                            <p>${name}</p>
                        </div>                       
                        <div class="other-info">
                            <h2>Price: $${price}</h2>
                            <h3> Stock: ${stock} </h3>                            
                            <div class="quantity-container">                          
                                <c:if test="${sizes != null}">
                                <label for="size">Sizes:
                                <select name="size" required>
                                    <option value="">Select a size</option> 
                                    <c:forEach var="size" items="${sizes}">
                                        <option value="${size}">${size}</option> 
                                    </c:forEach>
                                </select>
                                </c:if>
                                </label><br>
                                <label for="quantities">Quantity: <input name="quantity" type="number" min="1" max="${stock}" value="1" required>
                                </label>
                            </div>
                        </div>                        
                        <div class="button">
                            <button><span class="button_top">Add to Cart</span></button>
                        </div>
                        <input type="hidden" name="prodId" value="${prodId}"}>
                        <input type="hidden" name="stock" value="${stock}"}>
                    </form>
                </div>
                <div class="recommendations">
                    <h1>Other items:</h1>
                    <div class="recommendations-container" style="display: flex;gap: 1.5rem;padding: 0.5rem"> 
                        <jsp:include page="Recommend"></jsp:include>                        
                    </div>
                </div>
            </div>
    </body>
</html>