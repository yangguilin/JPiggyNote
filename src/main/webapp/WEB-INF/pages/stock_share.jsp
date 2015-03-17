<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 15/3/12
  Time: PM4:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>持股分享群</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/stock_share.js"></script>
    <script type="text/javascript" src="/js/stock_common.js"></script>
    <link href="/css/stock.css" rel="stylesheet" type="text/css">
    <link href="/css/stock_share.css" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="/img/stock_page_icon.png">
</head>
<body>
    <div class="cDiv_container">
        <div>
            <input type="hidden" id="iIpt_curUserName_h" value="${userName}" />
            <input type="hidden" id="iIpt_stockCodes_h" value="${stockCodes}" />
            <input type="hidden" id="iIpt_friendHoldStockData_h" value="${friendHoldStockData}" />
        </div>
        <div class="cDiv_systemInfo">
            <img class="cImg_systemInfoLogo" src="/img/stock-logo.png"/>
            <c:if test="${userName}">
                <span class="cSpan_loginInfo">当前登录用户：${userName}</span>
            </c:if>
            <a href="javascript:;" class="grayButton" onclick="backToMyStockPage()">返回我的持股页面</a>
        </div>
        <div class="cDiv_stockInfoList_c">
            <table id="iTbl_stockHolderInfoList" class="cTbl_dataList">
                <tr class="cTr_title">
                    <td class="cTd_holdStockNameAndCode">股票名称</td>
                    <td>当前价格</td>
                    <td>今日涨幅</td>
                    <td>用户合计</td>
                    <td class="cTd_holdStockItemProcPanelColumn">功能操作</td>
                </tr>
            </table>
            <table id="iTbl_holderDetailList" class="cTbl_dataList">
                <tr class="cTr_greenTitle">
                    <td class="cTd_holdStockUser">持股用户</td>
                    <td>成本价格</td>
                    <td>盈利幅度</td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
