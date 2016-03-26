<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 16/3/22
  Time: 上午11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>托福听力关键词文章检索</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/dingup/search_listening_article.js"></script>
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/dingup/search_listening_article.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div>
        <div>
            <input id="iIpt_curSearchedWord" type="hidden" value="${searchWord}" />
        </div>
        <div style="margin-top: 15px;">
            <span>听力原文总数:</span>&nbsp;&nbsp;<span class="cSpan_blue">${totalNum}</span>
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <input type="text" id="iIpt_searchedWord" placeholder="输入查询单词" class="cIpt_searchedWord" onkeydown="eventHandler.quickSearchWord()" />
            <a href="javascript:;" class="firstButton grayButton" onclick="eventHandler.searchWord()">查询</a>
        </div>
        <div class="cDiv_split"></div>
        <div class="cDiv_checkWordBox">
            <div>
                <c:choose>
                    <c:when test="${resultList != null && resultList.size() > 0}">
                        <h3>搜索匹配结果&nbsp;&nbsp;(&nbsp;${searchWord}&nbsp;|&nbsp;${resultList.size()}&nbsp;)</h3>
                    </c:when>
                    <c:otherwise>
                        <h4>匹配不到结果(&nbsp;${searchWord}&nbsp;)</h4>
                    </c:otherwise>
                </c:choose>
                <table class="cTbl_wordTranItem">
                    <tr class="cTr_title">
                        <td width="40px">序号</td>
                        <td width="80px">套题名称</td>
                        <td width="100px">类型</td>
                        <td width="50px">序号</td>
                        <td>标题</td>
                    </tr>
                    <c:set var="index" value="1" scope="page"/>
                    <c:forEach var="item" items="${resultList}">
                        <tr class="cTr_data" article_id="${item.getId()}" onclick="eventHandler.showArticleContent(this)">
                            <td>${index}</td>
                            <td>${item.getSubjectName()}</td>
                            <td>${item.getArticleType()}</td>
                            <td>${item.getArticleNum()}</td>
                            <td>${item.getArticleTitle()}</td>
                        </tr>
                        <c:set value="${index + 1}" var="index" />
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="cDiv_selectTranBox">
            <div id="iDiv_articleContent"></div>
        </div>
    </div>
</body>
</html>
