
// js class defined
function StockData(stockCode, stockName, buyPrice, buyDate){
    this.stockCode = stockCode;
    this.stockName = stockName;
    this.buyPrice = buyPrice;
    this.buyDate = buyDate;
}

var g_NaN = "--";
var g_freshIntervalMillisecond = 5000;
var g_intervalBeforeShowStockList = 100;
var g_cookieSavedDaysNum = 90;
var g_shangHaiCode = "sh000001";
var g_isTradeTime = false;
var g_myStockDataInCookie = "";
var g_myStockList = {};
var g_shangHaiCurrentPrice = "";
var g_shangHaiYesterdayPrice = "";


$(document).ready(function(){
    readStockDataFromCookieAndParseToStockList();
    getStockInfoFromSina();
    setTimeout(function() {
        initStockDataTableList();
    }, g_intervalBeforeShowStockList);
});

function getStockInfoFromSina(){
    var sinaStockCodes = getSinaStockCodes();
    addSinaStockScriptElement(sinaStockCodes);
}

function getSinaStockCodes(){
    var sinaStockCodes = "";
    for (var code in g_myStockList){
        sinaStockCodes += code + ",";
    }
    sinaStockCodes = removeLastWord4String(sinaStockCodes);
    if (sinaStockCodes == ""){
        sinaStockCodes = g_shangHaiCode;
    }else{
        sinaStockCodes += "," + g_shangHaiCode;
    }
    return sinaStockCodes;
}

function addSinaStockScriptElement(sinaStockCodes){
    var customScriptElement = document.createElement("script");
    customScriptElement.type = "text/javascript";
    customScriptElement.src = "http://hq.sinajs.cn/list=" + sinaStockCodes;
    customScriptElement.charset = "gb2312";
    var headElement = document.getElementsByTagName('head')[0];
    headElement.appendChild(customScriptElement);
}

function removeSinaStockScriptElement(){
    $("script[charset]").remove();
}

function initStockDataTableList(){
    getLatestStockDataFromSinaAndFillTableList();
    updateStockDataBetweenTradeTime();
}

function updateStockDataTableList(){
    removeSinaStockScriptElement();
    getLatestStockDataFromSinaAndFillTableList();
}

function getLatestStockDataFromSinaAndFillTableList(){
    getStockInfoFromSina();
    updateTableTitleAndDocumentTitle();
    parseSinaStockDataAndInsertIntoTableList();
}

function checkTradeTimeStatus(){
    var now = new Date();
    var currentHourNum = now.getHours();
    var currentMinNum = now.getMinutes();
    g_isTradeTime = (currentHourNum > 9 && currentHourNum < 15) ||
    ((currentHourNum == 9 && currentMinNum >= 30) || (currentHourNum == 15 && currentMinNum == 0));
}

function readStockDataFromCookieAndParseToStockList(){
    try {
        readStockDataFromCookie();
        parseStockDataIntoList();
    }catch(e){}
}

function updateStockDataBetweenTradeTime(){
    if (g_isTradeTime) {
        setInterval(
            function () {
                updateStockDataTableList();
            },
            g_freshIntervalMillisecond
        );
    }
}

function parseSinaStockDataAndInsertIntoTableList() {
    resetMyStockDataTableList();
    for(var stockItemCode in g_myStockList){
        addStockInfoIntoTableList("hq_str_" + stockItemCode, stockItemCode);
    }
}

function resetMyStockDataTableList(){
    if ($("#iTbl_stockInfoList tr").length > 1){
        $("#iTbl_stockInfoList tr:gt(0)").remove();
    }
}

function reloadPage(){
    window.location.reload();
}

