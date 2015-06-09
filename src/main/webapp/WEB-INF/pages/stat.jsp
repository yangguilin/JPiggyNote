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
        c:      common  通用样式

    ps_02. 元素用途简写：
        c:  container（容器）
        t:  title（标题）
        d:  data（数据）
        m:  menu（菜单）
        l:  list（列表）
        b:  button（按钮）
        f:  footer（底部）
        h:  hidden（隐藏）
        mk: mark（仅标记）
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
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/stat.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div>
        <input type="hidden" id="hidden_userName" value="${curUser.getUserName()}" />
        <input type="hidden" id="iIpt_monthCostPlan_h" value="${monthCostPlan}" />
        <input type="hidden" id="iIpt_curMonthCost_h" value="${statData.getCurMonthCostTotal()}" />
        <input type="hidden" id="iIpt_totalIncome_h" value="${statData.getFinalIncomeTotal()}" />
        <input type="hidden" id="iIpt_totalCost_h" value="${statData.getFinalCostTotal()}" />
        <input type="hidden" id="iIpt_curMonthNum_h" value="${statData.getMonthStatDataList().size()}" />
    </div>
    <div class="cDiv_main_c">
        <div class="cDiv_globalMenu_c">
            <a href="#" class="cA_statMenu_b" onclick="window.location.href='/'">首页</a>
            &nbsp;/&nbsp;
            <a href="#" class="cA_statMenu_b cA_statMenuSelected_b" title="下载全部记录" onclick="window.location.href='/stat/download'">统计</a>
            &nbsp;/&nbsp;
            <a href="#" class="cA_statMenu_b" onclick="window.location.href='/custom_config'">设置</a>
            &nbsp;/&nbsp;
            <a href="#" class="cA_statMenu_b" onclick="logout()">退出</a>
        </div>
        <div class="cDiv_content_c">
            <div>
                <a href="#" id="iA_curMonthStat_b" class="cA_statMenu_b cA_statMenuSelected_b">本月统计</a>
                &nbsp;/&nbsp;
                <a href="#" id="iA_monthStat_b" class="cA_statMenu_b">按月统计</a>
                &nbsp;/&nbsp;
                <a href="#" id="iA_prepayStat_b" class="cA_statMenu_b">垫付统计</a>
            </div>
            <div id="iDiv_curMonthStatInfo_c">
                <div id="iDiv_curMonthPlanCostProcess_c">
                    <table>
                        <tr>
                            <td id="iTd_curVal"></td>
                            <td id="iTd_leftVal"></td>
                        </tr>
                    </table>
                </div>
                <c:if test="${statData.getCurMonthIncomeList().size() > 0}">
                    <table id="iTbl_incomeData_l">
                        <tr>
                            <td id="iTd_incomeData_t" class="cC_itemTitle" colspan="4">
                                收入&nbsp;(&nbsp;${statData.getCurMonthIncomeCount()}&nbsp;)
                            </td>
                        </tr>
                        <tr id="iTr_incomeDetail_c">
                            <td colspan="4">
                                <table class="cC_detailList">
                                    <c:forEach items="${statData.getCurMonthIncomeList()}" var="item">
                                        <tr>
                                            <td>${item.getCreateDate()}</td>
                                            <td <c:if test="${item.getMoneyType() == 'PAYBACK'}">class="cTd_prepay_d"</c:if>>
                                                ${item.getAmount()}&nbsp;元
                                            </td>
                                            <td>${item.getRemark()}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        <tr class="cC_itemFooter">
                            <td>总计:</td>
                            <td>${statData.getCurMonthIncomeTotal()}&nbsp;元</td>
                            <td>收回:</td>
                            <td class="cTd_prepay_d">${statData.getCurMonthPaybackTotal()}&nbsp;元</td>
                        </tr>
                    </table>
                </c:if>
                <c:if test="${statData.getCurMonthCostList().size() > 0}">
                    <table id="iTbl_costData_l">
                        <tr>
                            <td id="iTd_costData_t" class="cC_itemTitle" colspan="4">
                                支出&nbsp;(&nbsp;${statData.getCurMonthCostCount()}&nbsp;)
                            </td>
                        </tr>
                        <tr id="iTr_costDetail_c">
                            <td colspan="4">
                                <table class="cC_detailList">
                                    <c:forEach items="${statData.getCurMonthCostList()}" var="item">
                                        <tr>
                                            <td>${item.getCreateDate()}</td>
                                            <td <c:if test="${item.getMoneyType() == 'PREPAY'}">class="cTd_prepay_d"</c:if>>
                                                ${item.getAmount()}&nbsp;元
                                            </td>
                                            <td>${item.getRemark()}</td>
                                        </tr>
                                    </c:forEach>
                                    <tr class="cTr_otherCostTotal_d">
                                        <td>无备注支出</td>
                                        <td>${statData.getCurMonthOtherCostTotal()}&nbsp;元</td>
                                        <td></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr class="cC_itemFooter">
                            <td>总计:</td>
                            <td>${statData.getCurMonthCostTotal()}&nbsp;元</td>
                            <td>垫付:</td>
                            <td class="cTd_prepay_d">${statData.getCurMonthPrepayTotal()}&nbsp;元</td>
                        </tr>
                    </table>
                </c:if>
                <c:if test="${statData.getCurMonthIncomeList().size() == 0 && statData.getCurMonthCostList().size() == 0}">
                        <table id="iTbl_emptyData_l">
                        <tr>
                            <td class="cTd_emptyItemTitle">
                                本月暂无任何记录
                            </td>
                        </tr>
                    </table>
                </c:if>
            </div>
            <div id="iDiv_monthStatInfo_c">
                <table id="iTbl_monthList_c">
                    <tr>
                        <td class="cC_totalItemTitle <c:choose><c:when test="${statData.getFinalTotal()>0}">cTd_totalMoneySurplus_t</c:when><c:otherwise>cTd_totalMoneyDebt_t</c:otherwise></c:choose>" colspan="4">
                            全部盈余&nbsp;&nbsp;|&nbsp;&nbsp;${statData.getFinalTotal()}&nbsp;元
                        </td>
                    </tr>
                    <tr>
                        <td class="<c:choose><c:when test='${statData.getFinalTotal()>0}'>cTd_totalSurplusSplit</c:when><c:otherwise>cTd_totalDebtSplit</c:otherwise></c:choose>" colspan="4"></td>
                    </tr>
                    <tr class="cC_totalItemFooter <c:if test='${statData.getFinalTotal()>0}'>cTr_totalSurplusItemFooter</c:if>">
                        <td>收入:</td>
                        <td>${statData.getFinalIncomeTotal()}&nbsp;元</td>
                        <td>支出:</td>
                        <td>${statData.getFinalCostTotal()}&nbsp;元</td>
                    </tr>
                    <tr class="cC_totalItemFooter cTr_monthAverageRow">
                        <td>月入:</td>
                        <td><span id="iSpan_monthIncome"></span>&nbsp;元</td>
                        <td>月出:</td>
                        <td><span id="iSpan_monthCost"></span>&nbsp;元</td>
                    </tr>
                    <tr class="cC_totalItemFooter cTr_monthAverageIncomeRow">
                        <td colspan="4">月均盈余：<span id="iSpan_monthAvg"></span>&nbsp;元</td>
                    <tr>
                        <td class="cC_itemSplitSpace" colspan="4"></td>
                    </tr>
                    <c:forEach items="${statData.getMonthStatDataList()}" var="item">
                    <tr>
                        <td colspan="4">
                            <table class="cTbl_monthItem_d">
                                <tr>
                                    <td name="monthTitle" class="cC_itemTitle <c:choose><c:when test="${item.getTotal()>0}">cTd_moneySurplus_t</c:when><c:otherwise>cTd_moneyDebt_t</c:otherwise></c:choose>" colspan="4">
                                        ${item.getMonth()}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getTotal()}&nbsp;元
                                    </td>
                                </tr>
                                <tr class="cTr_monthDetailData_c">
                                    <td colspan="4">
                                        <table class="cC_detailList">
                                            <c:forEach items="${item.getDetailList()}" var="record">
                                                <tr>
                                                    <td>${record.getCreateDate()}</td>
                                                    <td <c:if test="${record.getMoneyType() == 'INCOME'}">class="cTd_income_d"</c:if>>
                                                        ${record.getAmount()}&nbsp;元
                                                    </td>
                                                    <td>${record.getRemark()}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr class="cTr_otherIncomeTotal_d">
                                                <td>无备注收入</td>
                                                <td>${item.getOtherIncomeTotal()}&nbsp;元</td>
                                                <td></td>
                                            </tr>
                                            <tr class="cTr_otherCostTotal_d">
                                                <td>无备注支出</td>
                                                <td>${item.getOtherCostTotal()}&nbsp;元</td>
                                                <td></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr class="cC_itemFooter">
                                    <td>收入:</td>
                                    <td>${item.getIncomeTotal()}&nbsp;元</td>
                                    <td>支出:</td>
                                    <td>${item.getCostTotal()}&nbsp;元</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="cC_itemSplitSpace" colspan="4"></td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            <div id="iDiv_monthPrepayStatInfo_c">
                <c:set value="${statData.getFinalPaybackTotal()-statData.getFinalPrepayTotal()}" var="finalPrepayAndPaybackTotal" scope="page"></c:set>
                <table id="iTbl_monthPrepayList_c">
                    <tr>
                        <td class="cC_totalItemTitle <c:choose><c:when test="${finalPrepayAndPaybackTotal>0}">cTd_totalMoneySurplus_t</c:when><c:otherwise>cTd_totalMoneyDebt_t</c:otherwise></c:choose>" colspan="4">
                            垫付收支&nbsp;&nbsp;|&nbsp;&nbsp;${finalPrepayAndPaybackTotal}&nbsp;元
                        </td>
                    </tr>
                    <tr>
                        <td class="<c:choose><c:when test='${finalPrepayAndPaybackTotal>0}'>cTd_totalSurplusSplit</c:when><c:otherwise>cTd_totalDebtSplit</c:otherwise></c:choose>" colspan="4"></td>
                    </tr>
                    <tr class="cC_totalItemFooter <c:if test='${finalPrepayAndPaybackTotal>0}'>cTr_totalSurplusItemFooter</c:if>">
                        <td>收回:</td>
                        <td>${statData.getFinalPaybackTotal()}&nbsp;元</td>
                        <td>垫付:</td>
                        <td>${statData.getFinalPrepayTotal()}&nbsp;元</td>
                    </tr>
                    <tr>
                        <td class="cC_itemSplitSpace" colspan="4"></td>
                    </tr>
                    <c:forEach items="${statData.getMonthPrepayStatDataList()}" var="item">
                        <tr>
                            <td colspan="4">
                                <table class="cTbl_monthItem_d">
                                    <tr>
                                        <td name="monthTitle" class="cC_itemTitle <c:choose><c:when test="${item.getTotal()>0}">cTd_moneySurplus_t</c:when><c:otherwise>cTd_moneyDebt_t</c:otherwise></c:choose>" colspan="4">
                                                ${item.getMonth()}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getTotal()}&nbsp;元
                                        </td>
                                    </tr>
                                    <tr class="cTr_monthDetailData_c">
                                        <td colspan="4">
                                            <table class="cC_detailList">
                                                <c:forEach items="${item.getDetailList()}" var="record">
                                                    <tr>
                                                        <td>${record.getCreateDate()}</td>
                                                        <td <c:if test="${record.getMoneyType() == 'PAYBACK'}">class="cTd_payback_d"</c:if>>
                                                            ${record.getAmount()}&nbsp;元
                                                        </td>
                                                        <td>${record.getRemark()}</td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr class="cC_itemFooter">
                                        <td>收回:</td>
                                        <td>${item.getPaybackTotal()}&nbsp;元</td>
                                        <td>垫付:</td>
                                        <td>${item.getPrepayTotal()}&nbsp;元</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="cC_itemSplitSpace" colspan="4"></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
