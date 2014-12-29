<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 14/12/22
  Time: AM11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>向日葵你我他-社员登录</title>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/user_login.js"></script>
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
    <link href="/css/register.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="div_container">
    <h1>社员登录</h1>
    <form action="/snwt/user/login.do" method="post">
        <table class="table_menu">
            <tr>
                <td class="td_title">
                    <span class="span_title">社员姓名</span>
                </td>
                <td>
                    <input class="input_text" id="iIptName" type="text" name="name" onblur="" />
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    <span class="span_title">用户口令</span>
                </td>
                <td>
                    <input class="input_text" id="iIptPsw" type="password" name="password" />
                </td>
            </tr>
            <tr>
                <td colspan="2" style="padding-top:15px;">
                    <a href="#" style="font-size:18px;width:130px;" class="button green" onclick="login()">登录</a>
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
