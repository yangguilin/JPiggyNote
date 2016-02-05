
$(function(){
    curPage.init();
});

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
            searchResultHtml += "<td class='cTd_rightDottedSplit cTd_bottomDottedSplit'><span class='" + getSpanClass4tipWord(resultArr[index].accountTip) + "' value='" + resultArr[index].accountId + "' onclick='showContent(this)' type='account'>" + resultArr[index].accountTip + "</span></td>";
            var arr = resultArr[index].passwordTip.split(',');
            var valArr = resultArr[index].passwordIds.split(',');
            if (arr.length > 0 && valArr.length > 0 && valArr.length == arr.length){
                searchResultHtml += "<td class='" + dottedSplitClass + "'>";
                for (var i in arr) {
                    searchResultHtml += "<span class='" + getSpanClass4tipWord(arr[i]) + "' value='" + valArr[i] + "' onclick='showContent(this)' type='password'>" + arr[i] + "</span>";
                }
                searchResultHtml + "</td>";
            }
            searchResultHtml += "</tr>";
        }
        searchResultHtml += "</table>";
    }
    $("#iDiv_searchResult").empty().append($(searchResultHtml));

    function getSpanClass4tipWord(showName){
        return (showName == "点击查看" ? "cSpan_tipWord cSpn_customPart" : "cSpan_tipWord");
    }
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
            curPage.searchByShowName();
        }
    }
}

function quickAddNewContent(event){
    var theEvent = window.event || event;
    var code = theEvent.keyCode || theEvent.which;
    if ($("#txt_psw").val() != ""){
        if (code == 13){
            curPage.addNewAccount();
        }
    }
}

