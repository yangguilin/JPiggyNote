<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
    <title>欢迎来到小猪账本</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/home.js"></script>
    <link href="/css/home.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
    <c:if test="${empty curUser}" var="notLogin"></c:if>

    <c:if test="${notLogin}">
        <div class="div_container">
            <h1>小猪de糊涂账</h1>
            <table class="table_menu">
                <tr>
                    <td>
                        <a href="#" class="button green" onclick="window.location.href='/login'">登录</a>
                        <a href="#" class="button green" onclick="window.location.href='/register'">注册</a>
                    </td>
                </tr>
            </table>
        </div>
    </c:if>

    <c:if test="${!notLogin}">
        <div  class="div_container">
            <h2>欢迎亲：
                <span id="span_curUserName">${curUser.getUserName()}</span>
            </h2>
            <div class="groupList clear">
                <a href="#" class="button blue left" onclick="window.location.href='/daily_record'">记账</a>
                <a href="#" class="button blue middle" onclick="window.location.href='/category'">分类</a>
                <a href="#" class="button blue middle" onclick="window.location.href='/custom_config'">设置</a>
                <a href="#" class="button blue right" onclick="logout()">退出</a>
            </div>
            <br/>
            <div class="div_container">
                <table class="table_wallet">
                    <tr>
                        <td colspan="3">
                            <div class="div_cost_button_container div_button_unselected">
                                <img id="img_cost_button" src="/img/cost_button.png" width="40px" height="40px" onclick="selectImgButton(this)" />
                            </div>
                            <div class="div_income_button_container">
                                <img id="img_income_button" src="/img/income_button.png" width="40px" height="40px"  onclick="selectImgButton(this)"  />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="td_split"></td>
                    </tr>
                    <tr>
                        <td class="td_money_logo">
                            <img src="/img/money_logo_01.jpg" width="30px" height="30px" />
                        </td>
                        <td class="td_money_amount">
                            <input id="txt_amount" type="text" onblur="checkAmount()" />
                        </td>
                        <td>
                            <div class="clear">
                                <a id="btn_cost" href="#" class="button grey" onclick="">支出</a>
                                <a id="btn_income" href="#" class="button green" onclick="">收入</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </c:if>

</body>
</html>