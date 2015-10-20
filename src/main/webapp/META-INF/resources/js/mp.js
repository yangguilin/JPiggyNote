
function searchShowName(){
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
            var arr = resultArr[index].passwordTip.split(',');
            if (arr.length > 0){
                searchResultHtml += "<td class='" + dottedSplitClass + "'>";
                for (var i in arr) {
                    searchResultHtml += "<span>" + arr[i] + "</span>";
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
        return "cTd_dottedSplit";
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