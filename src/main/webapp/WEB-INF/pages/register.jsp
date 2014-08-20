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
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/register.js"></script>
    <link href="/css/register.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="div_container">
        <h1>注册账号</h1>
        <form action="register.do" method="post" onsubmit="return checkPassword();">
            <table class="table_menu">
                <tr>
                    <td class="td_title">
                        <span class="span_title">账号</span>
                    </td>
                    <td>
                        <input id="txt_user_name" type="text" name="userName" username_status="" onblur="checkUserName()" />
                    </td>
                </tr>
                <tr>
                    <td class="td_title">
                        <span class="span_title">口令</span>
                    </td>
                    <td>
                        <input id="txt_psw" type="password" name="password" />
                    </td>
                </tr>
                <tr>
                    <td class="td_title">
                        <span class="span_title">确认</span>
                    </td>
                    <td>
                        <input id="txt_psw_confirm" type="password"  />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a href="#" class="button green" onclick="register()">注册</a>
                        <a href="#" class="button green" onclick="document.forms[0].reset()">重置</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="div_login_msg">${errorMsg}</div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
