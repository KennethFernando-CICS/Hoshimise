<div class="content-container">
    <div class="selected">
        <input type="hidden" id="${param.cbValue}-total" name="itemTotal" value="0.00">
        <input type="hidden" id="${param.cbValue}-totalParam" name="${param.cbValue}totalParam" value="">
        <span class="delete-btn"></span>
        <input type="checkbox" class="ch-box" name="selected" value="${param.cbValue}" id="${param.cbValue}-cb" onClick="productTotal('${param.cbValue}')"/>
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
                <h4>Price: <span>$</span><span id="${param.cbValue}-price">${param.price}</span></h4>
                <h4>Number of Stocks left: <span>${param.stock}</span></h4>
                <label for="quantity">Quantity: 
                    <input type="number" value="${param.quantity}" min="1" name="quantity" id="${param.cbValue}-qty" onClick="productTotal('${param.cbValue}')">
                </label>
            </div>
        </div>                       
    </div>
</div>