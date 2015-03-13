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
  <script type="text/javascript" src="/js/stock_common.js"></script>
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
  <div class="cDiv_systemInfo">
    <img class="cImg_systemInfoLogo" src="/img/stock-logo.png"/>
    <c:if test="${!notLogin}">
      <span class="cSpan_loginInfo">当前登录用户：${userName}</span>
      <a id="iA_userLogout_b" href="javascript:;" class="grayButton" onclick="logout()">退出登录</a>
    </c:if>
    <c:if test="${notLogin}">
      <a id="iA_userRegister_b" href="javascript:;" class="grayButton" onclick="showRegisterPanel()">用户注册</a>
      <a id="iA_userLogin_b" href="javascript:;" class="grayButton" onclick="showLoginPanel()">用户登录</a>
      <span class="cSpan_procTips"><b>西瓜温馨提示：</b>登陆后可对全部列表进行清空、备份和恢复等操作。不登陆的话，请保证使用固定浏览器，且不清空浏览器cookie缓存。</span>
    </c:if>
    <c:if test="${!notLogin}">
      <a href="javascript:;" class="grayButton" onclick="backupSelectedStockDataToServer()">备份全部列表</a>
      <a id="iA_recoverStockList_b" href="javascript:;" class="grayButton" onclick="recoverSelectedStockDataFromServer()">恢复全部列表</a>
      <a href="javascript:;" class="grayButton" onclick="clearAllStockItemInCookie()">清空全部列表</a>
        <c:if test="${beLaoNiuGroupMember}">
            <a href="javascript:;" class="grayButton" onclick="stopShareMyShortHoldStocksToLaoNiu()">退出老牛分享群</a>
        </c:if>
        <c:if test="${!beLaoNiuGroupMember}">
            <a href="javascript:;" class="grayButton" onclick="shareMyShortHoldStocksToLaoNiu()">加入老牛分享群</a>
        </c:if>
        <c:if test="${beLaoNiuGroupMember}">
            <a href="javascript:;" class="grayButton" onclick="goFriendSharePage()">跳转到老牛分享群</a>
        </c:if>
    </c:if>
  </div>
  <div class="cDiv_realTimeInfo_c">
    <img class="cImg_realTimeLogo" src="/img/clock.png"/>
    <span id="iSpan_shangHaiZhiShuTitle">上证指数：</span>
    <span id="iSpan_shangHaiZhiShu"></span>&nbsp;&nbsp;|&nbsp;
    <span id="iSpan_currentTime"></span>
  </div>
  <div class="cDiv_tabMenu_c">
    <ul>
      <li>
        <a id="iA_currentHoldStockTabMenu" href="javascript:;" class="cA_tabMenuSelected" onclick="showStockListByMenuName(this, 'selected')">目前持仓</a>
      </li>
      <li>
        <a id="iA_trailAdjustHoldStockTabMenu" href="javascript:;" class="cA_tabMenu" onclick="showStockListByMenuName(this, 'followed')">回调追踪</a>
      </li>
    </ul>
  </div>
  <div class="cDiv_procPanel_c">
    <span class="cSpan_firstTitle"></span>
    <a href="javascript:;" class="blueButton" group_type="selected" onclick="showAddSelectedStockPanel()">添加持仓股票</a>
    <a id="iA_addFollowedStock_b" href="javascript:;" class="blueButton" group_type="followed" onclick="showAddFollowedStockPanel()">添加追踪股票</a>
  </div>
  <div class="cDiv_stockInfoList_c">
    <table id="iTbl_stockInfoList" group_type="selected">
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
        <td>月化盈亏</td>
        <td class="cTd_stockItemProcPanelColumn">功能操作</td>
      </tr>
    </table>
    <table id="iTbl_showMore_c" group_type="selected">
      <tr>
        <td id="iTd_showSelectedStockDetailList_b" onclick="showStockDetailColumnsInTable()">详细</td>
      </tr>
      <tr>
        <td id="iTd_showLongPeriodStockRows_b" onclick="showLongPeriodStockRows()">长线</td>
      </tr>
    </table>
    <table id="iTbl_followedStockInfoList" group_type="followed">
      <tr class="cTr_title">
        <td class="cTd_stockNameAndCode">股票名称</td>
        <td>当前价格</td>
        <td>今日涨幅</td>
        <td>今日最低</td>
        <td>今日最高</td>
        <td>目标价格</td>
        <td>目标幅差</td>
        <td>跟踪天数</td>
        <td class="cTd_stockItemProcPanelColumn">功能操作</td>
      </tr>
    </table>
  </div>
