<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/11
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>个性化设置</title>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/custom_config.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/custom_config.css">
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
    <input id="hidden_userName" type="hidden" value="${customConfig.getUserName()}" />
    <input id="iIpt_monthCostPlan_h" type="hidden" value="${customConfig.getMonthCostPlan()}" />
    <input id="iIpt_remarkAmount_h" type="hidden" value="${customConfig.getRemarkAmount()}" />
</div>
<div id="div_container">
    <div class="cDiv_globalMenu_c">
        <a href="#" class="cA_statMenu_b" onclick="window.location.href='/'">首页</a>
        &nbsp;/&nbsp;
        <a href="#" class="cA_statMenu_b" onclick="window.location.href='/stat'">统计</a>
        &nbsp;/&nbsp;
        <a href="#" class="cA_statMenu_b cA_statMenuSelected_b">设置</a>
        &nbsp;/&nbsp;
        <a href="#" class="cA_statMenu_b" onclick="logout()">退出</a>
    </div>
    <table class="table_options">
        <tr>
            <td class="td_title">月计划消费额：</td>
            <td>
                <input id="input_month_cost_plan" type="text" class="input_text" placeholder="${customConfig.getMonthCostPlan()}" value="" onkeyup="check()" />
            </td>
        </tr>
        <tr>
            <td class="td_title">备注显示金额：</td>
            <td>
                <input id="input_remark_show_amount" type="text" class="input_text" placeholder="${customConfig.getRemarkAmount()}" value="" onkeyup="check()" />
            </td>
        </tr>
        <tr>
            <td colspan="2" style="height:10px;"></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="#" id="iA_update_b" class="button green cA_update_b" onclick="update()">更新</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
