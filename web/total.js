function productTotal(cbName){
    var cb = document.getElementById(cbName + "-cb");
    var productTotal = document.getElementById(cbName + "-total");
    var productTotalParam = document.getElementById(cbName + "-totalParam");
    var product = productTotal.value;
    var cbqty = document.getElementById(cbName + "-qty").value;
    var cbprice = document.getElementById(cbName + "-price").textContent;    
    if(cb.checked){
        product = (cbqty * cbprice).toFixed(2);
        productTotal.value = product;   
        productTotalParam.value = product;           
    }else if(!cb.checked) {
        product = 0.00.toFixed(2);
        productTotal.value = product;
        productTotalParam.value = product;     
    }
    cartTotal();
}
function cartTotal(){
    var totalSpan = document.getElementById("selectedTotal");
    var totalParam = document.getElementById("selectedTotalPrice");
    var productTotals = document.getElementsByName("itemTotal");
    var selectedTotal = 0;
    var totals = [];
    for(var i=0; i<productTotals.length; i++){
        totals.push(parseFloat(productTotals[i].value).toFixed(2))
        selectedTotal += parseFloat(productTotals[i].value);
    }        
    totalParam.value = parseFloat(selectedTotal).toFixed(2);
    totalSpan.textContent = parseFloat(selectedTotal).toFixed(2);
    console.log(totals);
    console.log(totalParam.value);
}