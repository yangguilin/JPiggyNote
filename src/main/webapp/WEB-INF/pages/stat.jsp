<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 14-10-8
  Time: AM11:21
  To change this template use File | Settings | File Templates.
--%>
<%--
    1. 元素id格式：i + 元素缩写大写首字母_元素自定义名称_元素用途简写
    2. 元素class格式：c + 元素缩写大写首字母_元素自定义名称_元素用途简写
    3. 元素name格式：n + 元素缩写大写首字母_元素自定义名称_元素用途简写

    ps_01. 元素缩写大写首字母
        div:    div
        tbl:    table
        tr:     tr
        td:     td
        a:      a
        ipt:    input
        lab:    label
        btn:    button

    ps_02. 元素用途简写：
        c:  container（容器）
        t:  title（标题）
        d:  data（数据）
        m:  menu（菜单）
        l:  list（列表）
        b:  button（按钮）
        f:  footer（底部）
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />

    <title>统计数据</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/stat.js"></script>
    <link href="/css/stat.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/buttons.css" rel="stylesheet" type="text/css">
    <link href="/css/shared/screen.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div>
        <input type="hidden" id="hidden_userName" value="${curUser.getUserName()}" />
    </div>
    <div class="cDiv_main_c">
        <h2>
            <div class="groupList clear" style="font-style: normal;">
                <a href="#" class="button blue left cA_topMenu_b" onclick="window.location.href='/'">首页</a>
                <a href="#" class="button blue middle cA_topMenu_b" onclick="window.location.href='/custom_config'">设置</a>
                <a href="#" class="button blue right cA_topMenu_b" onclick="logout()">退出</a>
            </div>
        </h2>
        <div class="cDiv_content_c">
            <div>
                <a href="#" class="cA_statMenu_b cA_statMenuSelected_b">本月统计</a>
                &nbsp;&nbsp;/&nbsp;&nbsp;
                <a href="#" class="cA_statMenu_b">按月统计</a>
            </div>
            <div>
                <table id="iTbl_incomeData_l">
                    <tr>
                        <td id="iTd_incomeData_t" colspan="4">收入</td>
                    </tr>
                    <tr id="iTr_incomeDetail_c">
                        <td colspan="4">
                            <table class="cTbl_statDetail_l">
                                <c:forEach items="${statData.getCurMonthIncomeList()}" var="item">
                                    <tr>
                                        <td>${item.getCreateDate()}</td>
                                        <td>${item.getAmount()}&nbsp;元</td>
                                        <td>${item.getRemark()}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    <tr class="cTr_statData_f">
                        <td>记录:</td>
                        <td>${statData.getCurMonthIncomeCount()}</td>
                        <td>总计:</td>
                        <td>${statData.getCurMonthIncomeTotal()}&nbsp;元</td>
                    </tr>
                </table>
                <table id="iTbl_costData_l">
                    <tr>
                        <td id="iTd_costData_t" colspan="4">支出</td>
                    </tr>
                    <tr id="iTr_costDetail_c">
                        <td colspan="4">
                            <table class="cTbl_statDetail_l">
                                <c:forEach items="${statData.getCurMonthCostList()}" var="item">
                                    <tr>
                                        <td>${item.getCreateDate()}</td>
                                        <td>${item.getAmount()}&nbsp;元</td>
                                        <td>${item.getRemark()}</td>
                                    </tr>
                                </c:forEach>
                                <tr id="iTr_otherTotal_d">
                                    <td>无备注记录</td>
                                    <td>${statData.getCurMonthOtherCostTotal()}&nbsp;元</td>
                                    <td></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr class="cTr_statData_f">
                        <td>记录:</td>
                        <td>${statData.getCurMonthCostCount()}</td>
                        <td>总计:</td>
                        <td>${statData.getCurMonthCostTotal()}&nbsp;元</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
