<div class="content-container">
    <div class="selected">
        <input type="hidden" id="${param.itemId}-total" name="itemTotal" value="0.00">
        <input type="hidden" id="${param.itemId}-totalParam" name="totalParam" value="0.00">
        <span class="delete-btn"></span>
        <input type="checkbox" class="ch-box" name="selected" value="${param.itemId}" id="${param.itemId}-cb" onClick="productTotal('${param.itemId}')"/>
    </div>
    <div class="image">
        <img src="resources/images/${param.imgName}" />
    </div>
    <div class="information">
        <div class="item-information">
            <div class="item-name">
                <h1>${param.itemName}</h1>
            </div>
            <div class="other-info">
                <h4>Size: <span>${param.size}</span></h4>
                <h4>Price: <span>$</span><span id="${param.itemId}-price">${param.price}</span></h4>
                <h4>Number of Stocks left: <span>${param.stock}</span></h4>
                <label for="quantity">Quantity: 
                    <input type="number" required onKeyUp="qtyValidate('${param.itemId}')" value="${param.quantity}" min="1" max="${param.stock}" name="quantity" id="${param.itemId}-qty" onClick="productTotal('${param.itemId}')">
                </label>
            </div>
        </div>                       
    </div>
</div>