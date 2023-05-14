function productTotal(itemName){
    var cb = document.getElementById(itemName + "-cb");
    console.log(cb.value); 
    var productTotal = document.getElementById(itemName + "-total");
    var productTotalParam = document.getElementById(itemName + "-totalParam");
    var product = productTotal.value;
    var cbqty = document.getElementById(itemName + "-qty").value;
    var cbprice = document.getElementById(itemName + "-price").textContent;    
    if(cb.checked){
        product = (cbqty * cbprice).toFixed(2);
        productTotal.value = product;   
        productTotalParam.value = product;                   
    }else if(!cb.checked) {
        product = 0.00.toFixed(2);
        productTotal.value = product;
        productTotalParam.value = product;     
    }
    cb.value = newCbVal(cb.value,cbqty);
    console.log("New CbVal: " + cb.value)
    cartTotal();
}
function newCbVal(curVal,quantity){
    var details = curVal.split("-");
    return details[0] + "-" + details[1] + "-" + quantity;
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
    console.log("Items Total: " + totals);
    console.log("Selected Total: " + totalParam.value);
}
function qtyValidate(itemName){
    var inputBox = document.getElementById(itemName + "-qty");
    var value = parseInt(inputBox.value);
    var min = parseInt(inputBox.min);
    var max = parseInt(inputBox.max);
    if(value > max){
        inputBox.value = inputBox.max;
    }else if(value <= min){
        inputBox.value = inputBox.min;
    }
}
function showCaptcha(){
    var captcha = document.getElementById("captchaImage");
    captcha.src = 'SimpleCaptchaServlet';
    var blur = document.getElementById("blur");
    blur.classList.toggle('active');
    var popupCloser = document.getElementById("popupClose");
    popupCloser.classList.toggle('active');            
    var popup = document.getElementById("popup");
    popup.classList.toggle('active');
}
function submitCaptcha(){
    var captchaInput = document.getElementById("captchaInput");
    var captchaAnswer = document.getElementById("captchaAnswer");
    captchaAnswer.value = captchaInput.value;
    console.log(captchaAnswer.value);
    var form = document.getElementById("selected");
    form.action = "CaptchaChecker";
    if(captchaAnswer.value != ""){
        form.submit();
    }
    else{
        alert("Please input your answer to captcha.")
    }
}