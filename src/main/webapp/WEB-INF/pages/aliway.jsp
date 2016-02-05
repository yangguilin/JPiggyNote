<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 16/2/3
  Time: 下午5:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>阿里味儿</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/aliway.js"></script>
</head>
<body>
    <div>
        <div>
            <input id="iIpt_pageUrl" type="text" value="" />
        </div>
        <div>
            <span id="iSpan_curReplyNum"></span>
        </div>
        <div>
            <input type="button" value="获取" onclick="getPageHtmlContent()" />
        </div>
    </div>
</body>
</html>