function showContent(obj){
    var type = $(obj).attr("type");
    var id = $(obj).attr("value");
    $.post(
        "/mp/show_content.do",
        { "val":id, "type":type },
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
    $(".cImg_search, #iIpt_searchContent, #iA_search_b").hide();
    $(".cImg_plus, #iIpt_addContentName, #iA_add_b").show();
    $("#iDiv_searchResult").hide();
}
function showSearchPanel(){
    clearInputControl();
    clearSearchResultControl();
    $(".cImg_plus, #iIpt_addContentName, #iA_add_b").hide();
    $(".cImg_search, #iIpt_searchContent, #iA_search_b").show();
    $("#iDiv_addNewResultPanel, #iDiv_editModulePanel").hide();
    $("#iDiv_searchResult").show();
}
function clearInputControl(){
    $("#iIpt_searchContent, #iIpt_addContentName").val("");
}
function clearSearchResultControl(){
    $("#iDiv_searchResult").empty();
}


var curPage = {
    init:function(){
        // 绑定事件
        $("#iA_search_b").click(curPage.searchByShowName);
        $("#iA_add_b").click(curPage.addNewAccount);
        $("#iIpt_addContentName").hide();
    },
    searchByShowName:function(){
        var showName = $.trim($("#iIpt_searchContent").val());
        if (showName == "") {
            $("#iDiv_searchResult").empty().append($("<table class='cTbl_searchResult'><tr><td colspan='2'>账号总数:&nbsp;" + $("#iIpt_accountTotalNum_h").val() + "</td></tr></table>"));
        } else {
            $.post("/mp/search.do", {"showName": showName},
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
    },
    addNewAccount:function(){
        $("#iDiv_searchResult").hide();
        var name = $.trim($("#iIpt_addContentName").val());
        if (name == "") return;
        $.post("/mp/account_show_name_exist.do", { "showName": name }, function(d){
            if (d && d.code != undefined && d.code == "1"){
                if (d.message){
                    $("#iDiv_addNewResultPanel, .cTbl_newItemResultMsg").show();
                    $("#iDiv_editModulePanel, .cTbl_newItem").hide();
                    $("#iSpan_newItemResultMsg").text("账号项名称已存在");
                } else {
                    $("#iIpt_addContentName").val("");
                    $("#iTr_newPasswordInfo td:first-child").text(name);
                    $("#iSpn_newAccountInfo").attr("value", "").text("修改账号信息");
                    $("#iSpn_newPswInfo").attr("value", "").text("修改密码组合");
                    $("#iDiv_addNewResultPanel, #iDiv_editModulePanel, .cTbl_newItem").show();
                    $("#iTbl_AccountEditor, #iTbl_pswCombination, #iTbl_pswEditor, .cTbl_newItemResultMsg").hide();
                }
            }
        });
    },
    showAccountEditor:function(){
        $("#iTbl_AccountEditor").show();
        $("#iSel_existAccount").change();
        curPage.hidePasswordSelector();
        $("#iSpn_newAccountInfo").addClass("cSpan_tipWordSelected");
        $("#iSpn_newPswInfo").removeClass("cSpan_tipWordSelected");
    },
    hideAccountEditor: function(){
        $("#iTbl_AccountEditor").hide();
        $("input.cIpt_newItem").val("");
        $("#iSpn_newAccountInfo").removeClass("cSpan_tipWordSelected");
    },
    showPasswordSelector: function(){
        $("#iTbl_pswCombination").show();
        $("#iTbl_pswEditor").hide();
        curPage.hideAccountEditor();
        $("#iSpn_newAccountInfo").removeClass("cSpan_tipWordSelected");
        $("#iSpn_newPswInfo").addClass("cSpan_tipWordSelected");
    },
    hidePasswordSelector: function(){
        $("#iTbl_pswCombination, #iTbl_pswEditor").hide();
        $("#iSpn_newPswInfo").removeClass("cSpan_tipWordSelected");
    },
    showNewPswPartEditor: function(){
        $("#iTbl_pswCombination").hide();
        $("#iTbl_pswEditor").show();

    },
    hideNewPswPartEditor: function(){
        $("#iTbl_pswEditor").hide();
        $("input.cIpt_newItem").val("");
    },
    changeAccountInfo:function(){
        if (curPage.pCheckNewAccountInfo()){
            $("#iTbl_AccountEditor tr[tag=new_item]").show();
        } else {
            $("#iTbl_AccountEditor tr[tag=new_item]").hide();
        }
    },
    addPasswordPart: function(obj){
        var $newPswInfo = $("#iSpn_newPswInfo");
        var partText = $(obj).text();
        var partVal = $(obj).attr("value");
        var curValues = $newPswInfo.attr("value");
        var curShowText = $newPswInfo.html();
        if (curValues == ""){
            curValues = partVal;
            curShowText = partText;
        } else {
            curValues += "," + partVal;
            curShowText += "&nbsp;|&nbsp;" + partText;
        }
        $newPswInfo.attr("value", curValues).html(curShowText);
    },
    resetNewPswInfo: function(obj){
        $(obj).attr("value", "").text("修改密码组合");
    },
    confirmNewAccount: function(){
        var accountInfo = "";
        var originalVal = "";
        if (curPage.pCheckNewAccountInfo()){
            accountInfo = $("#iIpt_newAccount").val();
            originalVal = $("#iIpt_newAccountOriginalVal").val();
            if (originalVal != ""){
                $.post("/mp/new_account.do", { "accountInfo": accountInfo, "originalVal": originalVal },
                function(d){
                    if (d && d.code != undefined && d.code == "1"){
                        var arr = d.message.split(",");
                        if (arr[0] != "") {
                            if ($("#iSel_existAccount option[value=" + arr[1] + "]").length == 0) {
                                $("#iSel_existAccount").append($("<option selected value='" + arr[1] + "'>" + arr[0] + "</option>"));
                            }
                        }
                        curPage.hideAccountEditor();
                        var showTip = arr[0] == "" ? originalVal : arr[0];
                        $("#iSpn_newAccountInfo").text(showTip).attr("value", arr[1]);
                    } else {
                        alert(d.message);
                    }
                });
            }
        } else {
            accountInfo = $("#iSel_existAccount option:selected").text()
            originalVal = $("#iSel_existAccount").val();
            curPage.hideAccountEditor();
            $("#iSpn_newAccountInfo").attr("value", originalVal).text(accountInfo);
        }
    },
    confirmNewPswPart: function(){
        var text = "", val = "";
        if (curPage.pCheckNewPswPart()){
            text = $("#iIpt_newPswPartText").val();
            val = $("#iIpt_newPswPartVal").val();
            if (val != "") {
                $.post("/mp/new_psw_part.do", {"text": text, "value": val}, function (d) {
                    if (d && d.code != undefined && d.code == "1") {
                        curPage.hideNewPswPartEditor();
                        var arr = d.message.split(",");
                        if (arr[0] != "") {
                            $("#iTbl_pswCombination tr:first td:nth-child(2)").append($('<span onclick="curPage.addPasswordPart(this)" value="' + arr[1] + '" class="cSpan_tipWord">' + arr[0] + '</span>'));
                            curPage.showPasswordSelector();
                        } else {
                            var $newPswInfo = $("#iSpn_newPswInfo");
                            var curPswText = $newPswInfo.html();
                            var curPswVal = $newPswInfo.attr("value");
                            var customPartHtml = "<span class='cSpn_customPart'>" + val + "</span>";
                            if (curPswVal == "") {
                                $newPswInfo.attr("value", arr[1]).html(customPartHtml).removeClass("cSpan_tipWordSelected");
                            } else {
                                var newPswVal = curPswVal + "," + arr[1];
                                var newPswText = curPswText + "&nbsp;|&nbsp;" + customPartHtml;
                                $newPswInfo.attr("value", newPswVal).html(newPswText);
                            }
                        }
                    } else {
                        alert(d.message);
                    }
                });
            }
        }
    },
    finishNewAccountInfo: function(){
        var accountId = "", pswIds = "", showName = "";
        showName = $("#iTd_newItemShowName").text();
        accountId = $("#iSpn_newAccountInfo").attr("value");
        pswIds = $("#iSpn_newPswInfo").attr("value");
        if (showName != "" && accountId != "" && pswIds != ""){
            $.post("/mp/finish_new_account.do", { "showName": showName, "accountId": accountId, "pswIds": pswIds },
            function(d){
                if (d && d.code != undefined && d.code == "1"){
                    $("#iIpt_addContentName").val("");
                    curPage.hidePasswordSelector();
                    $(".cTbl_newItem").hide();
                    $("#iSpan_newItemResultMsg").text("成功添加新的账号信息!");
                    $(".cTbl_newItemResultMsg").show();
                } else {
                    alert(d.message);
                }
            });
        } else {
            alert("账号/密码信息不完整");
        }
    },
    pCheckNewAccountInfo : function(){
        return ($("#iSel_existAccount option:selected").val() == "new");
    },
    pCheckNewPswPart: function(){
        return ($("#iIpt_newPswPartVal").val() != "")
    }
};

