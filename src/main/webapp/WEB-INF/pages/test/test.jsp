<%--
  Created by IntelliJ IDEA.
  User: guilin
  Date: 2014/8/1
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户相关操作调用测试页面</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/test.js"></script>
</head>
<body>
<h2>自定义配置</h2>
<br/>
<input type="button" value="添加" onclick="addCustomConfig()"/>
<br/>
<input type="button" value="更新" onclick="updateCustomConfig()"/>
<br/>
<input type="button" value="重置" onclick="resetCustomConfig()"/>
<br/>
<br/>
<h2>日常记录</h2>
<br/>
<input type="button" value="添加" onclick="addRecord()"/>
<br/>
<input type="button" value="更新" onclick="updateRecord()"/>
<br/>
<input type="button" value="删除" onclick="deleteRecord()"/>
</body>
</html>
