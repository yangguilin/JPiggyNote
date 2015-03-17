
function StockHoldInfo(stockCode){
    this.stockCode = stockCode;
    this.holderList = {};
}
function StockHolder(userName, buyPrice){
    this.userName = userName;
    this.buyPrice = buyPrice;
}

var g_stockHoldInfoList = {};

$(document).ready(function(){
    initData();
    getLatestStockPriceFromSina();
    setTimeout(function() {
        initFriendsHoldStockTableList();
    }, g_intervalBeforeShowStockList);
});

/*
* 持股信息字符串格式：stockCode1::userName1:buyPrice1,userName2:buyPrice2,...;stockCode2::...
* */
function initData(){
    var friendHoldStockData = $("#iIpt_friendHoldStockData_h").val();
    var stockArr = friendHoldStockData.split(";");
    for (var stockIndex in stockArr){
        if (stockArr[stockIndex] != ""){
            var stockItems = stockArr[stockIndex].split("::");
            var stockCode = stockItems[0];
            var stockHoldInfo = new StockHoldInfo(stockCode);
            var holdDataItems = stockItems[1].split(",");
            for (var holdIndex in holdDataItems){
                if (holdDataItems[holdIndex] != ""){
                    var arr = holdDataItems[holdIndex].split(":");
                    var stockHolder = new StockHolder(arr[0], arr[1]);
                    stockHoldInfo.holderList[arr[0]] = stockHolder;
                }
            }
            g_stockHoldInfoList[stockCode] = stockHoldInfo;
        }
    }
}

function getLatestStockPriceFromSina(){
    var stockCodes = $("#iIpt_stockCodes_h").val();
    addSinaStockScriptElement(stockCodes);
}

function initFriendsHoldStockTableList(){
    var stockCodes = $("#iIpt_stockCodes_h").val();
    var stockCodeArr = stockCodes.split(",");
    for (var index in stockCodeArr){
        addStockInfoIntoTableList(stockCodeArr[index]);
    }
}

function addStockInfoIntoTableList(stockCode){
    var stockDataVarName = g_sinaStockVarPrefix + stockCode;
    if (checkSinaStockCodeAvailable(stockDataVarName)) {
        var elements = window[stockDataVarName].split(",");
        var stockName = elements[0];
        var todayOpenPrice = elements[1];
        var yesterdayClosePrice = elements[2];
        var currentPrice = elements[3];
        var todayPercent = getTodayPercent(yesterdayClosePrice, currentPrice);
        var holderNum = arrayCount(g_stockHoldInfoList[stockCode].holderList);
        var tdClassName4TodayIncrease = getTdClassName4TodayIncrease(currentPrice, yesterdayClosePrice);

        var htmlString = generateTableRowHtmlString(stockCode, stockName, todayOpenPrice, currentPrice, yesterdayClosePrice,
                                                    todayPercent, holderNum, tdClassName4TodayIncrease);
        $("#iTbl_stockHolderInfoList tbody").append($(htmlString));
    }
}

function generateTableRowHtmlString(stockCode,
                                       stockName,
                                       todayOpenPrice,
                                       currentPrice,
                                       yesterdayClosePrice,
                                       todayPercent,
                                       holderNum,
                                       tdClassName4TodayIncrease) {

    if (todayOpenPrice.toNumber() == 0){
        currentPrice = "停牌";
        todayPercent = g_NaN;
        tdClassName4TodayIncrease = "cTd_balanceStatus";
    }
    var procPanelHtmlString = "<a href='javascript:;' stock_code='" + stockCode
                                + "' yesterday_close_price='" + yesterdayClosePrice
                                + "' current_price='" + currentPrice
                                + "' onclick='showHolderListByStockCode(this)'>查看详情</a>";
    return "<tr class='cTr_data'>"
        + "<td>" + stockCode + "&nbsp;|&nbsp;" + stockName + "</td>"
        + "<td class='" + tdClassName4TodayIncrease + "'>" + currentPrice + "</td>"
        + "<td class='" + tdClassName4TodayIncrease + "'>" + todayPercent + "</td>"
        + "<td>" + holderNum + "</td>"
        + "<td>" + procPanelHtmlString + "</td>"
        + "</tr>";
}

function showHolderListByStockCode(obj){
    changeDetailButtonStatus(obj);
    autoSetDetailTablePosition();
    clearHolderDetailList("iTbl_holderDetailList");
    var stockCode = $(obj).attr("stock_code");
    var holderArr = g_stockHoldInfoList[stockCode].holderList;
    var currentPrice = $(obj).attr("current_price");
    var yesterdayClosePrice = $(obj).attr("yesterday_close_price");
    if (currentPrice == "停牌"){
        currentPrice = yesterdayClosePrice;
    }
    for (var index in holderArr){
        addHolderDataTolist(holderArr[index].userName, holderArr[index].buyPrice, currentPrice);
    }
}

function getTotalPercent(buyPrice, currentPrice){
    var totalPercent = "0.0";
    if (buyPrice != "" && currentPrice != "") {
        var buyPriceNum = Number(buyPrice);
        totalPercent = ((Number(currentPrice) - buyPriceNum) * 100 / buyPriceNum).toFixed(2) + "%";
    }
    return totalPercent;
}

function changeDetailButtonStatus(obj){
    $("a[stock_code]").removeClass("grayText").text("查看详情");
    $(obj).text("详情 ==>").addClass("grayText");
    $(obj).parent().parent().addClass("cTr_selectedRow").siblings("tr").removeClass("cTr_selectedRow");
}

function autoSetDetailTablePosition(){
    var scrollTopVal = $(document).scrollTop() + "px";
    $("#iTbl_holderDetailList").css({"position":"relative", "top":scrollTopVal});
}

function clearHolderDetailList(tableId){
    if ($("#" + tableId + " tr").length > 1){
        $("#" + tableId + " tr:gt(0)").remove();
    }
}

function addHolderDataTolist(userName, buyPrice, currentPrice){
    var totalPercent = getTotalPercent(buyPrice, currentPrice);
    var totalPercentTdClassName = getClassName4TotalPercent(buyPrice, currentPrice);
    var htmlString = "<tr class='cTr_data'>"
                     + "<td>" + userName + "</td>"
                     + "<td>" + buyPrice + "</td>"
                     + "<td class='" + totalPercentTdClassName + "'>" + totalPercent + "</td>"
                     + "</tr>"
    $("#iTbl_holderDetailList tbody").append($(htmlString));
}

function getClassName4TotalPercent(buyPrice, currentPrice){
    var className = "cTd_balanceStatus";
    var buyPriceNum = buyPrice.toNumber();
    var currentPriceNum = currentPrice.toNumber();
    if (buyPriceNum < currentPriceNum){
        className = "cTd_upStatus";
    } else if (buyPriceNum > currentPriceNum){
        className = "cTd_downStatus";
    }
    return className;
}

function backToMyStockPage(){
    window.location.href = "/stock";
}