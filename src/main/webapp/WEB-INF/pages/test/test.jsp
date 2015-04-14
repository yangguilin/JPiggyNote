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
    <script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="/js/jquery.remodal.min.js"></script>
    <script type="text/javascript" src="/js/test.js"></script>
    <link href="/css/jquery.remodal.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        .button {
            font-family: 'Helvetica Neue', 'Luxi Sans', 'DejaVu Sans', Tahoma, 'Hiragino Sans GB', STHeiti, 'Microsoft YaHei';
            border-sizing: border-box;
            display: block;
            font-size: 12px;
            height: 31px;
            line-height: 17px;
            text-align: left;
            width: 52px;
        }
        .grayButton {
            color: #333;
            cursor: pointer;
            font-size: 12px;
            padding: 6px 12px;
            border-radius: 2px;
            text-decoration: none;
            border: 1px solid #BCBCBC;
            font-family: 'Helvetica Neue', 'Luxi Sans', 'DejaVu Sans', Tahoma, 'Hiragino Sans GB', STHeiti, 'Microsoft YaHei';
        }
        .grayButton:hover { background-color:#f1f1f1; }
        a.blueButton {
            color: #fff;
            cursor: pointer;
            font-size: 12px;
            padding: 6px 12px;
            border-radius: 2px;
            text-decoration: none;
            border: 1px solid #2385d6;
            background-color: #248be4;
            font-family: 'Helvetica Neue', 'Luxi Sans', 'DejaVu Sans', Tahoma, 'Hiragino Sans GB', STHeiti, 'Microsoft YaHei';
        }
        a.blueButton:hover {
            background-color:#1e91f5;
        }
        a.tabMenu {
            color:#666;
            width:150px;
            height:22px;
            display:block;
            font-size: 14px;
            padding:12px 0px;
            text-align: center;
            text-decoration: none;
            border: 1px solid #DDD;
            background-color: #FBFAF8;
        }
        a.tabMenuSelected {
            width:150px;
            display:block;
            color:#01A2CA;
            padding:12px 0px;
            text-align: center;
            text-decoration: none;
            background-color: #FFF;
            border-left: 1px solid #DDD;
            border-right: 1px solid #DDD;
            border-top: 2px solid #01A2CA;
            border-bottom: 1px solid #FFF;
        }
        a.tabMenu:hover { color:#01A2CA; }
        ul.tabContainer li {
            list-style: none outside none;
            float: left;
        }
    </style>
    <script>
        window.remodalGlobals = {
            namespace: "modal",
            defaults: {
                hashTracking: false
            }
        };
    </script>
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
<br/>
<br/>
<br/>
<br/>
<a href="javascript:;" class="buttonText">深圳</a>
<a href="javascript:;" class="blueButton">操作按钮</a>
<br/>
<br/>
<br/>
<br/>
<div>
    <ul class="tabContainer">
        <li>
            <a href="javascript:;" class="tabMenuSelected">金融</a>
        </li>
        <li>
            <a href="javascript:;" class="tabMenu">游戏</a>
        </li>
        <li>
            <a href="javascript:;" class="tabMenu">移动</a>
        </li>
        <li>
            <a href="javascript:;" class="tabMenu">社区</a>
        </li>
    </ul>
</div>
<a class="" href="#modal">Popup</a>
<div class="remodal-bg">
    <a href="#modal">Call the modal with data-remodal-id="modal"</a>
</div>
<div class="remodal" data-remodal-id="modal">
    <h1>Remodal</h1>
    <p>
        Flat, responsive, lightweight, fast, easy customizable modal window plugin
        with declarative state notation and hash tracking.
    </p>
    <br>
    <a class="remodal-cancel" href="#">Cancel</a>
    <a class="remodal-confirm" href="#">OK</a>
</div>
</body>
</html>
