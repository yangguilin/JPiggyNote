<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/7
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户注册</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/register.js"></script>
    <link href="/css/register.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>用户注册信息</h2>
    <form action="register.do" method="post" onsubmit="return checkPassword();">
        <c:if test="${errorMsg}">
            <div style="color:red">${errorMsg}</div>
        </c:if>

        <table border="1px" width="60%">
            <tr>
                <td width="20%">用户名</td>
                <td width="80%">
                    <input id="username" type="text" name="userName" username_status="" onblur="checkUserName()" />
                    <span id="userInfoMsg" class="username_msg_default"></span>
                </td>
            </tr>
            <tr>
                <td>密码</td>
                <td>
                    <input id="psw" type="password" name="password" />
                </td>
            </tr>
            <tr>
                <td>密码确认</td>
                <td>
                    <input id="psw2" type="password" name="again" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="注册" />
                    <input type="reset" value="重置" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
