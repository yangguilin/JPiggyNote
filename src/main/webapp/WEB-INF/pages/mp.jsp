<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 15/10/15
  Time: PM4:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
  <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
  <title>密码又忘了，哎</title>
  <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="/js/common.js"></script>
  <script type="text/javascript" src="/js/mp.js"></script>
  <link href="/css/mp.css" rel="stylesheet" type="text/css">
  <link href="/css/common.css" rel="stylesheet" type="text/css">
  <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="cDiv_container">
    <div class="cDiv_top"></div>
    <div class="cDiv_content">
      <div>
        <span>
          <input type="text" id="iIpt_searchContent" class="cIpt_searchContent" />
        </span>
        <span>
          <a href="#" class="cA_searchButton_b" onclick="searchShowName()">找找看</a>
        </span>
      </div>
    </div>
    <div class="cDiv_foot"></div>
  </div>
</body>
</html>
