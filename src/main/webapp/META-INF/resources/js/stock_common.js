
var g_NaN = "--";
var g_intervalBeforeShowStockList = 300;
var g_sinaStockVarPrefix = "hq_str_";


function addSinaStockScriptElement(sinaStockCodes){
    var customScriptElement = document.createElement("script");
    customScriptElement.type = "text/javascript";
    customScriptElement.src = "http://hq.sinajs.cn/list=" + sinaStockCodes;
    customScriptElement.charset = "gb2312";
    var headElement = document.getElementsByTagName('head')[0];
    headElement.appendChild(customScriptElement);
}

function checkSinaStockCodeAvailable(stockDataVarName){
    return ((stockDataVarName in window) && (window[stockDataVarName] != ""));
}

function getTodayPercent(yesterdayClosePrice, currentPrice){
    var currentPriceNum = currentPrice.toNumber();
    var yesterdayClosePriceNum = yesterdayClosePrice.toNumber();
    if (currentPriceNum <= 0 || yesterdayClosePriceNum <= 0){
        return 0;
    }
    return ((currentPriceNum - yesterdayClosePriceNum) * 100 / yesterdayClosePriceNum).toFixed(2) + "%";
}

function getTdClassName4TodayIncrease(currentPrice, yesterdayClosePrice){
    return getTdClassNameByPrice(currentPrice, yesterdayClosePrice);
}

function getTdClassNameByPrice(newPrice, oldPrice){
    var tdClassName = "cTd_balanceStatus";
    var newPriceNum = Number(newPrice);
    var oldPriceNum = Number(oldPrice);
    if (newPriceNum > oldPriceNum){
        tdClassName = "cTd_upStatus";
    } else if (newPriceNum < oldPriceNum){
        tdClassName = "cTd_downStatus";
    }
    return tdClassName;
}