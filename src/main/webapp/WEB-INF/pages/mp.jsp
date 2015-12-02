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
    <div class="cDiv_top">
      <div class="cDiv_logo">
      </div>
    </div>
    <div class="cDiv_content">
      <div>
        <table class="cTbl_searchPanel">
          <tr>
            <td>
              <img src="/img/mp_plus.png" class="cImg_plus" onclick="showAddNewPanel()" />
              <img src="/img/mp_search.png" class="cImg_search" onclick="showSearchPanel()" />
            </td>
            <td>
              <input type="text" id="iIpt_searchContent" class="cIpt_mainInputBox" />
              <input type="text" id="iIpt_addContentName" class="cIpt_mainInputBox" />
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
            <td class="cTd_bottomDottedSplit">微信</td>
            <td class="cTd_rightDottedSplit">
              <span class="cSpan_tipWord">修改账号信息</span>
            </td>
            <td class="cTd_rightDottedSplit">
              <span class="cSpan_tipWord">修改密码组合</span>
            </td>
            <td class="cTd_bottomDottedSplit">
              <a href="#" class="firstButton blueButton">保存</a>
            </td>
          </tr>
        </table>
      </div>
      <div id="iDiv_editModulePanel">
        <table class="cTbl_operatePanel">
          <caption>账号信息</caption>
          <tr>
            <td>已用提示：</td>
            <td>
              <select class="cSel_chooseList">
                <option>快乐17</option>
                <option>gmail邮箱</option>
                <option>hotmail邮箱</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>账号提示：</td>
            <td>
              <input type="text" class="cIpt_newItem" />
            </td>
          </tr>
          <tr>
            <td>账号原值：</td>
            <td>
              <input type="text" class="cIpt_newItem" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="cTd_bottomRow">
              <a href="#" class="firstButton blueButton">添加</a>
            </td>
          </tr>
        </table>
        <table class="cTbl_operatePanel">
          <caption>密码组合</caption>
          <tr>
            <td class="cTd_firstTitleTd">常用提示词：</td>
            <td style="width:300px;">
              <span class="cSpan_tipWord">数字</span>
              <span class="cSpan_tipWord">姓名</span>
              <span class="cSpan_tipWord">一百万</span>
              <span class="cSpan_tipWord">分隔符</span>
              <span class="cSpan_tipWord">分隔符分隔符</span>
              <span class="cSpan_tipWord">分隔符aaaaa</span>
              <span class="cSpan_tipWord">好的</span>
              <span class="cSpan_tipWord">分隔符aaaaa</span>
            </td>
          </tr>
        </table>
        <table class="cTbl_operatePanel">
          <caption>新的密码块</caption>
          <tr>
            <td>密码块提示：</td>
            <td>
              <input type="text" class="cIpt_newItem" />
            </td>
          </tr>
          <tr>
            <td>密码块原值：</td>
            <td>
              <input type="text" class="cIpt_newItem" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="cTd_bottomRow">
              <a href="#" class="firstButton blueButton">保存</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="cDiv_foot"></div>
  </div>
</body>
</html>