function addStockInfoIntoTableList(stockDataVarName, stockCode){
    if (stockDataVarName in window) {
        var elements = window[stockDataVarName].split(",");
        var name = elements[0];
        var todayOpenPrice = elements[1];
        var yesterdayClosePrice = elements[2];
        var currentPrice = elements[3];
        var todayMaxPrice = elements[4];
        var todayMinPrice = elements[5];
        var todayPercent = getTodayPercent(yesterdayClosePrice, currentPrice);
        var todayOpenStatus = getTodayOpenStatus(todayOpenPrice, yesterdayClosePrice);
        var holdStockDays = getHoldStockDaysNum(stockCode);
        var totalPercent = getTotalPercent(stockCode, currentPrice);
        var tdClassName4TodayOpenStatus = getTdClassName4TodayOpenStatus(todayOpenPrice, yesterdayClosePrice);
        var tdClassName4TodayIncrease = getTdClassName4TodayIncrease(currentPrice, yesterdayClosePrice);
        var tdClassName4TotalIncrease = getTdClassName4TotalIncrease(stockCode, currentPrice);

        var stockInfoTdHtmlString = generateStockInfoTrHtmlString(
            stockCode,
            name,
            currentPrice,
            todayPercent,
            todayOpenStatus,
            todayOpenPrice,
            yesterdayClosePrice,
            todayMinPrice,
            todayMaxPrice,
            holdStockDays,
            totalPercent,
            tdClassName4TodayIncrease,
            tdClassName4TodayOpenStatus,
            tdClassName4TotalIncrease
        );
        $("#iTbl_stockInfoList tbody").append($(stockInfoTdHtmlString));
    }
}

function updateTableTitleAndDocumentTitle(){
    checkTradeTimeStatus();
    getShangHaiCurrentPrice();

    var tableTitleText4Time = getCurrentTime() + " - [ " + (g_isTradeTime ? "交易时间" : "闭市时间") + " ]";
    $("#iSpan_currentTime").text(tableTitleText4Time);
    var tableTitleText4ShangHaiPrice = g_shangHaiCurrentPrice + " - [ "
                            + getTodayPercent(g_shangHaiYesterdayPrice, g_shangHaiCurrentPrice) + " ]";
    var shangHaiPriceStatus = getTdClassName4TodayIncrease(g_shangHaiCurrentPrice, g_shangHaiYesterdayPrice);
    $("#iSpan_shangHaiZhiShu").attr("class", shangHaiPriceStatus).text(tableTitleText4ShangHaiPrice);

    showPageTitle();
}

function showPageTitle(){
    document.title = "上证指数：" + g_shangHaiCurrentPrice;
}

function getShangHaiCurrentPrice(){
    var shangHaiVarName = "hq_str_" + g_shangHaiCode;
    if (shangHaiVarName in window) {
        var shangHaiData = window[shangHaiVarName];
        if (shangHaiData != null && shangHaiData != undefined){
            var arr = shangHaiData.split(",");
            if (arr != "" && arr.length > 1) {
                g_shangHaiCurrentPrice = arr[3];
                g_shangHaiYesterdayPrice = arr[2];
            }
        }
    }
}

function getCurrentTime() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var day = now.getDate();
    var hh = now.getHours();
    var mm = now.getMinutes();

    var clock = year + "-";
    if(month < 10) {
        clock += "0";
    }
    clock += month + "-";
    if(day < 10) {
        clock += "0";
    }
    clock += day + " ";
    if(hh < 10) {
        clock += "0";
    }
    clock += hh + ":";
    if (mm < 10) {
        clock += '0';
    }
    clock += mm;

    return(clock);
}

function getTodayPercent(yesterdayClosePrice, currentPrice){
    var currentPriceNum = Number(currentPrice);
    var yesterdayClosePriceNum = Number(yesterdayClosePrice);
    if (currentPriceNum <= 0 || yesterdayClosePriceNum <= 0){
        return 0;
    }
    return ((currentPriceNum - yesterdayClosePriceNum) * 100 / yesterdayClosePriceNum).toFixed(2) + "%";
}

function getTodayOpenStatus(todayOpenPrice, yesterdayClosePrice){
    var todayOpenPriceNum = Number(todayOpenPrice);
    var yesterdayClosePriceNum = Number(yesterdayClosePrice);

    var status = "平开";
    if (todayOpenPriceNum > yesterdayClosePriceNum){
        status = "高开";
    } else if (todayOpenPriceNum < yesterdayClosePriceNum){
        status = "低开";
    }
    return status;
}

