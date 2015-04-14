
// js class defined
function StockData(stockCode, stockName, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType){
    this.stockCode = stockCode;
    this.stockName = stockName;
    this.buyPrice = buyPrice;
    this.holdNum = holdNum;
    this.buyDate = buyDate;
    this.gainPrice = gainPrice;
    this.losePrice = losePrice;
    this.holdType = holdType;
    this.gainStatus = false;
    this.loseStatus = false;
}
function FollowedStockData(stockCode, stockName, targetPrice, followedDate){
    this.stockCode = stockCode;
    this.stockName = stockName;
    this.targetPrice = targetPrice;
    this.followedDate = followedDate;
}


var g_freshIntervalMillisecond = 5000;
var g_cookieSavedDaysNum = 90;
var g_shangHaiCode = "sh000001";
var g_userCookieName = "user_stock_data";
var g_stockDetailColumnsIndex = "4,5,6,7,8,9,11";
var g_upBreakThoughPercent = 4;
var g_downBreakThoughPercent = 4;
var g_showStockDetailColumnsStatus = false;
var g_showLongPeriodStockRowsStatus = false;
var g_isTradeTime = false;
var g_myAllStockDataInCookie = "";
var g_myStockDataInCookie = "";
var g_myFollowedStockDataInCookie = "";
var g_myStockList = {};
var g_myFollowedStockList = {};
var g_shangHaiCurrentPrice = "";
var g_shangHaiYesterdayPrice = "";
var g_currentLoginUserName = "";
var g_userHoldStocksTotalValue = 0;
var g_laoNiuGroupId = 2;

var g_isTestMode = false;
var g_publishVersion = "1.5.0";


$(document).ready(function(){
    initPageDefaultJsEvents();
    initPageDefaultCss();
    readStockDataFromCookieAndParseToStockList();
    getStockInfoFromSina();
    setTimeout(function() {
        updateQuickStockCookieData();
        initStockDataTableList();
    }, g_intervalBeforeShowStockList);
});

function initPageDefaultJsEvents(){
    $(".cSpan_button").mouseover(function(){
        $(this).addClass("cSpan_mouseOverButton");
    }).mouseout(function(){
        $(this).removeClass("cSpan_mouseOverButton");
    });
}

function initPageDefaultCss(){
    if ($("#hidden_stock_cookie").val() == ""){
        $("#iA_recoverStockList_b").hide();
    }
}

function getStockInfoFromSina(){
    var sinaStockCodes = getSinaStockCodes();
    addSinaStockScriptElement(sinaStockCodes);
}

function getSinaStockCodes(){
    var sinaStockCodes = "";
    for (var code in g_myStockList){
        sinaStockCodes += code + ",";
    }
    for (var code in g_myFollowedStockList){
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
    parseSinaStockDataAndInsertIntoTableList();
    updateTableTitleAndDocumentTitle();
    controlStockRowsByHoldTypeStatus();
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
    if (g_isTradeTime || g_isTestMode) {
        setInterval(
            function () {
                updateStockDataTableList();
                controlStockDetailColumnsByStatus();
                controlStockRowsByHoldTypeStatus();
            },
            g_freshIntervalMillisecond
        );
    }
}

function parseSinaStockDataAndInsertIntoTableList() {
    g_userHoldStocksTotalValue = 0;
    clearMyStockDataTableList();
    clearMyFollowedStockDataTableList();
    for (var stockItemCode in g_myStockList) {
        addStockInfoIntoTableList(stockItemCode);
    }
    for (var stockItemCode in g_myFollowedStockList) {
        addFollowedStockInfoIntoTableList(stockItemCode);
    }
    if ($("#iTbl_stockInfoList tr").length == 1){
        addBlankInfoIntoTableList("iTbl_stockInfoList");
    }
    if ($("#iTbl_followedStockInfoList tr").length == 1){
        addBlankInfoIntoTableList("iTbl_followedStockInfoList");
    }
    showTabMenuNumber();
}

function clearMyStockDataTableList(){
    if ($("#iTbl_stockInfoList tr").length > 1){
        $("#iTbl_stockInfoList tr:gt(0)").remove();
    }
}

function clearMyFollowedStockDataTableList(){
    if ($("#iTbl_followedStockInfoList tr").length > 1){
        $("#iTbl_followedStockInfoList tr:gt(0)").remove();
    }
}

function reloadPage(){
    window.location.reload();
}

function updateQuickStockCookieData(){
    if (checkUserLoginStatus()) {
        $.post("stock/update_quick_cookie.do", {
            "userName": g_currentLoginUserName,
            "quickCookie": g_myAllStockDataInCookie
        });
    }
}

function addStockInfoIntoTableList(stockCode){
    var stockDataVarName = g_sinaStockVarPrefix + stockCode;
    if (checkSinaStockCodeAvailable(stockDataVarName)) {
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
        var trClassName4GainAndLoseStatus = getTrClassName4GainAndLoseStatus(stockCode, currentPrice);
        var tdClassName4TodayBreakThoughStatus = getTdClassName4TodayBreakThoughStatus(yesterdayClosePrice, todayMinPrice, todayMaxPrice);
        var isStopTrade = getStopTradeStatus(elements);
        var totalPercentByMonth = getTotalPercentByMonth(totalPercent, holdStockDays);

        g_myStockList[stockCode].stockName = name;
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
            tdClassName4TotalIncrease,
            trClassName4GainAndLoseStatus,
            isStopTrade,
            tdClassName4TodayBreakThoughStatus,
            totalPercentByMonth
        );
        $("#iTbl_stockInfoList tbody").append($(stockInfoTdHtmlString));
        g_userHoldStocksTotalValue += currentPrice.toNumber() * g_myStockList[stockCode].holdNum.toNumber();
    } else if (checkStockCodeAvailable(stockCode)){
        delete g_myStockList[stockCode];
    }
}

