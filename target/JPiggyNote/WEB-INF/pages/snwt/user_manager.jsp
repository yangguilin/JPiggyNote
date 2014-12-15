<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 14/12/12
  Time: PM4:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>向日葵你我他-用户测试页面</title>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/register.js"></script>
    <link href="/css/register.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="div_container">
    <h1>社员注册</h1>
    <form action="/snwt/user/register.do" method="post">
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
                    <span class="span_title">入学时间</span>
                </td>
                <td>
                    <input class="input_text" id="iIptYear" type="text" name="year" onblur="" />
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    <span class="span_title">手机号码</span>
                </td>
                <td>
                    <input class="input_text" id="iIptMobilePhone" type="text" name="mobilePhone" onblur="" />
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
                <td class="td_title">
                    <span class="span_title">口令确认</span>
                </td>
                <td>
                    <input class="input_text" id="iIptPswConfirm" type="password"  />
                </td>
            </tr>
            <tr>
                <td colspan="2" style="padding-top:15px;">
                    <a href="#" style="font-size:18px; margin-left: 15px; margin-right: 30px;" class="button green" onclick="document.forms[0].reset()">重置</a>
                    <a href="#" style="font-size:18px;" class="button green" onclick="register()">注册</a>
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
