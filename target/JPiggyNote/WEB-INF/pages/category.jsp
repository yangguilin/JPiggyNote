<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/12
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>分类管理</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/category.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/category.css">
</head>
<body>
    <h2>管理我的分类</h2>
    <table id="table_category_list" border="1px" width="60%">
        <tr class="tr_title">
            <td width="10%">类别</td>
            <td width="35%">分类名称</td>
            <td width="10%">使用频率</td>
            <td width="15%">操作</td>
        </tr>
        <c:forEach items="${costMap}" var="item">
            <tr categoryid="${item.key}">
                <td>支出</td>
                <td>${item.value}</td>
                <td>N/A</td>
                <td>
                    <span onclick="unlockCategoryItemForEdit(this)" class="span_button">[修改]</span>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4" class="td_split"></td>
        </tr>
        <c:forEach items="${incomeMap}" var="item">
            <tr categoryid="${item.key}">
                <td>收入</td>
                <td>${item.value}</td>
                <td>N/A</td>
                <td>
                    <span onclick="unlockCategoryItemForEdit(this)" class="span_button">[修改]</span>
                </td>
            </tr>
        </c:forEach>
        <tr id="tr_opTr">
            <td colspan="2">
                <input type="button" class="btn_add" value="添加新分类" onclick="addNewCategory(this)" />
            </td>
            <td colspan="2">
                <input type="button" class="btn_return" value="返回" onclick="backToMenu()" />
            </td>
        </tr>
    </table>
    <div>
        <input type="hidden" id="hidden_user_name" value="${userName}" />
    </div>
</body>
</html>
