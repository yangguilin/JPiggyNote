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
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/custom_config.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/custom_config.css">
</head>
<body>
    <h2>简单还是精确，这是个问题</h2>
    <div>
        <table border="1px" width="40%">
            <tr>
                <td width="40%">月计划消费额</td>
                <td width="60%">
                    <input id="input_month_cost_plan" type="text" value="${customConfig.getMonthCostPlan()}" />
                </td>
            </tr>
            <tr>
                <td>记录分类功能</td>
                <td>
                    <select id="select_category_switch">
                        <option value="0" <c:if test="${customConfig.getCategorySwitch() == 0}">selected</c:if>>关闭</option>
                        <option value="1" <c:if test="${customConfig.getCategorySwitch() == 1}">selected</c:if>>开启</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>垫付/可报销功能</td>
                <td>
                    <select id="select_prepay_switch">
                        <option value="0" <c:if test="${customConfig.getPrepaySwitch() == 0}">selected</c:if>>关闭</option>
                        <option value="1" <c:if test="${customConfig.getPrepaySwitch() == 1}">selected</c:if>>开启</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" class="btn_update" value="更新" onclick="update()" />
                    <input id="hidden_curUserName" type="hidden" value="${customConfig.getUserName()}" />
                </td>
                <td>
                    <input type="button" class="btn_return" value="返回" onclick="backToMenu()" />
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
