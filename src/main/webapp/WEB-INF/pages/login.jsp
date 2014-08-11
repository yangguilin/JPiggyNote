<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/5
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/login.js"></script>
    <link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>请先登录</h2>
    <form action="login.do" method="post" onsubmit="return check();">
        <div class="loginMsg">${errorMsg}</div>
        <table border="1px" width="60%">
            <tr>
                <td width="20%">用户名</td>
                <td width="80%">
                    <input id="userName" type="text" name="userName" />
                    <span id="userInfoMsg" class="loginMsg"></span>
                </td>
            </tr>
            <tr>
                <td>密码</td>
                <td>
                    <input id="psw" type="password" name="password" />
                    <span id="pswInfoMsg" class="loginMsg"></span>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="登录" />
                    <input type="reset" value="重置" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
