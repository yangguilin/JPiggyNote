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
        <h2>小猪账本欢迎亲</h2>
        <div>
            <input class="btn_login" type="button" value="登录" onclick="window.location.href='/login'" />
        </div>
        <br/>
        <div>
            <input class="btn_register" type="button" value="注册" onclick="window.location.href='/register'" />
        </div>
    </c:if>

    <c:if test="${!notLogin}">
        <h2>欢迎亲：
            <span id="span_curUserName">${curUser.getUserName()}</span>
        </h2>
        <input class="btn_logout" type="button" value="退出" onclick="logout()" />
        <br/>
        <br/>
        <br/>
        <div>
            <input type="button" class="btn_daily_record" value="添加记录" onclick="window.location.href='/daily_record'" />
        </div>
        <br/>
        <div>
            <input type="button" class="btn_custom_config" value="自定义设置" onclick="window.location.href='/custom_config'" />
        </div>
        <br/>
        <div>
            <input type="button" class="btn_category" value="我的分类" onclick="window.location.href='/category'" />
        </div>
    </c:if>

</body>
</html>