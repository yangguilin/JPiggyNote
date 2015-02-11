
// js class defined
function StockData(stockCode, stockName, buyPrice, buyDate, gainPrice, losePrice){
    this.stockCode = stockCode;
    this.stockName = stockName;
    this.buyPrice = buyPrice;
    this.buyDate = buyDate;
    this.gainPrice = gainPrice;
    this.losePrice = losePrice;
    this.gainStatus = false;
    this.loseStatus = false;
}

var g_NaN = "--";
var g_freshIntervalMillisecond = 5000;
var g_intervalBeforeShowStockList = 300;
var g_cookieSavedDaysNum = 90;
var g_shangHaiCode = "sh000001";
var g_userCookieName = "user_stock_data";
var g_stockDetailColumnsIndex = "4,5,6,7,8";
var g_sinaStockVarPrefix = "hq_str_";
var g_showStockDetailColumnsStatus = false;
var g_isTradeTime = false;
var g_myStockDataInCookie = "";
var g_myStockList = {};
var g_shangHaiCurrentPrice = "";
var g_shangHaiYesterdayPrice = "";
var g_currentLoginUserName = "";

var g_isTestMode = false;
var g_publishVersion = "1.2.1";


$(document).ready(function(){
    initPageDefaultJsEvents();
    initPageDefaultCss();
    readStockDataFromCookieAndParseToStockList();
    getStockInfoFromSina();
    setTimeout(function() {
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
    if (g_isTradeTime || g_isTestMode) {
        setInterval(
            function () {
                updateStockDataTableList();
                controlStockDetailColumnsByStatus();
            },
            g_freshIntervalMillisecond
        );
    }
}

function parseSinaStockDataAndInsertIntoTableList() {
    clearMyStockDataTableList();
    for (var stockItemCode in g_myStockList) {
        addStockInfoIntoTableList(stockItemCode);
    }
    if ($("#iTbl_stockInfoList tr").length == 1){
        addBlankInfoIntoTableList();
    }
}

function clearMyStockDataTableList(){
    if ($("#iTbl_stockInfoList tr").length > 1){
        $("#iTbl_stockInfoList tr:gt(0)").remove();
    }
}

function reloadPage(){
    window.location.reload();
}

function addStockInfoIntoTableList(stockCode){
    var stockDataVarName = g_sinaStockVarPrefix + stockCode;
    if (checkStockCodeAvailable(stockDataVarName)) {
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
        var tdClassName4GainAndLoseStatus = getTdClassName4GainAndLoseStatus(stockCode, currentPrice);

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
            tdClassName4GainAndLoseStatus
        );
        $("#iTbl_stockInfoList tbody").append($(stockInfoTdHtmlString));
    }
}

function addBlankInfoIntoTableList(){
    var blankInfoTdHtmlString = "<tr><td colspan='11' style='text-align: center;'><span>通过“添加自选”按钮添加个股，进行实时跟踪</span></td></tr>";
    $("#iTbl_stockInfoList tbody").append($(blankInfoTdHtmlString));
}

function checkStockCodeAvailable(stockDataVarName){
    return ((stockDataVarName in window) && (window[stockDataVarName] != ""));
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
    $("#iSpan_shangHaiZhiShuTitle").attr("class", shangHaiPriceStatus);
    showPageTitle();
}

function showPageTitle(){
    document.title = "上证：" + g_shangHaiCurrentPrice;
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

function getTdClassName4GainAndLoseStatus(stockCode, currentPrice){
    var tdClassName = "";
    var currentPriceNum = Number(currentPrice);
    var gainPriceNum = Number(g_myStockList[stockCode].gainPrice);
    var losePriceNum = Number(g_myStockList[stockCode].losePrice);
    var gainStatus = (currentPriceNum >= gainPriceNum) && gainPriceNum != 0;
    var loseStatus = (currentPriceNum <= losePriceNum) && losePriceNum != 0;
    g_myStockList[stockCode].gainStatus = gainStatus;
    g_myStockList[stockCode].loseStatus = loseStatus;
    if (gainStatus){
        tdClassName = "cTd_gainStatus";
    } else if (loseStatus){
        tdClassName = "cTd_loseStatus";
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
                                       tdClassName4TotalIncrease,
                                       tdClassName4GainAndLoseStatus) {

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
    var procPanelHtmlString = "<a href='#' onclick='confirmAndRemoveCurrentStockItem(\"" + stockCode + "\")'>[取消]</a>&nbsp;&nbsp;&nbsp;&nbsp;"
        + "<a href='#' onclick='showUpdateStockInfoPanel(\"" + stockCode + "\")'>[调整]</a>";
    var gainAndLoseIconHtmlString = getGainAndLoseIconHtml(stockCode);

    return "<tr class='cTr_data'>"
        + "<td class='" + tdClassName4GainAndLoseStatus + "'>" + gainAndLoseIconHtmlString + "&nbsp;&nbsp;|&nbsp;" + stockCode + "&nbsp;|&nbsp;" + name + "</td>"
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

function checkAndSaveStockData(){
    var stockCode = $("#iIpt_stockCode").val();
    var buyPrice = $("#iIpt_buyPrice").val();
    var buyDate = $("#iIpt_buyDate").val();
    var gainPrice = $("#iIpt_gainPrice").val();
    var losePrice = $("#iIpt_losePrice").val();

    if (checkUserInputStockData(stockCode, buyPrice, buyDate, gainPrice, losePrice)){
        addStockDataToCookieAndRefreshPage(stockCode, buyPrice, buyDate, gainPrice, losePrice);
    }
}

function checkAndUpdateStockData(){
    var stockCode = $("#iSpan_updateStockCode").text();
    var buyPrice = $("#iIpt_buyPrice").val();
    var gainPrice = $("#iIpt_gainPrice").val();
    var losePrice = $("#iIpt_losePrice").val();
    var buyDate = $("#iIpt_buyDate").val();
    if (checkUserInputStockData(stockCode, buyPrice, buyDate, gainPrice, losePrice)){
        addStockDataToCookieAndRefreshPage(stockCode, buyPrice, buyDate, gainPrice, losePrice);
    }
}

function checkUserInputStockData(stockCode, buyPrice, buyDate, gainPrice, losePrice) {
    var passStatus = false;
    var errorMsg = "";
    var stockCodeReg = /^(sz|SZ|sh|SH)\d{6}$/;
    var priceReg = /^\d+(.\d{0,3})$/;
    var dateReg = /^\d{4}-\d{2}-\d{2}$/;

    if (stockCode == "" || !stockCodeReg.test(stockCode)) {
        errorMsg = "股票代码输入有误，是不是忘了前缀了？";
    } else if (buyPrice == "" || !priceReg.test(buyPrice)) {
        errorMsg = "成本价格输入有误";
    } else if (gainPrice != "" && !priceReg.test(gainPrice)) {
        errorMsg = "止盈价格输入有误";
    } else if (losePrice != "" && !priceReg.test(losePrice)) {
        errorMsg = "止损价格输入有误";
    } else if (buyDate == "" || !dateReg.test(buyDate)) {
        errorMsg = "买入日期输入有误";
    } else if ((Number(gainPrice) <= Number(losePrice)) && gainPrice != "" && losePrice != ""){
        errorMsg = "止盈价格必须大于止损价格";
    }else {
        passStatus = true;
    }

    if (!passStatus && errorMsg != ""){
        alert(errorMsg);
    }
    return passStatus;
}

function updateGlobalStockData(stockCode, buyPrice, buyDate, gainPrice, losePrice){
    var newStockItemData = stockCode + "," + buyPrice + "," + buyDate + "," + gainPrice + "," + losePrice;
    if (g_myStockDataInCookie == "" || g_myStockDataInCookie == undefined){
        g_myStockDataInCookie = newStockItemData;
    } else {
        g_myStockDataInCookie += ";" + newStockItemData;
    }
}

function resetAllInputControls(){
    $("#iIpt_stockCode").val("");
    $("#iIpt_buyPrice").val("");
    $("#iIpt_buyDate").val("");
    $("#iIpt_gainPrice").val("");
    $("#iIpt_losePrice").val("");
    $("#iIpt_userName4Register").val("");
    $("#iIpt_psw4Register").val("");
    $("#iIpt_pswConfirm4Register").val("");
    $("#iIpt_userName4Login").val("");
    $("#iIpt_psw4Login").val("");
}

function readStockDataFromCookie(){
    var stockDataInCookie =  $.cookie(g_userCookieName);
    g_myStockDataInCookie = (stockDataInCookie == undefined || stockDataInCookie == null) ? "" : stockDataInCookie;
}

function addStockDataToCookie(){
    $.cookie(g_userCookieName, g_myStockDataInCookie, { expires: g_cookieSavedDaysNum });
}

function removeStockItemFromCookie(stockCode){
    if (stockCode in g_myStockList){
        delete g_myStockList[stockCode];
        updateStockDataToCookieAndRefreshPage();
    }
}

function confirmAndRemoveCurrentStockItem(stockCode){
    if (confirm("确定要取消对自选股【" + stockCode + "】的关注么？")){
        removeStockItemFromCookie(stockCode);
    }
}

function clearAllStockItemInCookie(){
    if (confirm("确定要清空当前自选股列表信息么？")){
        $.cookie(g_userCookieName, "");
        reloadPage();
    }
}

function updateStockItemToCookie(stockCode, buyPrice, buyDate){
    if (stockCode != "" && buyPrice != "" && buyDate != ""){
        g_myStockList[stockCode] = new StockData(stockCode, "", buyPrice, buyDate);
        updateStockDataToCookieAndRefreshPage();
    }
}

function addStockDataToCookieAndRefreshPage(stockCode, buyPrice, buyDate, gainPrice, losePrice){
    updateGlobalStockData(stockCode, buyPrice, buyDate, gainPrice, losePrice);
    addStockDataToCookie();
    reloadPage();
}

function updateStockDataToCookieAndRefreshPage(){
    generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}

function parseStockDataIntoList(){
    if (g_myStockDataInCookie != undefined && g_myStockDataInCookie != ""){
        var stockArray = g_myStockDataInCookie.split(";");
        for(var i = 0; i < stockArray.length; i++){
            if (stockArray[i] != "") {
                var stockItems = stockArray[i].split(",");
                if (stockItems.length == 5) {
                    g_myStockList[stockItems[0]] = new StockData(stockItems[0], "", stockItems[1], stockItems[2], stockItems[3], stockItems[4]);
                }
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
                allStockDataValue += stockItemObj.stockCode + ","
                                    + stockItemObj.buyPrice + ","
                                    + stockItemObj.buyDate + ","
                                    + stockItemObj.gainPrice + ","
                                    + stockItemObj.losePrice + ";";
            }
        }
    }
    if (allStockDataValue != ""){
        allStockDataValue = allStockDataValue.substring(0, allStockDataValue.length - 1);
    }
    g_myStockDataInCookie = allStockDataValue;
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
}

function hideStockDetailColumnsInTable(){
    g_showStockDetailColumnsStatus = false;
    controlStockDetailColumnsByStatus();
}

function controlStockDetailColumnsByStatus(){
    var arr = g_stockDetailColumnsIndex.split(",");
        for (var i = 0; i < arr.length; i++){
            var $tds = $("#iTbl_stockInfoList tr td:nth-child(" + arr[i] + ")");
            if (g_showStockDetailColumnsStatus){
                $tds.show();
                $("#iA_showMoreColumns")
                    .unbind("click", showStockDetailColumnsInTable)
                    .text("精简模式")
                    .bind("click", hideStockDetailColumnsInTable);
            } else {
                $tds.hide();
                $("#iA_showMoreColumns")
                    .unbind("click")
                    .text("详细模式", hideStockDetailColumnsInTable)
                    .bind("click", showStockDetailColumnsInTable);
            }
        }
}

function showAddSelectedStockPanel(){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_editStockInfoPanel_c");
    $("#iDiv_updateStockCode_c").hide();
    $("#iDiv_addStockCode_c").show();
    $("#iA_updateStockInfo_b").hide();
    $("#iA_addStockInfo_b").show();
}

function showUpdateStockInfoPanel(stockCode){
    showBackgroundCoverLayer();
    showProcPanelById("iDiv_editStockInfoPanel_c");
    $("#iDiv_updateStockCode_c").show();
    $("#iDiv_addStockCode_c").hide();
    $("#iA_updateStockInfo_b").show();
    $("#iA_addStockInfo_b").hide();
    autoFillStockInfoToUpdatedPanel(stockCode);
}

function autoFillStockInfoToUpdatedPanel(stockCode){
    var currentStockData = g_myStockList[stockCode];
    $("#iSpan_updateStockCode").text(stockCode);
    $("#iSpan_updateStockName").text(currentStockData.stockName);
    $("#iIpt_buyPrice").attr("value", currentStockData.buyPrice);
    $("#iIpt_gainPrice").attr("value", currentStockData.gainPrice);
    $("#iIpt_losePrice").attr("value", currentStockData.losePrice);
    $("#iIpt_buyDate").attr("value", currentStockData.buyDate);
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
    if (confirm("确认要备份当前自选股列表么？")) {
        $.post("/stock/save_cookie.do", {"userName": g_currentLoginUserName, "stockCookie": g_myStockDataInCookie},
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
            g_myStockDataInCookie = stockCookie;
            addStockDataToCookie();
            reloadPage();
        }
    }
}

function checkUserLoginStatus(){
    var isLogin = false;
    var userName = $("#hidden_user_name").val();
    if (userName == ""){
        alert("请先登录，再操作");
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
    var userName = $("#iIpt_userName4Register").val();
    var psw = $("#iIpt_psw4Register").val();
    var confirmPsw = $("#iIpt_ConfirmPsw4Register").val();
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
    $.post("/user/exist.do", { "userName": userName },
        function (data) {
            if (data == "success") {
                alert("账号[" + userName + "]已存在");
            } else {
                $.post("/simple_register.do", { "userName": userName, "password": psw },
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
    var userName = $("#iIpt_userName4Login").val();
    var psw = $("#iIpt_psw4Login").val();
    if(!checkBeforeLogin(userName, psw)){
        return;
    }
    $.post("/login_ajax.do", { "userName": userName, "password": psw },
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
    $.post("/logout.do", { "userName": userName },
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
    if ($("#iIpt_psw4Login").val() != ""){
        if (code == 13){
            login();
        }
    }
}


/* function for test */
function addTestData(){
    g_myStockList["sz000977"] = new StockData("sz000977", "浪潮信息", "38.888", "2014-11-11", "", "");
    g_myStockList["sh603288"] = new StockData("sh603288", "海天味业", "42.341", "2014-12-03", "", "");
    g_myStockList["sh600509"] = new StockData("sh600509", "天富能源", "10.581", "2014-12-16", "", "");
    g_myStockList["sz000099"] = new StockData("sz000099", "中信海直", "14.62", "2014-12-22", "", "");
    g_myStockList["sz000837"] = new StockData("sz000837", "秦川机床", "11.115", "2015-01-12", "", "");
    g_myStockList["sh600597"] = new StockData("sh600597", "光明乳业", "18.311", "2015-01-14", "", "");
    generateStockDataByList();
    addStockDataToCookie();
    reloadPage();
}