function addFollowedStockInfoIntoTableList(stockCode){
    var stockDataVarName = g_sinaStockVarPrefix + stockCode;
    if (checkSinaStockCodeAvailable(stockDataVarName)) {
        var elements = window[stockDataVarName].split(",");
        var name = elements[0];
        var todayOpenPrice = elements[1];
        var yesterdayClosePrice = elements[2];
        var currentPrice = elements[3];
        var todayMaxPrice = elements[4];
        var todayMinPrice = elements[5];
        var todayPercent = getTodayPercent(yesterdayClosePrice, currentPrice);
        var targetPrice = g_myFollowedStockList[stockCode].targetPrice;
        var holdStockDays = getFollowedStockDaysNum(stockCode);
        var gapPercent = getGapPercent(currentPrice, targetPrice);
        var gapPrice = getGapPrice(currentPrice, targetPrice);
        var tdClassName4TodayIncrease = getTdClassName4TodayIncrease(currentPrice, yesterdayClosePrice);
        var tdClassName4TargetStatus = getTdClassName4TargetStatus(currentPrice, targetPrice);
        var tdClassName4TodayBreakThoughStatus = getTdClassName4TodayBreakThoughStatus(yesterdayClosePrice, todayMinPrice, todayMaxPrice);
        var isStopTrade = getStopTradeStatus(elements);

        g_myFollowedStockList[stockCode].stockName = name;
        var followedStockInfoTdHtmlString = generateFollowedStockInfoTrHtmlString(
            stockCode,
            name,
            currentPrice,
            todayPercent,
            targetPrice,
            todayMinPrice,
            todayMaxPrice,
            gapPercent,
            holdStockDays,
            tdClassName4TodayIncrease,
            tdClassName4TargetStatus,
            isStopTrade,
            tdClassName4TodayBreakThoughStatus
        );
        $("#iTbl_followedStockInfoList tbody").append($(followedStockInfoTdHtmlString));
    } else if (checkStockCodeAvailable(stockCode)){
        delete g_myFollowedStockList[stockCode];
    }
}

function getStopTradeStatus(stockItems){
    return (stockItems[4].toNumber() == 0 && stockItems[5].toNumber() == 0);
}

function getTotalPercentByMonth(totalPercent, holdStockDays){
    var totalPercentNum = totalPercent.substring(0, totalPercent.length - 1).toNumber();
    return (totalPercentNum * 30 / holdStockDays).toFixed(2) + "%";
}

function addBlankInfoIntoTableList(tableId){
    var blankInfoTdHtmlString = "<tr><td colspan='11' style='text-align: center; color:gray;'><span>暂无内容</span></td></tr>";
    $("#" + tableId + " tbody").append($(blankInfoTdHtmlString));
}

function updateTableTitleAndDocumentTitle(){
    checkTradeTimeStatus();
    getShangHaiCurrentPrice();

    var tableTitleText4Time = getCurrentTime();
    $("#iSpan_currentTime").text(tableTitleText4Time);
    var tableTitleText4ShangHaiPrice = g_shangHaiCurrentPrice + " - [ "
                            + getTodayPercent(g_shangHaiYesterdayPrice, g_shangHaiCurrentPrice) + " ]";
    var shangHaiPriceStatus = getTdClassName4TodayIncrease(g_shangHaiCurrentPrice, g_shangHaiYesterdayPrice);
    $("#iSpan_shangHaiZhiShu").attr("class", shangHaiPriceStatus).text(tableTitleText4ShangHaiPrice);
    $("#iSpan_shangHaiZhiShuTitle").attr("class", shangHaiPriceStatus);
    showPageTitle();
    showUserHoldStocksTotalValue();
}

function showUserHoldStocksTotalValue(){
    var totalValueText = g_userHoldStocksTotalValue.toFixed(0) + " 元"
    $("#iSpan_userHoldStocksTotalValue").text(totalValueText);
}

function showPageTitle(){
    document.title = "上证：" + g_shangHaiCurrentPrice;
}

function showTabMenuNumber(){
    var shortPeriodStockNum = 0;
    var longPeriodStockNum = 0;
    for (var item in g_myStockList){
        if (g_myStockList[item].holdType != undefined) {
            if (g_myStockList[item].holdType == "long") {
                longPeriodStockNum++;
            } else {
                shortPeriodStockNum++;
            }
        }
    }
    $("#iA_currentHoldStockTabMenu").text("目前持仓 ( " + shortPeriodStockNum + " | " + longPeriodStockNum + " )");
    $("#iA_trailAdjustHoldStockTabMenu").text("回调追踪 ( " + arrayCount(g_myFollowedStockList) + " )");
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
            var buyDate = new Date(buyDateVal);
            daysNum = parseInt((todayDate - buyDate) / (1000 * 60 * 60 * 24)) + 1;
        }
    }
    return daysNum;
}

function getFollowedStockDaysNum(stockCode){
    var daysNum = "0";
    if (stockCode in g_myFollowedStockList) {
        var followedDateVal = g_myFollowedStockList[stockCode].followedDate;
        if (followedDateVal != "") {
            var todayDate = new Date();
            var followedDate = new Date(followedDateVal);
            daysNum = parseInt((todayDate - followedDate) / (1000 * 60 * 60 * 24)) + 1;
        }
    }
    return daysNum;
}