function getTotalPercent(stockCode, currentPrice){
    var totalPercent = "0.0";
    if (stockCode in g_myStockList) {
        var buyPriceVal = g_myStockList[stockCode].buyPrice;
        if (buyPriceVal != "") {
            var buyPrice = Number(buyPriceVal);
            totalPercent = ((Number(currentPrice) - buyPrice) * 100 / buyPrice).toFixed(2) + "%";
        } else {
            totalPercent = g_NaN;
        }
    }
    return totalPercent;
}

function getHoldStockDaysNum(stockCode){
    var daysNum = "0";
    if (stockCode in g_myStockList) {
        var buyDateVal = g_myStockList[stockCode].buyDate;
        if (buyDateVal != "") {
            var todayDate = new Date();
            var buyDate = new Date(g_myStockList[stockCode].buyDate);
            daysNum = parseInt((todayDate - buyDate) / (1000 * 60 * 60 * 24));
        } else {
            daysNum = g_NaN;
        }
    }
    return daysNum;
}

function getTdClassName4TodayOpenStatus(todayOpenPrice, yesterdayClosePrice){
    return getTdClassNameByPrice(todayOpenPrice, yesterdayClosePrice);
}

function getTdClassName4TodayIncrease(currentPrice, yesterdayClosePrice){
    return getTdClassNameByPrice(currentPrice, yesterdayClosePrice);
}

