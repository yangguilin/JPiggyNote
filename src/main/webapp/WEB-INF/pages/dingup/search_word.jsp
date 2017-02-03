<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 16/4/20
  Time: 下午5:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>柯林斯单词搜索</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/dingup/search_word.js"></script>
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/dingup/search_word.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
    <div>
        <input id="iIpt_id" type="hidden" value="${wtb.getId()}" />
        <input id="iIpt_wordName" type="hidden" value="${wtb.getWordName()}" />
    </div>
    <div style="margin-top: 15px; text-align: center;">
        <input type="text" id="iIpt_searchedWord" placeholder="输入查询单词" class="cIpt_searchedWord" onkeydown="eventHandler.quickSearchWord()" />
        <a href="javascript:;" class="firstButton grayButton" onclick="eventHandler.searchWord(this)">查询</a>
    </div>
    <div class="cDiv_split"></div>
    <div class="cDiv_selectTranBox">
        <c:choose>
            <c:when test="${collinsSenseList != null && collinsSenseList.size() > 0}">
                <h3>${searchWord}&nbsp;&nbsp;|&nbsp;&nbsp;柯林斯匹配结果&nbsp;&nbsp;(&nbsp;${collinsSenseList.size()}&nbsp;)</h3>
            </c:when>
            <c:otherwise>
                <h4>${searchWord}&nbsp;&nbsp;|&nbsp;&nbsp;柯林斯匹配不到结果</h4>
            </c:otherwise>
        </c:choose>
        <c:forEach var="item" items="${collinsSenseList}">
            <div>
                <table class="cTbl_wordTranItem">
                    <tr>
                        <td class="cTd_title" posp='${item.getPosp()}'>
                            <span>${item.getId()}&nbsp;&nbsp;|&nbsp;&nbsp;${item.getPosp()}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="cTd_def">${item.getDef()}</td>
                    </tr>
                    <tr>
                        <td class="cTd_tran">${item.getTran()}</td>
                    </tr>
                    <c:if test="${item.getExamples().size() > 0}">
                        <tr>
                            <td class="cTd_example_ex">${item.getExamples().get(0).getEx()}</td>
                        </tr>
                        <tr>
                            <td class="cTd_example_tran">${item.getExamples().get(0).getTran()}</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>