function getTdClassName4TodayOpenStatus(todayOpenPrice, yesterdayClosePrice){
    return getTdClassNameByPrice(todayOpenPrice, yesterdayClosePrice);
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

function getTrClassName4GainAndLoseStatus(stockCode, currentPrice){
    var trClassName = "";
    var currentPriceNum = Number(currentPrice);
    var gainPriceNum = Number(g_myStockList[stockCode].gainPrice);
    var losePriceNum = Number(g_myStockList[stockCode].losePrice);
    var gainStatus = (currentPriceNum >= gainPriceNum) && gainPriceNum != 0 && currentPriceNum != 0;
    var loseStatus = (currentPriceNum <= losePriceNum) && losePriceNum != 0 && currentPriceNum != 0;
    g_myStockList[stockCode].gainStatus = gainStatus;
    g_myStockList[stockCode].loseStatus = loseStatus;
    if (gainStatus){
        trClassName = "cTr_gainStatus";
    } else if (loseStatus){
        trClassName = "cTr_loseStatus";
    }
    return trClassName;
}

function getTdClassName4TargetStatus(currentPrice, targetPrice){
    var tdClassName = "";
    if (targetPrice != "") {
        var currentPriceNum = currentPrice.toNumber();
        var targetPriceNum = targetPrice.toNumber();
        if (currentPriceNum > 0 && targetPriceNum > 0) {
            if (currentPriceNum <= targetPriceNum) {
                tdClassName = "cTd_adjustTargetStatus";
                ;
            }
        }
    }
    return tdClassName;
}

function getTdClassName4TodayBreakThoughStatus(yesterdayClosePrice, todayMinPrice, todayMaxPrice){
    var tdClassName = "";
    var closePriceNum = yesterdayClosePrice.toNumber();
    var minPriceNum = todayMinPrice.toNumber();
    var maxPriceNum = todayMaxPrice.toNumber();
    if (maxPriceNum > closePriceNum){
        if (((maxPriceNum - closePriceNum) * 100 / closePriceNum) >= g_upBreakThoughPercent){
            tdClassName = "cSpan_upBreakThoughStatus";
        }
    } else if (closePriceNum > minPriceNum){
        if (((closePriceNum - minPriceNum) * 100 / closePriceNum)>= g_downBreakThoughPercent){
            tdClassName = "cSpan_downBreakThoughStatus";
        }
    }
    return tdClassName;
}

function getGapPercent(currentPrice, targetPrice){
    var gapPercent = "";
    if (targetPrice != "") {
        var currentPriceNum = Number(currentPrice);
        var targetPriceNum = Number(targetPrice);
        gapPercent = ((targetPriceNum - currentPriceNum) * 100 / currentPriceNum).toFixed(2) + "%";
    }
    return gapPercent;
}

function getGapPrice(currentPrice, targetPrice){
    var gapPrice = "";
    if (targetPrice != "") {
        gapPrice = (Number(targetPrice) - Number(currentPrice)).toFixed(2);
    }
    return gapPrice;
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
                                       tdClassName4TotalIncrease,
                                       trClassName4GainAndLoseStatus,
                                       isStopTrade,
                                       tdClassName4TodayBreakThoughStatus,
                                       totalPercentByMonth) {

    if (isStopTrade){
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
        trClassName4GainAndLoseStatus = "";
        tdClassName4TodayBreakThoughStatus = "";
    }
    if (trClassName4GainAndLoseStatus != ""){
        tdClassName4TodayBreakThoughStatus = "";
    }
    var procPanelHtmlString = "<a href='#' onclick='confirmAndRemoveCurrentStockItem(\"" + stockCode + "\")'>"
        + "<img src='/img/delete.png' alt='[取消]' title='删除' class='cImg_stockItemProc_b' /></a>&nbsp;&nbsp;&nbsp;&nbsp;"
        + "<a href='#' onclick='showUpdateStockInfoPanel(\"" + stockCode + "\")'>"
        + "<img src='/img/config.png' alt='[调整]' title='修改' class='cImg_stockItemProc_b' /></a>";
    var gainAndLoseIconHtmlString = getGainAndLoseIconHtml(stockCode);
    var gainAndLoseTip = getGainAndLoseTip(stockCode);

    return "<tr class='cTr_data " + trClassName4GainAndLoseStatus + "' hold_type='" + g_myStockList[stockCode].holdType + "'>"
                + "<td class='" + tdClassName4TodayBreakThoughStatus +"'>"
                    + gainAndLoseIconHtmlString + "&nbsp;&nbsp;|&nbsp;" + stockCode + "&nbsp;|&nbsp;" + name + "</td>"
                + "<td class='" + tdClassName4TodayIncrease + "'>" + currentPrice + "</td>"
                + "<td class='" + tdClassName4TodayIncrease + "'>" + todayPercent + "</td>"
                + "<td class='" + tdClassName4TodayOpenStatus + "'>" + todayOpenStatus + "</td>"
                + "<td class='" + tdClassName4TodayOpenStatus + "'>" + todayOpenPrice + "</td>"
                + "<td>" + yesterdayClosePrice + "</td>"
                + "<td class='cTd_downStatus'>" + todayMinPrice + "</td>"
                + "<td class='cTd_upStatus'>" + todayMaxPrice + "</td>"
                + "<td>" + holdStockDays + "</td>"
                + "<td class='" + tdClassName4TotalIncrease + " cTd_cursorDefault' title='" + gainAndLoseTip + "'>" + totalPercent + "</td>"
                + "<td class='" + tdClassName4TotalIncrease + "'>" + totalPercentByMonth + "</td>"
                + "<td>" + procPanelHtmlString + "</td>"
            + "</tr>";
}

function generateFollowedStockInfoTrHtmlString(stockCode,
                                               name,
                                               currentPrice,
                                               todayPercent,
                                               targetPrice,
                                               todayMinPrice,
                                               todayMaxPrice,
                                               gapPercent,
                                               holdStockDays,
                                               tdClassName4TodayIncrease,
                                               tdClassName4TargetStatus,
                                               isStopTrade,
                                               tdClassName4TodayBreakThoughStatus) {

    if (targetPrice == ""){
        targetPrice = g_NaN;
        gapPercent = g_NaN;
        tdClassName4TargetStatus = "";
    }
    if (isStopTrade){
        currentPrice = "停牌";
        tdClassName4TodayIncrease = "cTd_balanceStatus";
    }
    if (tdClassName4TargetStatus != ""){
        tdClassName4TodayBreakThoughStatus = "";
    }
    var procPanelHtmlString = "<a href='#' onclick='confirmAndRemoveCurrentFollowedStockItem(\"" + stockCode + "\")'>"
        + "<img src='/img/delete.png' alt='[取消]' title='删除' class='cImg_stockItemProc_b' /></a>&nbsp;&nbsp;&nbsp;&nbsp;"
        + "<a href='#' onclick='showUpdateFollowedStockInfoPanel(\"" + stockCode + "\")'>"
        + "<img src='/img/config.png' alt='[调整]' title='修改' class='cImg_stockItemProc_b' /></a>";
    var targetIconHtml = getAdjustTargetIconHtml(currentPrice, targetPrice);

    return "<tr class='cTr_data'>"
                + "<td class='" + tdClassName4TargetStatus + " " + tdClassName4TodayBreakThoughStatus + "'>" + targetIconHtml + "&nbsp;&nbsp;|&nbsp;" + stockCode + "&nbsp;|&nbsp;" + name + "</td>"
                + "<td class='" + tdClassName4TodayIncrease + "'>" + currentPrice + "</td>"
                + "<td class='" + tdClassName4TodayIncrease + "'>" + todayPercent + "</td>"
                + "<td class='cTd_downStatus'>" + todayMinPrice + "</td>"
                + "<td class='cTd_upStatus'>" + todayMaxPrice + "</td>"
                + "<td>" + targetPrice + "</td>"
                + "<td>" + gapPercent + "</td>"
                + "<td>" + holdStockDays + "</td>"
                + "<td>" + procPanelHtmlString + "</td>"
            + "</tr>";
}

function getGainAndLoseTip(stockCode){
    var content = "";
    var stockObj = g_myStockList[stockCode];
    if (stockObj != null){
        var buyPriceNum = stockObj.buyPrice.toNumber();
        var gainPriceNum = stockObj.gainPrice.toNumber();
        var losePriceNum = stockObj.losePrice.toNumber();
        if (gainPriceNum > 0) {
            content += "止盈：" + ((gainPriceNum - buyPriceNum) * 100 / buyPriceNum).toFixed(0) + "%";
        }
        if (losePriceNum > 0) {
            if (content != ""){
                content += " | ";
            }
            content += "止损：" + ((buyPriceNum - losePriceNum) * 100 / buyPriceNum).toFixed(0) + "%";
        }
    }
    return content;
}

function getGainAndLoseIconHtml(stockCode){
    var htmlString = "";
    var gainPriceNum = Number(g_myStockList[stockCode].gainPrice);
    var losePriceNum = Number(g_myStockList[stockCode].losePrice);
    if (gainPriceNum > 0){
        htmlString += "<img src='/img/gain.png' title='止盈目标：" + gainPriceNum + "' alt='盈' ";
        if (g_myStockList[stockCode].gainStatus){
            htmlString += "class='cImg_iconActive'";
        } else {
            htmlString += "class='cImg_iconNotActive'";
        }
        htmlString += " />";
    }
    if (losePriceNum > 0){
        htmlString += "<img src='/img/lose.png' title='止损目标：" + losePriceNum + "' alt='损' ";
        if (g_myStockList[stockCode].loseStatus){
            htmlString += "class='cImg_iconActive'";
        } else {
            htmlString += "class='cImg_iconNotActive'";
        }
        htmlString += " />";
    }
    return htmlString;
}

function getAdjustTargetIconHtml(currentPrice, targetPrice){
    var currentPriceNum = Number(currentPrice);
    var targetPriceNum = Number(targetPrice);
    var html = "<img src='/img/target.png' title='回调目标：" + targetPrice + "' alt='调' ";
    if (currentPriceNum <= targetPriceNum){
        html += "class='cImg_iconActive'";
    } else {
        html += "class='cImg_iconNotActive'";
    }
    html += " />";
    return html;
}

function checkAndSaveStockData(){
    var stockCode = $("#iIpt_stockCode").val().trim();
    var buyPrice = $("#iIpt_buyPrice").val().trim();
    var holdNum = $("#iIpt_holdNum").val().trim();
    var buyDate = $("#iIpt_buyDate").val().trim();
    var gainPrice = $("#iIpt_gainPrice").val().trim();
    var losePrice = $("#iIpt_losePrice").val().trim();
    var holdType = $("a.cA_radioSelected").attr("value");
    if (checkUserInputStockData(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType)){
        addStockDataToCookieAndRefreshPage(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType);
    }
}

function checkAndUpdateStockData(){
    var stockCode = $("#iSpan_updateStockCode").text();
    var buyPrice = $("#iIpt_buyPrice").val().trim();
    var holdNum = $("#iIpt_holdNum").val().trim();
    var gainPrice = $("#iIpt_gainPrice").val().trim();
    var losePrice = $("#iIpt_losePrice").val().trim();
    var buyDate = $("#iIpt_buyDate").val().trim();
    var holdType = $("a.cA_radioSelected").attr("value");
    if (checkUserInputStockData(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType)){
        addStockDataToCookieAndRefreshPage(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType);
    }
}

function checkAndUpdateFollowedStockData(){
    var stockCode = $("#iSpan_updateFollowedStockCode").text();
    var targetPrice = $("#iIpt_followedTargetPrice").val().trim();
    var followedDate = g_myFollowedStockList[stockCode].followedDate;
    if (checkUserInputFollowedStockData(stockCode, targetPrice)) {
        addFollowedStockDataToCookieAndRefreshPage(stockCode, targetPrice, followedDate);
    }
}

function checkAndSaveFollowedStock(){
    var stockCode = $("#iIpt_followedStockCode").val().trim();
    var targetPrice = $("#iIpt_followedTargetPrice").val().trim();
    var followedDate = getTodayDateString();
    if (checkUserInputFollowedStockData(stockCode, targetPrice)){
        addFollowedStockDataToCookieAndRefreshPage(stockCode, targetPrice, followedDate);
    }
}

function checkUserInputStockData(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType) {
    var passStatus = false;
    var errorMsg = "";

    if (!checkStockCodeAvailable(stockCode)) {
        errorMsg = "股票代码输入有误，是不是忘了前缀了？";
    } else if (!checkStockPriceAvailable(buyPrice)) {
        errorMsg = "成本价格输入有误";
    } else if (!checkStockHoldNum(holdNum)){
        errorMsg = "持股数量输入有误";
    } else if (!checkStockPriceAvailable(gainPrice) && gainPrice != "") {
        errorMsg = "止盈价格输入有误";
    } else if (!checkStockPriceAvailable(losePrice) && losePrice != "") {
        errorMsg = "止损价格输入有误";
    } else if (!checkDateAvailable(buyDate)) {
        errorMsg = "买入日期输入有误";
    } else if ((Number(gainPrice) <= Number(losePrice)) && gainPrice != "" && losePrice != "") {
        errorMsg = "止盈价格必须大于止损价格";
    } else if (holdType != "short" && holdType != "long"){
        errorMsg = "持股类型有误";
    }else {
        passStatus = true;
    }

    if (!passStatus && errorMsg != ""){
        alert(errorMsg);
    }
    return passStatus;
}

function checkStockCodeAvailable(stockCode){
    var stockCodeReg = /^(sz|SZ|sh|SH)\d{6}$/;
    return (stockCode != "" && stockCodeReg.test(stockCode.trim()));
}

function checkStockHoldNum(holdNum){
    var holdNumReg = /^\d+$/;
    return (holdNum != "" && holdNumReg.test(holdNum.trim()));
}

function checkStockPriceAvailable(stockPrice){
    var priceReg = /^\d+(.\d{0,3})$/;
    return (stockPrice != "" && priceReg.test(stockPrice.trim()));
}

function checkDateAvailable(date){
    var dateReg = /^\d{4}-\d{2}-\d{2}$/;
    return (date != "" && dateReg.test(date.trim()));
}

function checkUserInputFollowedStockData(stockCode, targetPrice){
    var passStatus = false;
    var errorMsg = "";
    if (!checkStockCodeAvailable(stockCode)) {
        errorMsg = "股票代码输入有误，是不是忘了前缀了？";
    } else if (!(checkStockPriceAvailable(targetPrice) || targetPrice == "")) {
        errorMsg = "目标价格输入有误";
    }else {
        passStatus = true;
    }

    if (!passStatus && errorMsg != ""){
        alert(errorMsg);
    }
    return passStatus;
}

function updateGlobalStockData(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType){
    g_myStockList[stockCode] = new StockData(stockCode, "", buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType);
}

function updateGlobalFollowedStockData(stockCode, targetPrice, followedDate){
    g_myFollowedStockList[stockCode] = new FollowedStockData(stockCode, "", targetPrice, followedDate);
}

function getTodayDateString(){
    return new Date().format("yyyy-MM-dd");
}

function resetAllInputControls(){
    $("input[type=text]").val("");
    $("input[type=password]").val("");
    selectStockHoldTypeRadio($("a[value='short']"));
    resetQuickTargetPriceCalculator();
}

function readStockDataFromCookie(){
    var stockDataInCookie =  $.cookie(g_userCookieName);
    g_myAllStockDataInCookie = (stockDataInCookie == undefined || stockDataInCookie == null) ? "" : stockDataInCookie;
    if (g_myAllStockDataInCookie != ""){
        var arr = g_myAllStockDataInCookie.split("::");
        g_myStockDataInCookie = arr[0];
        if (arr.length == 2){
            g_myFollowedStockDataInCookie = arr[1];
        }
    }
}

function addStockDataToCookie(){
    $.cookie(g_userCookieName, g_myAllStockDataInCookie, { expires: g_cookieSavedDaysNum });
}

function removeStockItemFromCookie(stockCode){
    if (stockCode in g_myStockList){
        delete g_myStockList[stockCode];
    }
    updateStockDataToCookieAndRefreshPage();
}

function removeFollowedStockItemFromCookie(stockCode){
    if (stockCode in g_myFollowedStockList){
        delete g_myFollowedStockList[stockCode];
        updateStockDataToCookieAndRefreshPage();
    }
}

function confirmAndRemoveCurrentStockItem(stockCode){
    if (confirm("确定要取消对自选股【" + stockCode + "】的关注么？")){
        removeStockItemFromCookie(stockCode);
    }
}

function confirmAndRemoveCurrentFollowedStockItem(stockCode){
    if (confirm("确定要取消对自选股【" + stockCode + "】的追踪么？")){
        removeFollowedStockItemFromCookie(stockCode);
    }
}

function clearAllStockItemInCookie(){
    if (confirm("确定要清空当前自选股列表信息么？")){
        $.cookie(g_userCookieName, "");
        reloadPage();
    }
}

function updateStockItemToCookie(stockCode, buyPrice, holdNum, buyDate){
    if (stockCode != "" && buyPrice != "" && holdNum != "" && buyDate != ""){
        g_myStockList[stockCode] = new StockData(stockCode, "", buyPrice, holdNum, buyDate);
        updateStockDataToCookieAndRefreshPage();
    }
}

function addStockDataToCookieAndRefreshPage(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType){
    updateGlobalStockData(stockCode, buyPrice, holdNum, buyDate, gainPrice, losePrice, holdType);
    generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}

function addFollowedStockDataToCookieAndRefreshPage(stockCode, targetPrice, followedDate){
    updateGlobalFollowedStockData(stockCode, targetPrice, followedDate);
    generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}

function updateStockDataToCookieAndRefreshPage(){
    generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}

function parseStockDataIntoList(){
    if (g_myAllStockDataInCookie != undefined && g_myAllStockDataInCookie != ""){
        if (g_myStockDataInCookie != "") {
            var selectedStockArray = g_myStockDataInCookie.split(";");
            for (var i = 0; i < selectedStockArray.length; i++) {
                if (selectedStockArray[i] != "") {
                    var stockItems = selectedStockArray[i].split(",");
                    if (stockItems.length >= 5) {
                        var buyPrice = stockItems[1];
                        var holdNum = "0";
                        if (buyPrice.indexOf(":") != -1){
                            var arr = stockItems[1].split(":");
                            buyPrice = arr[0];
                            holdNum = arr[1];
                        }
                        var stockData = new StockData(stockItems[0], "", buyPrice, holdNum, stockItems[2], stockItems[3], stockItems[4]);
                        stockData.holdType = getStockHoldType(stockItems[5]);
                        g_myStockList[stockItems[0]] = stockData;
                    }
                }
            }
        }
        if (g_myFollowedStockDataInCookie != ""){
            var followedStockArray = g_myFollowedStockDataInCookie.split(";");
            for(var i = 0; i < followedStockArray.length; i++){
                if (followedStockArray[i] != "") {
                    var stockItems = followedStockArray[i].split(",");
                    if (stockItems.length == 3) {
                        g_myFollowedStockList[stockItems[0]] = new FollowedStockData(stockItems[0], "", stockItems[1], stockItems[2]);
                    }
                }
            }
        }
    }
}

function generateStockDataByList(){
    var selectedStockVal = "";
    for(var stockItemCode in g_myStockList){
        if (stockItemCode != undefined) {
            var stockItemObj = g_myStockList[stockItemCode];
            if (stockItemObj != null) {
                selectedStockVal += stockItemObj.stockCode + ","
                                    + stockItemObj.buyPrice + ":" + stockItemObj.holdNum + ","
                                    + stockItemObj.buyDate + ","
                                    + stockItemObj.gainPrice + ","
                                    + stockItemObj.losePrice + ","
                                    + stockItemObj.holdType + ";";
            }
        }
    }
    if (selectedStockVal != ""){
        selectedStockVal = selectedStockVal.substring(0, selectedStockVal.length - 1);
    }
    var followedStockVal = "";
    for(var stockItemCode in g_myFollowedStockList){
        if (stockItemCode != undefined) {
            var stockItemObj = g_myFollowedStockList[stockItemCode];
            if (stockItemObj != null) {
                followedStockVal += stockItemObj.stockCode + ","
                + stockItemObj.targetPrice + ","
                + stockItemObj.followedDate + ";"
            }
        }
    }
    if (followedStockVal != ""){
        followedStockVal = followedStockVal.substring(0, followedStockVal.length - 1);
    }
    g_myStockDataInCookie = selectedStockVal;
    g_myFollowedStockDataInCookie = followedStockVal;
    g_myAllStockDataInCookie = g_myStockDataInCookie + "::" + g_myFollowedStockDataInCookie;
}

function removeLastWord4String(str){
    var finalStr = "";
    if (str != ""){
        finalStr = str.substring(0, str.length - 1);
    }
    return finalStr;
}

function showStockDetailColumnsInTable(){
    g_showStockDetailColumnsStatus = true;
    controlStockDetailColumnsByStatus();
    updateShowDetailColumnsMenuTextAndEvent();
}

function showLongPeriodStockRows(){
    g_showLongPeriodStockRowsStatus = true;
    controlStockRowsByHoldTypeStatus();
    updateShowLongPeriodStockRowsMenuTextAndEvent();
}

function hideStockDetailColumnsInTable(){
    g_showStockDetailColumnsStatus = false;
    controlStockDetailColumnsByStatus();
    updateShowDetailColumnsMenuTextAndEvent();
}

function hideLongPeriodStockRows(){
    g_showLongPeriodStockRowsStatus = false;
    controlStockRowsByHoldTypeStatus();
    updateShowLongPeriodStockRowsMenuTextAndEvent();
}

function controlStockDetailColumnsByStatus(){
    var arr = g_stockDetailColumnsIndex.split(",");
    for (var i = 0; i < arr.length; i++){
        var $td = $("#iTbl_stockInfoList tr td:nth-child(" + arr[i] + ")");
        if (g_showStockDetailColumnsStatus){
            $td.show();
        } else {
            $td.hide();
        }
    }
}

function updateShowDetailColumnsMenuTextAndEvent(){
    if (g_showStockDetailColumnsStatus){
        $("#iTd_showSelectedStockDetailList_b")
            .unbind("click", showStockDetailColumnsInTable)
            .text("精简")
            .bind("click", hideStockDetailColumnsInTable);
    } else {
        $("#iTd_showSelectedStockDetailList_b")
            .unbind("click", hideStockDetailColumnsInTable)
            .text("详细")
            .bind("click", showStockDetailColumnsInTable);
    }
}

function controlStockRowsByHoldTypeStatus(){
    var showHoldType = g_showLongPeriodStockRowsStatus ? "long" : "short";
    var hideHoldType = g_showLongPeriodStockRowsStatus ? "short" : "long";
    $("tr[hold_type='" + showHoldType + "']").show();
    $("tr[hold_type='" + hideHoldType + "']").hide();
}

function updateShowLongPeriodStockRowsMenuTextAndEvent(){
    if (g_showLongPeriodStockRowsStatus){
        $("#iTd_showLongPeriodStockRows_b")
            .unbind("click", showLongPeriodStockRows)
            .text("短线")
            .bind("click", hideLongPeriodStockRows);
    } else {
        $("#iTd_showLongPeriodStockRows_b")
            .unbind("click", hideLongPeriodStockRows)
            .text("长线")
            .bind("click", showLongPeriodStockRows);
    }
}

function showAddSelectedStockPanel(){
    resetAllInputControls();
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_editStockInfoPanel_c");
    $("#iDiv_updateStockCode_c").hide();
    $("#iDiv_addStockCode_c").show();
    $("#iA_updateStockInfo_b").hide();
    $("#iA_addStockInfo_b").show();
    $("#iIpt_buyDate").val(getTodayDateString());
}

function showUpdateStockInfoPanel(stockCode){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_editStockInfoPanel_c");
    $("#iDiv_updateStockCode_c").show();
    $("#iDiv_addStockCode_c").hide();
    $("#iA_updateStockInfo_b").show();
    $("#iA_addStockInfo_b").hide();
    selectStockHoldTypeRadio($("a[value='" + g_myStockList[stockCode].holdType + "']"));
    resetQuickTargetPriceCalculator();
    autoFillStockInfoToUpdatedPanel(stockCode);
}

function showUpdateFollowedStockInfoPanel(stockCode){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_addFollowedStockPanel_c");
    $("#iDiv_updateFollowedStockCode_c").show();
    $("#iDiv_addFollowedStockCode_c").hide();
    $("#iA_updateFollowedStockInfo_b").show();
    $("#iA_addFollowedStockInfo_b").hide();
    autoFillFollowedStockInfoToUpdatedPanel(stockCode);
}

function showAddFollowedStockPanel(){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_addFollowedStockPanel_c");
    $("#iDiv_updateFollowedStockCode_c").hide();
    $("#iDiv_addFollowedStockCode_c").show();
    $("#iA_updateFollowedStockInfo_b").hide();
    $("#iA_addFollowedStockInfo_b").show();
}

function autoFillStockInfoToUpdatedPanel(stockCode){
    var currentStockData = g_myStockList[stockCode];
    $("#iSpan_updateStockCode").text(stockCode);
    $("#iSpan_updateStockName").text(currentStockData.stockName);
    $("#iIpt_buyPrice").attr("value", currentStockData.buyPrice);
    $("#iIpt_holdNum").attr("value", currentStockData.holdNum);
    $("#iIpt_gainPrice").attr("value", currentStockData.gainPrice);
    $("#iIpt_losePrice").attr("value", currentStockData.losePrice);
    $("#iIpt_buyDate").attr("value", currentStockData.buyDate);
}

function autoFillFollowedStockInfoToUpdatedPanel(stockCode){
    var currentStockData = g_myFollowedStockList[stockCode];
    $("#iSpan_updateFollowedStockCode").text(stockCode);
    $("#iSpan_updateFollowedStockName").text(currentStockData.stockName);
    $("#iIpt_followedTargetPrice").attr("value", currentStockData.targetPrice);
}

function showBackgroundCoverLayer(){
    var pageHeight = $(document).height();
    var pageWidth = $(document).width();
    $("#iDiv_backgroundCoverLayer").css(
        {
            "width":pageWidth + "px",
            "height":pageHeight + "px",
            "top": "0px",
            "left":"0px"
        }
    ).show();
}

function showProcPanelById(divId){
    var fixTopHeight = 50;
    var $panelObj = $("#" + divId);
    var panelWidth = Number($panelObj.attr("panelWidth"));
    var panelHeight = Number($panelObj.attr("panelHeight"));
    var panelWidthVal = panelWidth + "px";
    var panelHeightVal = panelHeight + "px";
    var pageHeight = Number($(document).height());
    var pageWidth = Number($(document).width());
    var leftVal = ((pageWidth - panelWidth) / 2) + "px";
    var topVal = ((pageHeight - panelHeight) / 2 - fixTopHeight) + "px";
    $panelObj.css({
        "left" : leftVal,
        "top" : topVal,
        "width" : panelWidthVal,
        "height" : panelHeightVal
    }).show()
        .children(".cTbl_userInput_c")
        .css({
            "width":panelWidthVal,
            "height":panelHeightVal
        })
        .siblings(".cDiv_userProcPanel_c").hide();
}

function hideBackgroundCoverLayerAndProcPanel(){
    $("#iDiv_backgroundCoverLayer").hide();
    $(".cDiv_userProcPanel_c").hide();
}

function backupSelectedStockDataToServer(){
    if(!checkUserLoginStatus()){
        return;
    }
    generateStockDataByList();
    if (confirm("确认要备份当前自选股列表么？")) {
        $.post("stock/save_cookie.do", {"userName": g_currentLoginUserName, "stockCookie": g_myAllStockDataInCookie, "quickCookie":g_myAllStockDataInCookie},
            function (data) {
                if (data == "success") {
                    alert("备份成功");
                } else {
                    alert("备份失败");
                }
            });
    }
}

function recoverSelectedStockDataFromServer(){
    if (confirm("确认要恢复之前备份的自选股列表么？")) {
        var stockCookie = $("#hidden_stock_cookie").val();
        if (stockCookie != "") {
            g_myAllStockDataInCookie = stockCookie;
            addStockDataToCookie();
            reloadPage();
        }
    }
}

function checkUserLoginStatus(){
    var isLogin = false;
    var userName = $("#hidden_user_name").val();
    if (userName == ""){
        g_currentLoginUserName = "";
    } else {
        g_currentLoginUserName = userName;
        isLogin = true;
    }
    return isLogin;
}

function showRegisterPanel(){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_userRegisterPanel_c");
}

function showLoginPanel(){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_userLoginPanel_c");
}

function register(){
    var userName = $("#iIpt_userName4Register").val().trim();
    var psw = $("#iIpt_psw4Register").val().trim();
    var confirmPsw = $("#iIpt_ConfirmPsw4Register").val().trim();
    if (checkBeforeRegister(userName, psw, confirmPsw)){
        checkUserExistAndRegister(userName, psw);
    }
}

function checkBeforeRegister(userName, psw, confirmPsw){
    var pass = false;
    var errorMsg = "";
    if (userName == ""){
        errorMsg = "账号不能为空";
    } else if (psw != confirmPsw){
        errorMsg = "两次输入口令不同";
    } else {
        pass = true;
    }
    if (errorMsg != "") {
        alert(errorMsg);
    }
    return pass;
}

function checkUserExistAndRegister(userName, psw){
    $.post("user/exist.do", { "userName": userName },
        function (data) {
            if (data == "success") {
                alert("账号[" + userName + "]已存在");
            } else {
                $.post("simple_register.do", { "userName": userName, "password": psw },
                    function (data) {
                        if (data == "success") {
                            alert("账号创建成功");
                            reloadPage();
                        } else {
                            alert("账号创建失败");
                        }
                    });
            }
        });
}

function login(){
    var userName = $("#iIpt_userName4Login").val().trim();
    var psw = $("#iIpt_psw4Login").val().trim();
    if(!checkBeforeLogin(userName, psw)){
        return;
    }
    $.post("login_ajax.do", { "userName": userName, "password": psw },
        function (data) {
            if (data == "success") {
                reloadPage();
            } else {
                alert("账号/口令不匹配");
                resetAllInputControls();
            }
        });
}

function checkBeforeLogin(userName, psw){
    var pass = false;
    var msg = "";
    if (userName == "" && psw == ""){
        msg = "账号/口令不能为空";
    } else if (userName == ""){
        msg = "账号不能为空";
    } else if (psw == ""){
        msg = "口令不能为空";
    } else {
        pass = true;
    }
    if (msg != "") {
        alert(msg);
    }
    return pass;
}

function logout(){
    var userName = $("#hidden_user_name").val();
    if (userName == null || userName == ""){
        return;
    }
    $.post("logout.do", { "userName": userName },
        function (data) {
            if (data == "success") {
                alert("成功退出");
                reloadPage();
            } else {
                alert("退出失败");
            }
        });
}

function quickLogin(event){
    var theEvent = window.event || event;
    var code = theEvent.keyCode || theEvent.which;
    if ($("#iIpt_psw4Login").val().trim() != ""){
        if (code == 13){
            login();
        }
    }
}

function showStockListByMenuName(obj, menuName){
    var groupType = "";
    var stockInfoTableId = "";
    if (menuName == "selected"){
        groupType = "selected";
        stockInfoTableId = "iTbl_stockInfoList";
    } else if (menuName == "followed"){
        groupType = "followed";
        stockInfoTableId = "iTbl_followedStockInfoList";
    }
    if (groupType != "" && stockInfoTableId != "") {
        $(".cA_tabMenuSelected").removeClass("cA_tabMenuSelected").addClass("cA_tabMenu");
        $(obj).removeClass("cA_tabMenu").addClass("cA_tabMenuSelected");
        $("table[group_type]").hide();
        $("table[group_type=" + groupType + "]").show();
        $("a[group_type]").hide();
        $("a[group_type=" + groupType + "]").show();
    }
}

function selectStockHoldTypeRadio(obj){
    if (!$(obj).hasClass("cA_radioSelected")){
        $(obj).removeClass("cA_radio").addClass("cA_radioSelected").siblings("a").removeClass("cA_radioSelected").addClass("cA_radio");
    }
}

function resetQuickTargetPriceCalculator(){
    $("select.cSel_quickCalculator").val("");
}

function getStockHoldType(holdType){
    var type = "short";
    if (holdType != undefined && holdType == "long"){
        type = holdType;
    }
    return type;
}

function quickAddTargetPriceByBuyPrice(obj){
    var $buyPriceObj = $("#iIpt_buyPrice");
    var buyPrice = $buyPriceObj.val().trim();
    if (buyPrice != "" && checkStockPriceAvailable(buyPrice)){
        var multiplier = $(obj).val();
        if (multiplier != "") {
            var targetPrice = (buyPrice.toNumber() * multiplier.toNumber()).toFixed(2);
            $(obj).siblings("input").val(targetPrice);
        }
    } else {
        $buyPriceObj.val("");
    }
}

function checkBuyPriceAndAutoUpdateTargetPrice(){
    var $buyPriceObj = $("#iIpt_buyPrice");
    var buyPrice = $buyPriceObj.val().trim();
    if (buyPrice != ""){
        var priceColor = "black";
        if (!checkStockPriceAvailable(buyPrice)){
            priceColor = "red";
        } else {
            var $gainPriceObj = $("#iIpt_gainPrice");
            var $lostPriceObj = $("#iIpt_losePrice");
            var quickGainMultiplier = $gainPriceObj.siblings("select").val();
            var quickLostMultiplier = $lostPriceObj.siblings("select").val();
            var gainTargetPrice = "";
            var lostTargetPrice = "";
            if (quickGainMultiplier != ""){
                gainTargetPrice = (quickGainMultiplier.toNumber() * buyPrice.toNumber()).toFixed(2);
            }
            if (quickLostMultiplier != ""){
                lostTargetPrice = (quickLostMultiplier.toNumber() * buyPrice.toNumber()).toFixed(2);
            }
            $gainPriceObj.val(gainTargetPrice);
            $lostPriceObj.val(lostTargetPrice);
        }
        $buyPriceObj.css("color", priceColor);
    }
}

function shareMyShortHoldStocksToLaoNiu(){
    if(!checkUserLoginStatus()){
        return;
    }
    $.post("group_member/add.do", { "userName": g_currentLoginUserName, "groupId":g_laoNiuGroupId },
        function (data) {
            if (data == "success") {
                reloadPage();
            } else {
                alert("添加分享群失败");
            }
        });
}

function stopShareMyShortHoldStocksToLaoNiu(){
    if(!checkUserLoginStatus()){
        return;
    }
    $.post("group_member/quite.do", { "userName": g_currentLoginUserName, "groupId":g_laoNiuGroupId },
        function (data) {
            if (data == "success") {
                reloadPage();
            } else {
                alert("退出分享群失败");
            }
        });
}

function goFriendSharePage(){
    window.location.href = "/stock/share/" + g_laoNiuGroupId;
}
