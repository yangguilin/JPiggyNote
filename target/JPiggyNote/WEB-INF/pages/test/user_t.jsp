<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 14-7-22
  Time: PM5:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>用户相关操作调用测试页面</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/user.js"></script>
</head>
<body>
    <div>
        <div>
            <span>添加新用户:</span>
            <form:form modelAttribute="user_new" action="../user/add" method="post">
                用户名：<form:input path="userName" /><br/>
                密码：<form:password path="password" /><br/>
                Email：<form:input path="email" /><br/>
                昵称：<form:input path="nikeName" /><br/>
                手机号：<form:input path="mobilePhone" /><br/>
                <input type="submit" value="注册" name="addSubmit" />
                <input type="reset" value="重置" />
            </form:form>
        </div>
        <br/>
        <br/>
        <div>
            <span>更新用户信息:</span>
            <form:form modelAttribute="user_update" action="../user/update" method="post">
                用户名：<form:input path="userName" /><br/>
                密码：<form:password path="password" /><br/>
                Email：<form:input path="email" /><br/>
                昵称：<form:input path="nikeName" /><br/>
                手机号：<form:input path="mobilePhone" /><br/>
                <input type="submit" value="更新" name="updateSubmit" />
                <input type="reset" value="重置" />
            </form:form>
        </div>
        <br/>
        <br/>
        <div>
            <span>删除用户:</span>
            <input id="txt_userName" type="text" class="normal_text" />
            <input type="button" value="删除用户" onclick="deleteUser()" />
        </div>
        <br/>
        <br/>
    </div>
</body>
</html>
