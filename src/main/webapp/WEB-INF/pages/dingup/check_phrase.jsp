<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 16/3/3
  Time: 上午12:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.ygl.piggynote.enums.dingup.WordTranExamTypeEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>托福常用短语人工审核</title>
    <script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/dingup/check_phrase.js"></script>
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/dingup/check_phrase.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
    <div>
        <input id="iIpt_id" type="hidden" value="${cptb.getId()}" />
        <input id="iIpt_wordName" type="hidden" value="${cptb.getPhrase()}" />
    </div>
    <div style="margin-top: 15px;">
        <span>录入短语总量:</span>&nbsp;&nbsp;<span class="cSpan_blue">${total}</span>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <span>已精校完成:</span>&nbsp;&nbsp;<span class="cSpan_green">${passed}</span>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <input type="text" id="iIpt_searchedWord" placeholder="输入查询短语" class="cIpt_searchedWord" />
        <a href="javascript:;" class="firstButton grayButton" onclick="eventHandler.searchWord(this)">查询</a>
    </div>
    <div class="cDiv_split"></div>
    <div class="cDiv_checkWordBox">
        <div class="cDiv_menuBar">
            <a href="#" class="grayButton firstButton" onclick="eventHandler.showLatestUpdateWord()">上一个</a>
            <a href="#" class="grayButton" onclick="eventHandler.updateRecordTime()">下一个</a>
            <a href="#" class="grayButton" onclick="eventHandler.updateWordContent()">仅保存</a>
            <a href="#" class="blueButton" onclick="eventHandler.updateWordContent('PASSED')">精校通过</a>
        </div>
        <table class="cTbl_currentWord">
            <caption style="height:40px;">
                <span style="cursor: pointer;" title="点击修改" onclick="eventHandler.showModifyWordNameBar(this)">${cptb.getPhrase()}</span>
                <div class="cDiv_hidden">
                    <input id="iIpt_newWordName" type="text" value="${cptb.getPhrase()}" class="cIpt_small" />
                    <a href="javascript:;" class="firstButton grayButton" onclick="eventHandler.modifyWordName(this)">修改短语</a>
                    <a href="javascript:;" class="grayButton" onclick="eventHandler.resetWordName(this)">取消</a>
                </div>
            </caption>
            <tr>
                <td>
                    <span style="color:darkgray !important;">原始中文词义:</span>
                </td>
                <td>
                    <input id="iIpt_originChMeaning" type="text" value="${cptb.getOriginChMeaning()}" class="cIpt_short" >
                </td>
            </tr>
            <tr>
                <td colspan="2" style="height:20px;"></td>
            </tr>
            <tr>
                <td>
                    <span>考试类型:</span>
                </td>
                <td>
                    <select id="iSel_examType" class="cIpt_short">
                        <option value="LISTENING" <c:if test="${cptb.getExamType() == WordTranExamTypeEnum.LISTENING}">selected</c:if> >听力</option>
                        <option value="SPEAKING" <c:if test="${cptb.getExamType() == WordTranExamTypeEnum.SPEAKING}">selected</c:if> >口语</option>
                        <option value="READING" <c:if test="${cptb.getExamType() == WordTranExamTypeEnum.READING}">selected</c:if> >阅读</option>
                        <option value="WRITING" <c:if test="${cptb.getExamType() == WordTranExamTypeEnum.WRITING}">selected</c:if> >写作</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <span>二级类型:</span>
                </td>
                <td>
                    <input id="iIpt_examSubType" type="text" value="${cptb.getExamSubType()}" class="cIpt_short" >
                </td>
            </tr>
            <tr>
                <td>
                    <span>三级类型:</span>
                </td>
                <td>
                    <input id="iIpt_examThirdType" type="text" value="${cptb.getExamThirdType()}" class="cIpt_short" >
                </td>
            </tr>
            <tr>
                <td>
                    <span>柯林斯中文词义:</span>
                </td>
                <td>
                    <input id="iIpt_clsChMeaning" type="text" value="${cptb.getClsChMeaning()}" class="cIpt_normal" >
                </td>
            </tr>
            <tr>
                <td>
                    <span>柯林斯英文词义:</span>
                </td>
                <td>
                    <textarea id="iTxt_clsEnMeaning" type="text" class="cIpt_long">${cptb.getClsEnMeaning()}</textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <span>柯林斯英文例句:</span>
                </td>
                <td>
                    <textarea id="iTxt_clsEnEx" type="text" cols="3" wrap="soft" class="cIpt_long">${cptb.getClsEnEx()}</textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <span>英文例句翻译:</span>
                </td>
                <td>
                    <textarea id="iTxt_clsChEx" type="text" cols="3" wrap="hard" class="cIpt_long">${cptb.getClsEnExTran()}</textarea>
                </td>
            </tr>
        </table>
    </div>
    <div class="cDiv_selectTranBox">
        <c:choose>
            <c:when test="${collinsPhvbList != null && collinsPhvbList.size() > 0}">
                <h3>${cptb.getPhrase()}&nbsp;&nbsp;|&nbsp;&nbsp;柯林斯匹配结果&nbsp;&nbsp;(&nbsp;${collinsPhvbList.size()}&nbsp;)</h3>
            </c:when>
            <c:otherwise>
                <h4>${cptb.getPhrase()}&nbsp;&nbsp;|&nbsp;&nbsp;柯林斯匹配不到结果</h4>
            </c:otherwise>
        </c:choose>
        <c:forEach var="item" items="${collinsPhvbList}">
            <div>
                <table class="cTbl_wordTranItem" onclick="eventHandler.fillDataToLeftForm(this)">
                    <tr>
                        <td class="cTd_title" posp='${item.getHeadWord().getWord()}'>
                            <span>${item.getHeadWord().getWord()}</span>
                        </td>
                    </tr>
                    <c:if test="${item.getSenseList() != null && item.getSenseList().size() > 0}">
                        <tr>
                            <td class="cTd_def">${item.getSenseList().get(0).getDef()}</td>
                        </tr>
                        <tr>
                            <td class="cTd_tran">${item.getSenseList().get(0).getTran()}</td>
                        </tr>
                        <c:if test="${item.getSenseList().get(0).getExamples().size() > 0}">
                            <tr>
                                <td class="cTd_example_ex">${item.getSenseList().get(0).getExamples().get(0).getEx()}</td>
                            </tr>
                            <tr>
                                <td class="cTd_example_tran">${item.getSenseList().get(0).getExamples().get(0).getTran()}</td>
                            </tr>
                        </c:if>
                    </c:if>
                </table>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
