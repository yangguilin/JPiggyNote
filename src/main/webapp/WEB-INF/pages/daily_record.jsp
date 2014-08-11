<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/8
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>日常记录</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/daily_record.js"></script>
    <link href="/css/daily_record.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>添加日常记录</h2>
    <div>
        <c:if test="${errorMsg}">
            <div style="color:red">${errorMsg}</div>
        </c:if>

        <table border="1px" width="40%">
            <tr>
                <td>用户</td>
                <td>
                    <span>${userBean.getUserName()}</span>
                </td>
            </tr>
            <tr>
                <td width="20%">操作</td>
                <td width="80%">
                    <select id="money_type">
                        <option value="COST" selected>[支出]</option>
                        <option value="INCOME">[收入]</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>金额</td>
                <td>
                    <input id="amount" type="number" min="0" required="required" autofocus="true" placeholder="0" value="0" />
                </td>
            </tr>
            <tr>
                <td>类别</td>
                <td>
                    <select id="category">
                        <option value="100" selected>衣</option>
                        <option value="101">食</option>
                        <option value="102">住</option>
                        <option value="103">行</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>统计</td>
                <td>
                    <select id="stat_type">
                        <option value="NORMAL" selected>日常</option>
                        <option value="PERIOD">阶段</option>
                        <option value="BIG">大额</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>备注</td>
                <td>
                    <textarea id="remark" rows="2" cols="150"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" class="btn_add" value="记一笔" onclick="addRecord()" />
                    <input id="hidden_user_name" type="hidden" value="${userBean.getUserName()}" />
                </td>
                <td>
                    <input type="button" class="btn_return" value="返回" onclick="backToMenu()" />
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
