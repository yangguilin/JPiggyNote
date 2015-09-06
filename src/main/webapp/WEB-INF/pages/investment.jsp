<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 15/5/12
  Time: AM11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />

  <title>投资理财</title>
  <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="/js/common.js"></script>
  <script type="text/javascript" src="/js/investment.js"></script>
  <link href="/css/investment.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div>
  </div>
  <div class="cDiv_main_c">
    <div class="cDiv_top_m">
      <a href="#" class="cA_menuItem_b" onclick="window.location.href='/'">首页</a>
      &nbsp;/&nbsp;
      <a href="#" class="cA_menuItem_b" onclick="window.location.href='/stat'">统计</a>
      &nbsp;/&nbsp;
      <a href="#" class="cA_menuItem_b cA_menuItemSelected_b" onclick="">理财</a>
      &nbsp;/&nbsp;
      <a href="#" class="cA_menuItem_b" onclick="window.location.href='/custom_config'">设置</a>
    </div>
    <div class="cDiv_content_c">
      <div>
        <a href="#" id="iA_currentInvestmentMenu_b" panel_id="iDiv_currentInvestmentPanel_c" class="cA_menuItem_b cA_menuItemSelected_b">当前项</a>
        &nbsp;/&nbsp;
        <a href="#" id="iA_finishedInvestmentMenu_b" panel_id="iDiv_finishedInvestmentPanel_c" class="cA_menuItem_b">已结束</a>
        &nbsp;/&nbsp;
        <a href="#" id="iA_newInvestmentMenu_b" panel_id="iDiv_newInvestmentPanel_c" class="cA_menuItem_b">新项目</a>
      </div>
      <div id="iDiv_currentInvestmentPanel_c" class="cDiv_list_mk">
        <c:set value="${statData.getCurrentInvestmentSurplusTotalNum() > 0}" var="isSurplusStatus4Current" scope="page"></c:set>
        <table>
          <tr class="cTr_totalTitle <c:choose><c:when test="${isSurplusStatus4Current}">cTr_green</c:when><c:otherwise>cTr_gray</c:otherwise></c:choose>">
            <td colspan="4">
              当前投资合计&nbsp;&nbsp;|&nbsp;&nbsp;${statData.getCurrentInvestmentTotalNum()}&nbsp;元
            </td>
          </tr>
          <tr class="<c:choose><c:when test="${isSurplusStatus4Current}">cTr_totalGreenSplit</c:when><c:otherwise>cTr_totalGraySplit</c:otherwise></c:choose>">
            <td colspan="4"></td>
          </tr>
          <tr class="cTr_totalFooter <c:choose><c:when test="${isSurplusStatus4Current}">cTr_green</c:when><c:otherwise>cTr_gray</c:otherwise></c:choose>">
            <td>本金:</td>
            <td>${statData.getCurrentInvestmentPrincipalTotalNum()}&nbsp;元</td>
            <td>收益:</td>
            <td>${statData.getCurrentInvestmentSurplusTotalNum()}&nbsp;元</td>
          </tr>
          <tr class="cTr_splitSpace">
            <td colspan="4"></td>
          </tr>
          <c:forEach items="${statData.getCurrentInvestmentList()}" var="item">
            <tr>
              <td colspan="4">
                <table>
                  <tr class="cTr_itemTitle <c:choose><c:when test="${(item.getCurrentNum() - item.getPrincipalNum()) > 0}">cTr_greenBg</c:when><c:otherwise>cTr_grayBg</c:otherwise></c:choose>">
                    <td colspan="4">
                      ${item.getName()}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getCurrentNum()}&nbsp;元
                    </td>
                  </tr>
                  <tr class="cTr_itemDetail_c">
                    <td colspan="4">
                      <table class="cTbl_itemDetail_d">
                        <c:forEach items="${item.getRecords()}" var="record">
                          <tr>
                            <td>${record.getDate()}</td>
                            <td>${record.getNum()}&nbsp;元</td>
                          </tr>
                        </c:forEach>
                        <tr class="cTr_detailProcPanel">
                          <td colspan="2">
                            <input type="text" investment_id="${item.getId()}" class="cIpt_newItemVal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="cA_smallBtn" href="javascript:;" onclick="addRecord4InvestmentItem(this)">[添加]</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="cA_smallBtn" href="javascript:;" onclick="finishCurInvestmentItem(this)">[完成]</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr class="cTr_itemFooter">
                    <td>本金:</td>
                    <td>${item.getPrincipalNum()}&nbsp;元</td>
                    <td>收益:</td>
                    <td>${item.getCurrentNum() - item.getPrincipalNum()}&nbsp;元</td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="cTr_splitSpace">
              <td colspan="4"></td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="iDiv_finishedInvestmentPanel_c" class="cDiv_list_mk">
        <c:set value="${statData.getFinishedInvestmentTotalSurplusTotalNum() > 0}" var="isSurplusStatus4Finish" scope="page"></c:set>
        <table>
          <tr class="cTr_totalTitle <c:choose><c:when test="${isSurplusStatus4Finish}">cTr_green</c:when><c:otherwise>cTr_gray</c:otherwise></c:choose>">
            <td colspan="4">
              历史投资收益&nbsp;&nbsp;|&nbsp;&nbsp;${statData.getFinishedInvestmentTotalSurplusTotalNum()}&nbsp;元
            </td>
          </tr>
          <tr class="<c:choose><c:when test="${isSurplusStatus4Finish}">cTr_totalGreenSplit</c:when><c:otherwise>cTr_totalGraySplit</c:otherwise></c:choose>">
            <td colspan="4"></td>
          </tr>
          <tr class="cTr_splitSpace">
            <td colspan="4"></td>
          </tr>
          <c:forEach items="${statData.getFinishedInvestmentList()}" var="item">
            <tr>
              <td colspan="4">
                <table>
                  <tr class="cTr_itemTitle <c:choose><c:when test="${(item.getCurrentNum() - item.getPrincipalNum()) > 0}">cTr_greenBg</c:when><c:otherwise>cTr_grayBg</c:otherwise></c:choose>">
                    <td colspan="4">
                        ${item.getName()}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getCurrentNum()}&nbsp;元
                    </td>
                  </tr>
                  <tr class="cTr_itemDetail_c">
                    <td colspan="4">
                      <table class="cTbl_itemDetail_d">
                        <c:forEach items="${item.getRecords()}" var="record">
                          <tr>
                            <td>${record.getDate()}</td>
                            <td>${record.getNum()}&nbsp;元</td>
                          </tr>
                        </c:forEach>
                        <tr class="cTr_detailProcPanel">
                          <td colspan="2">
                            <a class="cA_smallBtn" investment_id="${item.getId()}" href="javascript:;" onclick="deleteFinishInvestmentItem(this)">[完成]</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr class="cTr_itemFooter">
                    <td>本金:</td>
                    <td>${item.getPrincipalNum()}&nbsp;元</td>
                    <td>收益:</td>
                    <td>${item.getCurrentNum() - item.getPrincipalNum()}&nbsp;元</td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="cTr_splitSpace">
              <td colspan="4"></td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="iDiv_newInvestmentPanel_c" class="cDiv_list_mk">
        <table>
          <tr>
            <td colspan="2"></td>
          </tr>
          <tr class="cTr_formItem">
            <td>投资名称:</td>
            <td>
              <input type="text" id="iIpt_investmentName4New" />
            </td>
          </tr>
          <tr class="cTr_formItem">
            <td>投入本金:</td>
            <td>
              <input type="text" id="iIpt_investmentPrincipalNum4New" />
            </td>
          </tr>
          <tr class="cTr_formItem">
            <td>内容描述:</td>
            <td>
              <textarea rows="2" id="iTxt_investmentDesc4New"></textarea>
            </td>
          </tr>
          <tr class="cTr_formButton">
            <td colspan="2">
              <a class="grayButton firstButton" onclick="resetNewInvestmentForm()">重置</a>
              <a class="grayButton" onclick="submitNewInvestmentForm()">确定</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</body>
</html>
