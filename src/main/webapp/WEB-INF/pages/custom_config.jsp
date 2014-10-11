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
    <script type="text/javascript" src="/js/custom_config.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/custom_config.css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="div_container">
    <h1>看看能配置啥</h1>
    <table class="table_options">
        <tr>
            <td class="td_title">月计划消费额</td>
            <td>
                <input id="input_month_cost_plan" type="text" class="input_text" value="${customConfig.getMonthCostPlan()}" />
            </td>
        </tr>
        <%--<tr>--%>
            <%--<td class="td_title">日常记录分类</td>--%>
            <%--<td>--%>
                <%--<select id="select_category_switch" class="input_text">--%>
                    <%--<option value="0" <c:if test="${customConfig.getCategorySwitch() == 0}">selected</c:if>>关闭</option>--%>
                    <%--<option value="1" <c:if test="${customConfig.getCategorySwitch() == 1}">selected</c:if>>开启</option>--%>
                <%--</select>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td class="td_title">垫付报销功能</td>--%>
            <%--<td>--%>
                <%--<select id="select_prepay_switch" class="input_text">--%>
                    <%--<option value="0" <c:if test="${customConfig.getPrepaySwitch() == 0}">selected</c:if>>关闭</option>--%>
                    <%--<option value="1" <c:if test="${customConfig.getPrepaySwitch() == 1}">selected</c:if>>开启</option>--%>
                <%--</select>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td class="td_title">备注显示金额</td>
            <td>
                <input id="input_remark_show_amount" type="text" class="input_text" value="${customConfig.getRemarkAmount()}" />
            </td>
        </tr>
        <tr>
            <td colspan="2" style="height:10px;"></td>
        </tr>
        <tr>
            <td colspan="2">
                <a href="#" style="font-size:18px; margin-left: 22px;" class="button green" onclick="backToMenu()">返回</a>
                <a href="#" style="font-size:18px; margin-left: 30px;" class="button green" onclick="update()">更新</a>
                <input id="hidden_curUserName" type="hidden" value="${customConfig.getUserName()}" />
            </td>
        </tr>
    </table>
</div>
</body>
</html>
