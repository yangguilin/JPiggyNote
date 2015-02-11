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
        <h1>请亲登录</h1>
        <form action="login.do" method="post">
            <div id="div_login_msg" class="loginMsg">${errorMsg}</div>
            <table class="table_menu">
                <tr>
                    <td class="td_title">
                        <span class="span_title">账号</span>
                    </td>
                    <td>
                        <input id="userName" type="text" name="userName" />
                    </td>
                </tr>
                <tr>
                    <td class="td_title">
                        <span class="span_title">口令</span>
                    </td>
                    <td>
                        <input id="psw" type="password" name="password" />
                    </td>
                </tr>
                <tr style="padding-top: 10px;">
                    <td colspan="2">
                        <a href="#" class="button green" onclick="login()">登录</a>
                        <a href="#" class="button green" onclick="document.forms[0].reset()">注册</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
