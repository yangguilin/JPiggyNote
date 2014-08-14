<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>欢迎来到小猪账本</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/home.js"></script>
    <link href="/css/home.css" rel="stylesheet" type="text/css">
</head>
<body>
    <c:if test="${empty curUser}" var="notLogin"></c:if>

    <c:if test="${notLogin}">
        <div class="div_container">
            <h2>小猪de糊涂账</h2>
            <table border="1px" width="200px" class="table_menu">
                <tr>
                    <td>
                        <input type="button" value="登录" onclick="window.location.href='/login'" />
                    </td>
                    <td>
                        <input type="button" value="注册" onclick="window.location.href='/register'" />
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
            <table border="1px" width="400px" class="table_menu">
                <tr>
                    <td>
                        <input type="button" value="添加记录" onclick="window.location.href='/daily_record'" />
                    </td>
                    <td>
                        <input type="button" value="我的分类" onclick="window.location.href='/category'" />
                    </td>
                    <td>
                        <input type="button" value="自定义设置" onclick="window.location.href='/custom_config'" />
                    </td>
                    <td>
                        <input type="button" value="退出" onclick="logout()" />
                    </td>
                </tr>
            </table>
            <br/>
            <br/>
            <div class="div_container">
                <h3>俺的荷包</h3>
                <table width="400px" border="0px" class="table_wallet">
                    <tr>
                        <td class="td_first"></td>
                        <td colspan="2">
                            <div class="div_cost_button_container">
                                <img id="img_cost_button" src="/img/cost_button.png" width="40px" height="40px" onclick="selectImgButton(this)" />
                            </div>
                            <div class="div_income_button_container div_button_unselected">
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
                            <input id="btn_cost" type="button" value="支出" onclick="" />
                            <input id="btn_income" class="btn_hidden" type="button" value="收入" onclick="" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </c:if>

</body>
</html>