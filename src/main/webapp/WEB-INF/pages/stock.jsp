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
  <div id="iDiv_updateTime">
    <span>更新时间：</span>
    <span id="iSpan_currentTime"></span>
  </div>
  <table id="iTbl_stockInfoList">
    <tr class="cTr_title">
      <td class="cTd_stockNameAndCode">股票名称</td>
      <td>当前价格</td>
      <td>涨幅比例</td>
      <td>开盘状态</td>
      <td>今日开盘</td>
      <td>昨日收盘</td>
      <td>今日最低</td>
      <td>今日最高</td>
      <td>持股天数</td>
      <td>盈亏比例</td>
      <td>操作</td>
    </tr>
  </table>
  <br/>
  <table>
    <tr>
      <td>股票代码：</td>
      <td>
        <input id="iIpt_stockCode" type="text" />
        &nbsp;<span class="cSpan_comment">如：sz000099</span>
      </td>
    </tr>
    <tr>
      <td>买入价格：</td>
      <td>
        <input id="iIpt_buyPrice" type="text" />
        &nbsp;<span class="cSpan_comment">如：11.11</span>
      </td>
    </tr>
    <tr>
      <td>买入日期：</td>
      <td>
        <input id="iIpt_buyDate" type="date" />
        &nbsp;<span class="cSpan_comment">如：2015-01-11</span>
      </td>
    </tr>
  </table>
  <br/>
  <input id="iIpt_AddStockToCookie_btn" type="button" onclick="checkAndSaveStockData()" value="添加该自选股" />
  <%--<input id="" type="button" onclick="updateSinaData4Test()" value="更新数据" />--%>
</div>
</body>
</html>