function getTdClassName4TotalIncrease(stockCode, currentPrice){
    var tdClassName = "";
    if (stockCode in g_myStockList){
        var buyPriceVal = g_myStockList[stockCode].buyPrice;
    }
    if (buyPriceVal != ""){
        tdClassName = getTdClassNameByPrice(currentPrice, buyPriceVal);
    }
    return tdClassName;
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

function generateStockInfoTrHtmlString(stockCode,
                                       name,
                                       currentPrice,
                                       todayPercent,
                                       todayOpenStatus,
                                       todayOpenPrice,
                                       yesterdayClosePrice,
                                       todayMinPrice,
                                       todayMaxPrice,
                                       holdStockDays,
                                       totalPercent,
                                       tdClassName4TodayIncrease,
                                       tdClassName4TodayOpenStatus,
                                       tdClassName4TotalIncrease) {

    if (Number(currentPrice) == 0){
        currentPrice = "停牌";
        todayPercent = g_NaN;
        todayOpenStatus = g_NaN;
        todayOpenPrice = g_NaN;
        todayMinPrice = g_NaN;
        todayMaxPrice = g_NaN;
        totalPercent = getTotalPercent(stockCode, yesterdayClosePrice);
        tdClassName4TodayIncrease = "cTd_balanceStatus";
        tdClassName4TodayOpenStatus = "cTd_balanceStatus";
        tdClassName4TotalIncrease = getTdClassName4TotalIncrease(stockCode, yesterdayClosePrice);
    }
    var procPanelHtmlString = "<a href='#' onclick='removeStockItemFromCookie(\"" + stockCode + "\")'>取消</a>";

    return "<tr class='cTr_data'>"
        + "<td>" + stockCode + " | " + name + "</td>"
        + "<td class='" + tdClassName4TodayIncrease + "'>" + currentPrice + "</td>"
        + "<td class='" + tdClassName4TodayIncrease + "'>" + todayPercent + "</td>"
        + "<td class='" + tdClassName4TodayOpenStatus + "'>" + todayOpenStatus + "</td>"
        + "<td class='" + tdClassName4TodayOpenStatus + "'>" + todayOpenPrice + "</td>"
        + "<td>" + yesterdayClosePrice + "</td>"
        + "<td class='cTd_downStatus'>" + todayMinPrice + "</td>"
        + "<td class='cTd_upStatus'>" + todayMaxPrice + "</td>"
        + "<td>" + holdStockDays + "</td>"
        + "<td class='" + tdClassName4TotalIncrease + "'>" + totalPercent + "</td>"
        + "<td>" + procPanelHtmlString + "</td>"
        + "</tr>";
}

function checkAndSaveStockData(){
    var stockCode = $("#iIpt_stockCode").val();
    var buyPrice = $("#iIpt_buyPrice").val();
    var buyDate = $("#iIpt_buyDate").val();

    if (checkUserInputStockData(stockCode, buyPrice, buyDate)){
        updateGlobalStockData(stockCode, buyPrice, buyDate);
        addStockDataToCookie();
        reloadPage();
    } else {
        alert("信息输入有误，请重新输入。");
    }
    resetAllInputControls();
}

function checkUserInputStockData(stockCode, buyPrice, buyDate){
    var passStatus = true;
    if(stockCode != "" && buyPrice != "" && buyDate != ""){

    }
    return passStatus;
}

function updateGlobalStockData(stockCode, buyPrice, buyDate){
    var newStockItemData = stockCode + "," + buyPrice + "," + buyDate;
    if (g_myStockDataInCookie == "" || g_myStockDataInCookie == undefined){
        g_myStockDataInCookie = newStockItemData;
    } else {
        g_myStockDataInCookie += ";" + newStockItemData;
    }
}

function addStockDataToCookie(){
    $.cookie("user_stock_data", g_myStockDataInCookie, { expires: g_cookieSavedDaysNum });
}

function resetAllInputControls(){
    $("#iIpt_stockCode").val("");
    $("#iIpt_buyPrice").val("");
    $("#iIpt_buyDate").val("");
}

function readStockDataFromCookie(){
    var stockDataInCookie =  $.cookie("user_stock_data");
    g_myStockDataInCookie = (stockDataInCookie == undefined) ? "" : stockDataInCookie;
}

function removeStockItemFromCookie(stockCode){
    if (stockCode in g_myStockList){
        delete g_myStockList[stockCode];
    }
    g_myStockDataInCookie = generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}

function parseStockDataIntoList(){
    if (g_myStockDataInCookie != undefined && g_myStockDataInCookie != ""){
        var stockArray = g_myStockDataInCookie.split(";");
        for(var i = 0; i < stockArray.length; i++){
            if (stockArray[i] != "") {
                var stockItems = stockArray[i].split(",");
                g_myStockList[stockItems[0]] = new StockData(stockItems[0], "", stockItems[1], stockItems[2]);
            }
        }
    }
}

function generateStockDataByList(){
    var allStockDataValue = "";
    for(var stockItemCode in g_myStockList){
        if (stockItemCode != undefined) {
            var stockItemObj = g_myStockList[stockItemCode];
            if (stockItemObj != null) {
                allStockDataValue += stockItemObj.stockCode + "," + stockItemObj.buyPrice + "," + stockItemObj.buyDate + ";";
            }
        }
    }
    if (allStockDataValue != ""){
        allStockDataValue = allStockDataValue.substring(0, allStockDataValue.length - 1);
    }
    return allStockDataValue;
}

function removeLastWord4String(str){
    var finalStr = "";
    if (str != ""){
        finalStr = str.substring(0, str.length - 1);
    }
    return finalStr;
}


/* function for test */
function addTestData(){
    g_myStockList["sz000977"] = new StockData("sz000977", "浪潮信息", "38.888", "2014-11-11");
    g_myStockList["sh603288"] = new StockData("sh603288", "海天味业", "42.341", "2014-12-03");
    g_myStockList["sh600509"] = new StockData("sh600509", "天富能源", "10.581", "2014-12-16");
    g_myStockList["sz000099"] = new StockData("sz000099", "中信海直", "14.62", "2014-12-22");
    g_myStockList["sh601117"] = new StockData("sh601117", "中国化学", "9.439", "2015-01-06");
    g_myStockList["sz000837"] = new StockData("sz000837", "秦川机床", "11.115", "2015-01-12");
    g_myStockList["sh600597"] = new StockData("sh600597", "光明乳业", "18.311", "2015-01-14");
    g_myStockDataInCookie = generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}