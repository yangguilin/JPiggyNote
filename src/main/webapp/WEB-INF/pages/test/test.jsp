<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery.poshytip.js"></script>
    <%--<link href="/css/tip-yellowsimple.css" rel="stylesheet" type="text/css">--%>
    <link href="/css/tip-yellow.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        $(function(){
            $("#tip1").poshytip({allowTipHover:true});
        });
    </script>
</head>
<body>
<br />
<br />
<br />
<br />
<span id="tip1" title="嗨。。这里有个工具提示条！">鼠标滑上这里看看</span>
</body>
</html>