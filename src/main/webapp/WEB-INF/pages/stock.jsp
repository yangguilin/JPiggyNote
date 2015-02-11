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
  <link rel="shortcut icon" href="/img/stock_page_icon.png">
</head>
<body>
<c:if test="${empty userName}" var="notLogin"></c:if>
<div>
  <div>
    <input type="hidden" id="hidden_user_name" value="${userName}" />
    <input type="hidden" id="hidden_stock_cookie" value="${stockCookie}" />
  </div>
  <div class="cDiv_realTimeInfo_c">
    <span class="cSpan_firstTitle"></span>
    <span id="iSpan_shangHaiZhiShuTitle">上证指数：</span>
    <span id="iSpan_shangHaiZhiShu"></span>&nbsp;&nbsp;|&nbsp;
    <span id="iSpan_currentTime"></span>&nbsp;&nbsp;|&nbsp;
    <c:if test="${!notLogin}">
      <span>当前登录用户：${userName}</span>
      <a id="iA_userLogout_b" href="javascript:;" class="grayButton" onclick="logout()">退出登录</a>
    </c:if>
    <c:if test="${notLogin}">
      <a id="iA_userRegister_b" href="javascript:;" class="grayButton" onclick="showRegisterPanel()">用户注册</a>
      <a id="iA_userLogin_b" href="javascript:;" class="grayButton" onclick="showLoginPanel()">用户登录</a>
    </c:if>
  </div>
  <div class="cDiv_procPanel_c">
    <span class="cSpan_firstTitle"></span>
    <a href="javascript:;" class="blueButton firstButton" onclick="clearAllStockItemInCookie()">清空列表</a>
    <c:if test="${!notLogin}">
      <a href="javascript:;" class="blueButton" onclick="backupSelectedStockDataToServer()">备份列表</a>
      <a id="iA_recoverStockList_b" href="javascript:;" class="blueButton" onclick="recoverSelectedStockDataFromServer()">恢复列表</a>
    </c:if>
    <a id="iA_showMoreColumns" class="blueButton" href="javascript:;" onclick="showStockDetailColumnsInTable()">详细模式</a>
    <a href="javascript:;" class="blueButton" onclick="showAddSelectedStockPanel()">添加自选</a>
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
<div id="iDiv_editStockInfoPanel_c" class="cDiv_userProcPanel_c" panelWidth="400" panelHeight="280">
  <table class="cTbl_userInput_c">
    <tr>
      <td>股票代码：</td>
      <td>
        <div id="iDiv_updateStockCode_c">
          <span id="iSpan_updateStockCode"></span>
          &nbsp;|&nbsp;
          <span id="iSpan_updateStockName"></span>
        </div>
        <div id="iDiv_addStockCode_c">
          <input id="iIpt_stockCode" type="text" />&nbsp;<span class="cSpan_comment">如：sz000099</span>
        </div>
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
      <td>止盈价格：</td>
      <td>
        <input id="iIpt_gainPrice" type="text" />
        &nbsp;<span class="cSpan_comment">如：15.67 (可选)</span>
      </td>
    </tr>
    <tr>
      <td>止损价格：</td>
      <td>
        <input id="iIpt_losePrice" type="text" />
        &nbsp;<span class="cSpan_comment">如：10.0 (可选)</span>
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
        <a href="javascript:;" class="grayButton firstButton" onclick="hideBackgroundCoverLayerAndProcPanel()">取消</a>
        <a id="iA_addStockInfo_b" href="javascript:;" class="grayButton" onclick="checkAndSaveStockData()">添加</a>
        <a id="iA_updateStockInfo_b" href="javascript:;" class="grayButton" onclick="checkAndUpdateStockData()">更新</a>
      </td>
    </tr>
  </table>
</div>
<div id="iDiv_userRegisterPanel_c" class="cDiv_userProcPanel_c" panelWidth="300" panelHeight="180">
  <table class="cTbl_userInput_c">
    <tr>
      <td>用户账号：</td>
      <td>
        <input id="iIpt_userName4Register" type="text" />&nbsp;
      </td>
    </tr>
    <tr>
      <td>用户口令：</td>
      <td>
        <input id="iIpt_psw4Register" type="password" />
      </td>
    </tr>
    <tr>
      <td>确认口令：</td>
      <td>
        <input id="iIpt_ConfirmPsw4Register" type="password" />
      </td>
    </tr>
    <tr>
      <td class="cTd_addStockButton_c" colspan="2">
        <a href="javascript:;" class="grayButton firstButton" onclick="hideBackgroundCoverLayerAndProcPanel()">取消</a>
        <a href="javascript:;" class="grayButton" onclick="register()">注册</a>
      </td>
    </tr>
  </table>
</div>
<div id="iDiv_userLoginPanel_c" class="cDiv_userProcPanel_c" panelWidth="300" panelHeight="150">
  <table class="cTbl_userInput_c">
    <tr>
      <td>用户账号：</td>
      <td>
        <input id="iIpt_userName4Login" type="text" />&nbsp;
      </td>
    </tr>
    <tr>
      <td>用户口令：</td>
      <td>
        <input id="iIpt_psw4Login" type="password" onkeydown="quickLogin(event)" />
      </td>
    </tr>
    <tr>
      <td class="cTd_addStockButton_c" colspan="2">
        <a href="javascript:;" class="grayButton firstButton" onclick="hideBackgroundCoverLayerAndProcPanel()">取消</a>
        <a href="javascript:;" class="grayButton" onclick="login()">登录</a>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
