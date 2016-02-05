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
    <meta property="wb:webmaster" content="eeb9f7fdacccbe7e" />
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/login.js"></script>
    <link href="/css/login.css" rel="stylesheet" type="text/css">
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="div_container">
        <h1>小猪de糊涂账</h1>
        <form action="login" method="get">
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
                        <input id="psw" type="password" name="password" onkeydown="quickLogin(event)" />
                    </td>
                </tr>
                <tr style="padding-top: 10px;">
                    <td colspan="2" style="padding-top:15px;">
                        <a href="#" style="font-size:18px; margin-left: 15px; margin-right: 30px;" class="button green" onclick="window.location.href='/register'">注册</a>
                        <a href="#" style="font-size:18px;" class="button green" onclick="login()">登录</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="div_login_msg" class="loginMsg">${errorMsg}</div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
