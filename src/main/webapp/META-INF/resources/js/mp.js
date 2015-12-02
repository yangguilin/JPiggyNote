
$(function(){
    curPage.init();
});


function searchByShowName(){
    var showName = $("#iIpt_searchContent").val();
    if (showName == "") return;

    $.post(
        "/mp/search.do",
        { "userId":"112", "showName":showName },
        function (data) {
            if (data.code == "1") {
                var resultArr = data.message;
                updateSearchResult(resultArr);
            } else {
                updateSearchResult(null);
            }
        }
    );
}

function updateSearchResult(resultArr){
    var searchResultHtml = "";
    if (resultArr == null || resultArr.length == 0){
        searchResultHtml = "<table class='cTbl_searchResult'><tr><td colspan='2'>啥都没找到</td></tr>";
    } else {
        var rowNum = resultArr.length;
        var curNum = 0;
        searchResultHtml = "<table class='cTbl_searchResult'>";
        for (var index in resultArr){
            curNum++;
            var dottedSplitClass = getDottedSplitClass(curNum, rowNum);
            searchResultHtml += "<tr><td class='" + dottedSplitClass + "'>" + resultArr[index].showName + "</td>";
            searchResultHtml += "<td class='cTd_rightDottedSplit cTd_bottomDottedSplit'><span class='cSpan_tipWord' onclick='showContent(this)' type='account'>" + resultArr[index].accountTip + "</span></td>";
            var arr = resultArr[index].passwordTip.split(',');
            if (arr.length > 0){
                searchResultHtml += "<td class='" + dottedSplitClass + "'>";
                for (var i in arr) {
                    searchResultHtml += "<span class='cSpan_tipWord' onclick='showContent(this)' type='password'>" + arr[i] + "</span>";
                }
                searchResultHtml + "</td>";
            }
            searchResultHtml += "</tr>";
        }
        searchResultHtml += "</table>";
    }
    $("#iDiv_searchResult").empty().append($(searchResultHtml));
}

function getDottedSplitClass(curNum, rowNum){
    if (rowNum > 1 && curNum < rowNum){
        return "cTd_bottomDottedSplit";
    } else {
        return "";
    }
}

function quickSearch(event){
    var theEvent = window.event || event;
    var code = theEvent.keyCode || theEvent.which;
    if ($("#txt_psw").val() != ""){
        if (code == 13){
            searchShowName();
        }
    }
}

function quickAddNewContent(){
}

function showContent(obj){
    var type = $(obj).attr("type");
    var showName = $(obj).text();
    $.post(
        "/mp/show_content.do",
        { "showName":showName, "type":type },
        function (data){
            if (data.code == "1"){
                showWordPartContent(obj, data);
            } else {
                alert("获取原值失败");
            }
        }
    );
}

function showWordPartContent(obj, jsonData){
    var showName = $(obj).text();
    var content = jsonData.message;
    $(obj).toggleClass("cSpan_showContent").text(content);
    setTimeout(function(){
        $(obj).toggleClass("cSpan_showContent").text(showName);
    }, 3000);
}

function showAddNewPanel(){
    clearInputControl();
    clearSearchResultControl();
    $(".cImg_plus, #iIpt_searchContent, .cA_searchButton_b").hide();
    $(".cImg_search, #iIpt_addContent, .cA_addButton_b").show();
    $("#iDiv_searchResult").hide();
}
function showSearchPanel(){
    clearInputControl();
    $(".cImg_search, #iIpt_addContent, .cA_addButton_b").hide();
    $(".cImg_plus, #iIpt_searchContent, .cA_searchButton_b").show();
    $("#iDiv_addNewResultPanel, #iDiv_editModulePanel").hide();
    $("#iDiv_searchResult").show();
}
function clearInputControl(){
    $("#iIpt_searchContent, #iIpt_addContent").val("");
}
function clearSearchResultControl(){
    $("#iDiv_searchResult").empty();
}


var Page = {
    init:function(){
        var page = Page;
        // 绑定事件
        $("#iA_search_b").click(page.searchByShowName);
        $("#iA_add_b").click(page.addNewPassword);
        $("#iIpt_searchContent").keydown(page.searchByShowName);
        $("#iIpt_addContentName").keydown(page.addNewPassword);
    },
    searchByShowName:function(){
        var showName = $.trim($("#iIpt_searchContent").val());
        if (showName == "") return;
        $.post("/mp/search.do", { "userId":"112", "showName":showName },
            function (data) {
                if (data.code == "1") {
                    var resultArr = data.message;
                    updateSearchResult(resultArr);
                } else {
                    updateSearchResult(null);
                }
            }
        );
    },
    addNewPassword:function(){
        $("#iDiv_searchResult").hide();
        var name = $.trim($("#iIpt_addContentName").val());
        if (name == "") return;
        $("#iTr_newPasswordInfo td:first-child").text(name);
        $("#iTr_newPasswordInfo td:nth-child(2) span").text("修改账号信息");
        $("#iTr_newPasswordInfo td:nth-child(3) span").text("修改密码组合");
        $("#iDiv_addNewResultPanel").show();
    }
};

