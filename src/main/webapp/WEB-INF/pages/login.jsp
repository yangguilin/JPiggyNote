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

    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />

    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/login.js"></script>

    <link href="/css/login.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="div_container">
        <h1>请先登录</h1>
        <form action="login.do" method="post" onsubmit="return check();">
            <div class="loginMsg">${errorMsg}</div>
            <table class="table_menu">
                <tr>
                    <td>用户名</td>
                    <td>
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
                    <td>
                        <a href="#" class="button green" onclick="document.forms[0].submit()">登录</a>
                        <a href="#" class="button green" onclick="document.forms[0].reset()">注册</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
