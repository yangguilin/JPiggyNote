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
  <div>
    <input type="hidden" id="iIpt_accountTotalNum_h" value="${myAccountTotalNum}" />
  </div>
  <div class="cDiv_container">
    <div class="cDiv_top">
      <div class="cDiv_logo">
      </div>
    </div>
    <div class="cDiv_content">
      <div>
        <table class="cTbl_searchPanel">
          <tr>
            <td>
              <img src="/img/mp_search.png" class="cImg_search" onclick="showAddNewPanel()" />
              <img src="/img/mp_plus.png" class="cImg_plus" onclick="showSearchPanel()" />
            </td>
            <td>
              <input type="text" id="iIpt_searchContent" class="cIpt_mainInputBox" onkeydown="quickSearch(event)" />
              <input type="text" id="iIpt_addContentName" class="cIpt_mainInputBox" onkeydown="quickAddNewContent(event)"/>
            </td>
            <td>
              <a href="#" id="iA_search_b" class="cA_blockBtn">找找</a>
              <a href="#" id="iA_add_b" class="cA_blockBtn">添加</a>
            </td>
          </tr>
        </table>
      </div>
      <div id="iDiv_searchResult" class="cDiv_searchResult"></div>
      <div id="iDiv_addNewResultPanel">
        <table class='cTbl_newItem'>
          <tr id="iTr_newPasswordInfo">
            <td id="iTd_newItemShowName" class="cTd_bottomDottedSplit">微信</td>
            <td class="cTd_rightDottedSplit">
              <span id="iSpn_newAccountInfo" class="cSpan_tipWord" value="" onclick="curPage.showAccountEditor()">修改账号信息</span>
            </td>
            <td class="cTd_rightDottedSplit">
              <span id="iSpn_newPswInfo" class="cSpan_tipWord" value="" ondblclick="curPage.resetNewPswInfo(this)" onclick="curPage.showPasswordSelector()">修改密码组合</span>
            </td>
            <td class="cTd_bottomDottedSplit">
              <a href="#" class="firstButton blueButton" onclick="curPage.finishNewAccountInfo()">保存</a>
            </td>
          </tr>
        </table>
        <table class="cTbl_newItemResultMsg">
          <tr>
            <td>
              <span id="iSpan_newItemResultMsg"></span>
            </td>
          </tr>
        </table>
      </div>
      <div id="iDiv_editModulePanel">
        <table id="iTbl_AccountEditor" class="cTbl_operatePanel">
          <caption>账号信息</caption>
          <tr><td colspan="2"></td></tr>
          <tr>
            <td>已用提示：</td>
            <td style="margin-top: 5px;">
              <select id="iSel_existAccount" class="cSel_chooseList" onchange="curPage.changeAccountInfo()">
                <option value="new">新账号</option>
                <c:forEach items="${showAccountList}" var="item">
                  <option value="${item.getId()}">${item.getShowName()}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr tag="new_item">
            <td>账号提示：</td>
            <td>
              <input type="text" id="iIpt_newAccount" class="cIpt_newItem" placeholder="可选填" />
            </td>
          </tr>
          <tr tag="new_item">
            <td>账号原值：</td>
            <td>
              <input type="text" id="iIpt_newAccountOriginalVal" class="cIpt_newItem" />
            </td>
          </tr>
          <tr><td colspan="2"></td></tr>
          <tr>
            <td colspan="2" class="cTd_bottomRow">
              <a href="#" class="blueButton" onclick="curPage.hideAccountEditor()">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="#" class="firstButton blueButton" onclick="curPage.confirmNewAccount()">添加</a>
            </td>
          </tr>
        </table>
        <table id="iTbl_pswCombination" class="cTbl_operatePanel">
          <caption>密码组合</caption>
          <tr>
            <td class="cTd_firstTitleTd">
              <a href="javascript:;" class="firstButton grayButton" onclick="curPage.showNewPswPartEditor()">新密码块</a>
            </td>
            <td style="width:300px; padding: 10px 5px 10px 5px;">
              <c:forEach items="${showWordList}" var="item">
                <span onclick="curPage.addPasswordPart(this)" value="${item.getId()}" class="cSpan_tipWord">${item.getShowName()}</span>
              </c:forEach>
            </td>
          </tr>
        </table>
        <table id="iTbl_pswEditor" class="cTbl_operatePanel">
          <caption>新的密码块</caption>
          <tr>
            <td>密码块提示：</td>
            <td>
              <input type="text" id="iIpt_newPswPartText" class="cIpt_newItem" placeholder="可选填" />
            </td>
          </tr>
          <tr>
            <td>密码块原值：</td>
            <td>
              <input type="text" id="iIpt_newPswPartVal" class="cIpt_newItem" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="cTd_bottomRow">
              <a href="#" class="blueButton" onclick="curPage.hideNewPswPartEditor()">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="#" class="firstButton blueButton" onclick="curPage.confirmNewPswPart()">保存</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="cDiv_foot"></div>
  </div>
</body>
</html>