</div>
<div id="iDiv_backgroundCoverLayer"></div>
<div id="iDiv_editStockInfoPanel_c" class="cDiv_userProcPanel_c" panelWidth="400" panelHeight="320">
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
        <input id="iIpt_buyPrice" type="text" onblur="checkBuyPriceAndAutoUpdateTargetPrice()" />
        &nbsp;<span class="cSpan_comment">如：11.123</span>
      </td>
    </tr>
    <tr>
      <td>止盈价格：</td>
      <td>
        <input id="iIpt_gainPrice" type="text" />&nbsp;
        <select class="cSel_quickCalculator" onchange="quickAddTargetPriceByBuyPrice(this)">
            <option value="">输入</option>
            <option value="1.03">3%</option>
            <option value="1.05">5%</option>
            <option value="1.07">7%</option>
            <option value="1.09">9%</option>
            <option value="1.11">11%</option>
            <option value="1.15">15%</option>
            <option value="1.17">17%</option>
            <option value="1.20">20%</option>
            <option value="1.3">30%</option>
            <option value="1.5">50%</option>
        </select>
        &nbsp;<span class="cSpan_comment">(选填)</span>
      </td>
    </tr>
    <tr>
      <td>止损价格：</td>
      <td>
        <input id="iIpt_losePrice" type="text" />&nbsp;
        <select class="cSel_quickCalculator" onchange="quickAddTargetPriceByBuyPrice(this)">
            <option value="">输入</option>
            <option value="0.97">-3%</option>
            <option value="0.96">-4%</option>
            <option value="0.90">-10%</option>
        </select>
        &nbsp;<span class="cSpan_comment">(选填)</span>
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
      <td>持股类型：</td>
      <td>
        <a href="javascript:;" class="cA_radioSelected" onclick="selectStockHoldTypeRadio(this)" group="hold_type" value="short">短线</a>
        <a href="javascript:;" class="cA_radio" onclick="selectStockHoldTypeRadio(this)" group="hold_type" value="long">长线</a>
        &nbsp;<span class="cSpan_comment"></span>
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
<div id="iDiv_addFollowedStockPanel_c" class="cDiv_userProcPanel_c" panelWidth="400" panelHeight="180">
  <table class="cTbl_userInput_c">
    <tr>
      <td>股票代码：</td>
      <td>
        <div id="iDiv_updateFollowedStockCode_c">
          <span id="iSpan_updateFollowedStockCode"></span>
          &nbsp;|&nbsp;
          <span id="iSpan_updateFollowedStockName"></span>
        </div>
        <div id="iDiv_addFollowedStockCode_c">
          <input id="iIpt_followedStockCode" type="text" />&nbsp;<span class="cSpan_comment">如：sz000099</span>
        </div>
      </td>
    </tr>
    <tr>
      <td>目标价格：</td>
      <td>
        <input id="iIpt_followedTargetPrice" type="text" />
        &nbsp;<span class="cSpan_comment">如：11.88 (选填)</span>
      </td>
    </tr>
    <tr>
      <td class="cTd_addStockButton_c" colspan="2">
        <a href="javascript:;" class="grayButton firstButton" onclick="hideBackgroundCoverLayerAndProcPanel()">取消</a>
        <a id="iA_addFollowedStockInfo_b" href="javascript:;" class="grayButton" onclick="checkAndSaveFollowedStock()">添加</a>
        <a id="iA_updateFollowedStockInfo_b" href="javascript:;" class="grayButton" onclick="checkAndUpdateFollowedStockData()">更新</a>
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
