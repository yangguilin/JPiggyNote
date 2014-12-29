<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <meta property="wb:webmaster" content="eeb9f7fdacccbe7e" />

    <title>聪明账，糊涂算</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/home.js"></script>
    <link href="/css/home.css" rel="stylesheet" type="text/css">
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
    <c:if test="${empty curUser}" var="notLogin"></c:if>

    <c:if test="${notLogin}">
        <div class="div_container">
            <h1>小猪de糊涂账</h1>
            <table class="table_menu">
                <tr>
                    <td class="td_title">
                        <span class="span_title">账号</span>
                    </td>
                    <td>
                        <input class="input_text" id="txt_user_name" type="text" value="${userNameInCookie}" />
                    </td>
                </tr>
                <tr>
                    <td class="td_title">
                        <span class="span_title">口令</span>
                    </td>
                    <td>
                        <input class="input_text" id="txt_psw" type="password" onkeydown="quickLogin(event)" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="padding-top:15px;">
                        <a href="#" style="font-size:18px; margin-left: 15px; margin-right: 30px;" class="button green" onclick="window.location.href='/register'">注册</a>
                        <a href="#" style="font-size:18px;" class="button green" onclick="login()">登录</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="div_login_msg">${errorMsg}</div>
                    </td>
                </tr>
            </table>
        </div>
    </c:if>
    <c:if test="${!notLogin}">
        <div class="div_container">
            <input type="hidden" id="hidden_userName" value="${curUser.getUserName()}" />
            <input type="hidden" id="hidden_remarkAmount" value="${customConfig.getRemarkAmount()}" />
            <div class="cDiv_globalMenu_c">
                <a href="#" class="cA_statMenu_b cA_statMenuSelected_b">首页</a>
                &nbsp;/&nbsp;
                <a href="#" class="cA_statMenu_b" onclick="window.location.href='/stat'">统计</a>
                &nbsp;/&nbsp;
                <a href="#" class="cA_statMenu_b" onclick="window.location.href='/custom_config'">设置</a>
                &nbsp;/&nbsp;
                <a href="#" class="cA_statMenu_b" onclick="logout()">退出</a>
            </div>
            <div class="div_content_container">
                <div class="div_main_content_container">
                    <table class="table_wallet">
                        <tr>
                            <td colspan="3">
                                <div class="div_cost_button_container div_button_unselected">
                                    <img id="img_cost_button" src="/img/btn_cost.png" onclick="selectImgButton(this)" />
                                </div>
                                <div class="div_income_button_container">
                                    <img id="img_income_button" src="/img/btn_income.png" onclick="selectImgButton(this)"  />
                                </div>
                                <div class="div_prepay_button_container">
                                    <img id="img_prepay_button" src="/img/btn_prepay.png" onclick="selectImgButton(this)"  />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" class="td_split"></td>
                        </tr>
                        <tr>
                            <td class="td_money_logo">
                                <img src="/img/logo_money.png" width="30px" height="30px" />
                            </td>
                            <td class="td_money_amount">
                                <input id="txt_amount" type="text" onkeyup="checkAmountAndAdd(event)" />
                            </td>
                            <td>
                                <div id="div_pro_buttons" class="clear">
                                    <a id="btn_cost" href="#" style="font-size: 20px;" class="button grey" onclick="addNewRecord(this)">支出</a>
                                    <a id="btn_income" href="#" style="font-size: 20px;" class="button green" onclick="addNewRecord(this)">收入</a>
                                    <a id="btn_prepay" href="#" style="font-size: 20px;" class="button grey" onclick="addNewRecord(this)">垫付</a>
                                    <a id="btn_prepay_back" href="#" style="font-size: 20px;" class="button green" onclick="addNewRecord(this)">收回</a>
                                </div>
                            </td>
                        </tr>
                        <tr id="tr_remark">
                            <td colspan="3">
                                <span class="span_remark">备注</span>
                                <input type="text" id="input_remark" onkeyup="checkAmountAndAdd(event)" />
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="div_history_container">
                    <h3 id="h3_today">今天(${todayList.size()})</h3>
                    <table id="tbl_today_list">
                        <c:forEach items="${todayList}" var="item">
                            <tr>
                                <td>
                                    <div class="div_record_item">
                                        <input record_id="${item.getId()}" title_id="h3_today" type="button" value="删除" onclick="deleteRecord(this)" />&nbsp;&nbsp;&nbsp;&nbsp;${moneyTypeMap.get(item.getMoneyType())}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getAmount()}&nbsp;元<c:if test="${item.getRemark() != ''}">&nbsp;&nbsp;|&nbsp;&nbsp;${item.getRemark()}</c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <h3 id="h3_yesterday">昨天(${yesterdayList.size()})</h3>
                    <table id="tbl_yesterday_list">
                        <c:forEach items="${yesterdayList}" var="item">
                            <tr>
                                <td>
                                    <div class="div_record_item">
                                        <input record_id="${item.getId()}" title_id="h3_yesterday" type="button" value="删除" onclick="deleteRecord(this)" />&nbsp;&nbsp;&nbsp;&nbsp;${moneyTypeMap.get(item.getMoneyType())}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getAmount()}&nbsp;元<c:if test="${item.getRemark() != ''}">&nbsp;&nbsp;|&nbsp;&nbsp;${item.getRemark()}</c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <h3 id="h3_dayafteryesterday">前天(${dayBeforeYesterdayList.size()})</h3>
                    <table id="tbl_dayBeforeYesterday_list">
                        <c:forEach items="${dayBeforeYesterdayList}" var="item">
                            <tr>
                                <td>
                                    <div class="div_record_item">
                                        <input record_id="${item.getId()}" title_id="h3_dayafteryesterday" type="button" value="删除" onclick="deleteRecord(this)" />&nbsp;&nbsp;&nbsp;&nbsp;${moneyTypeMap.get(item.getMoneyType())}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getAmount()}&nbsp;元<c:if test="${item.getRemark() != ''}">&nbsp;&nbsp;|&nbsp;&nbsp;${item.getRemark()}</c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
</body>
</html>