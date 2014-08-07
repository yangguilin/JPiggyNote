<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/1
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>账目类别-测试页面</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/category.js"></script>
</head>
<body>
<div>
    <div>
        <span>添加新用户分类:</span>
        <form:form modelAttribute="category_new" action="../category/add" method="post">
            用户名：<form:input path="userName"/><br/>
            xml：<form:input path="categoryXml"/><br/>
            xml_sorted：<form:input path="categoryXmlSorted"/><br/>
            <input type="submit" value="添加" name="addSubmit"/>
            <input type="reset" value="重置"/>
        </form:form>
    </div>
    <br/>
    <input type="button" value="js添加" onclick="addCategory()" />
    <br/>
    <input type="button" value="js更新" onclick="updateCategory()" />
    <br/>
    <input type="button" value="js重置" onclick="resetCategory()" />
</div>
</body>
</html>
