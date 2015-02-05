<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 15/1/12
  Time: PM1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
  <title>我的股票</title>
  <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="/js/jquery.cookie.js"></script>
  <script type="text/javascript" src="/js/stock.js"></script>
  <link href="/css/stock.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
  <div class="cDiv_realTimeInfo_c">
    <span>更新时间：</span>
    <span id="iSpan_currentTime"></span>
    &nbsp;&nbsp;|&nbsp;&nbsp;
    <span>上证指数：</span>
    <span id="iSpan_shangHaiZhiShu"></span>
  </div>
  <div class="cDiv_procPanel_c">
    <span class="cSpan_button" onclick="clearAllStockItemInCookie()">[清空列表]</span>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <span id="iSpan_showMoreColumns" class="cSpan_button" onclick="showStockDetailColumnsInTable()">[详细模式]</span>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <span class="cSpan_button" onclick="showAddSelectedStockPanel()">[添加自选股]</span>
  </div>
  <div class="cDiv_stockInfoList_c">
    <table id="iTbl_stockInfoList">
      <tr class="cTr_title">
        <td class="cTd_stockNameAndCode">股票名称</td>
        <td>当前价格</td>
        <td>今日涨幅</td>
        <td>开盘状态</td>
        <td>今日开盘</td>
        <td>昨日收盘</td>
        <td>今日最低</td>
        <td>今日最高</td>
        <td>持股天数</td>
        <td>盈亏比例</td>
        <td class="cTd_stockItemProcPanelColumn">功能操作</td>
      </tr>
    </table>
  </div>
  <br/>
</div>
<div id="iDiv_backgroundCoverLayer"></div>
<div id="iDiv_addSelectedStockPanel_c" class="cDiv_userProcPanel_c">
  <table class="cTbl_userInput_c">
    <tr>
      <td>股票代码：</td>
      <td>
        <input id="iIpt_stockCode" type="text" />
        &nbsp;<span class="cSpan_comment">如：sz000099</span>
      </td>
    </tr>
    <tr>
      <td>成本价格：</td>
      <td>
        <input id="iIpt_buyPrice" type="text" />
        &nbsp;<span class="cSpan_comment">如：11.123</span>
      </td>
    </tr>
    <tr>
      <td>建仓日期：</td>
      <td>
        <input id="iIpt_buyDate" type="text" />
        &nbsp;<span class="cSpan_comment">如：2015-01-11</span>
      </td>
    </tr>
    <tr>
      <td class="cTd_addStockButton_c" colspan="2">
        <span class="cSpan_button" onclick="hideBackgroundCoverLayerAndProcPanel()">[取消]</span>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <span class="cSpan_button" onclick="checkAndSaveStockData()">[添加]</span>
      </td>
    </tr>
  </table>
</div>
<div id="iDiv_updateStockInfoPanel_c" class="cDiv_userProcPanel_c">
  <table class="cTbl_userInput_c">
    <tr>
      <td>股票代码：</td>
      <td>
        <span id="iSpan_currentUpdateStockCode"></span>
      </td>
    </tr>
    <tr>
      <td>成本价格：</td>
      <td>
        <input id="iIpt_currentUpdateStockBuyPrice" type="text" />
        &nbsp;<span class="cSpan_comment">如：11.123</span>
      </td>
    </tr>
    <tr>
      <td>建仓日期：</td>
      <td>
        <input id="iIpt_currentUpdateStockBuyDate" type="text" />
        &nbsp;<span class="cSpan_comment">如：2015-01-11</span>
      </td>
    </tr>
    <tr>
      <td class="cTd_addStockButton_c" colspan="2">
        <span class="cSpan_button" onclick="hideBackgroundCoverLayerAndProcPanel()">[取消]</span>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <span class="cSpan_button" onclick="checkAndUpdateStockData()">[更新]</span>
      </td>
    </tr>
  </table>
</div>
<div id="iDiv_addFollowedStockPanel_c" class="cDiv_userProcPanel_c">
  <table class="cTbl_userInput_c">
    <tr>
      <td>股票代码：</td>
      <td></td>
    </tr>
    <tr>
      <td>目标价格：</td>
      <td></td>
    </tr>
  </table>
</div>
</body>
</html>
