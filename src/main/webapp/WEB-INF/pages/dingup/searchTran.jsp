<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 16/2/24
  Time: 下午7:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>根据中文匹配英文翻译</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/dingup/searchTran.js"></script>
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/dingup/searchTran.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div>
        <span>总数:${count}</span>
        <span>成功:${success}</span>
        <span>查询无结果:${noResult}</span>
        <span>失败:${fail}</span>
    </div>
</body>
</html>
