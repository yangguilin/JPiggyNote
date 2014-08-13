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
            <div class="div_container">
                <h3>俺的荷包</h3>
                <table width="400px" border="1px" class="table_wallet">
                    <tr>
                        <td width="100px">
                            <img src="/img/money_logo_01.jpg" width="50px" height="50px" />
                        </td>
                        <td>
                            <input id="txt_amount" type="text" onblur="checkAmount()" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="button" value="收入" />
                            &nbsp;&nbsp;
                            <input type="button" value="支出" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </c:if>

</body>
</html>