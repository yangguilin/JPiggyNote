/**
 * Created by yanggavin on 15/5/12.
 */

$(document).ready(function(){
    pageInit();
});

function pageInit(){
    elementInit();
    jsEventInit();
}
function elementInit(){
    $("a").attr("href", "javascript:;");
}
function jsEventInit(){
    $(".cDiv_content_c .cA_menuItem_b").click(
        function(){
            var selectedClassName = "cA_menuItemSelected_b";
            if ($(this).hasClass(selectedClassName) == false){
                $(this).addClass(selectedClassName).siblings("a."+selectedClassName).removeClass(selectedClassName);
                var curPanelId = $(this).attr("panel_id");
                $("#" + curPanelId).siblings("div.cDiv_list_mk").hide().end().show("normal");
            }
        }
    );
    $(".cTr_itemTitle").click(
        function(){
            var $detailObj = $(this).siblings(".cTr_itemDetail_c");
            if ($detailObj.is(":visible")){
                $detailObj.hide();
            } else {
                $(".cTr_itemDetail_c").hide();
                $detailObj.show();
            }
        }
    );
}

function submitNewInvestmentForm(){
    var name = $("#iIpt_investmentName4New").val();
    var principalNum = $("#iIpt_investmentPrincipalNum4New").val();
    var desc = $("#iTxt_investmentDesc4New").val();
    if (checkFormData4Investment()){
        $.post("/investment/add.do",
            {"name":name, "principalNum":principalNum, "description":desc},
            function(data){
                if (data == "success"){
                    reloadCurrentPage();
                } else {
                    alert("新建投资项失败");
                }
            }
        )
    } else {
        alert("内容输入有误");
    }

    function checkFormData4Investment(){
        return (name != "" && principalNum.isFloatPointNumber());
    }
}

function resetNewInvestmentForm(){
    $("#iIpt_investmentName4New").val("");
    $("#iIpt_investmentPrincipalNum4New").val("");
    $("#iTxt_investmentDesc4New").val("");
}

function addRecord4InvestmentItem(obj){
    var $curValObj = $(obj).siblings(".cIpt_newItemVal");
    var id = $curValObj.attr("investment_id");
    var val = $curValObj.val();
    if (checkFormData4InvestmentRecord()){
        $.post("/investment/add_record.do",
            { "id":id, "num":val },
            function(data){
                if (data == "success"){
                    reloadCurrentPage();
                } else {
                    alert("添加记录失败");
                }
            }
        );
    }

    function checkFormData4InvestmentRecord(){
        return (id != "" && val.isFloatPointNumber());
    }
}

function finishCurInvestmentItem(obj){
    var id = $(obj).siblings(".cIpt_newItemVal").attr("investment_id");
    if (id != "" && confirm("该项投资已完成？")){
        $.post("/investment/finish_record.do",
            { "id":id },
            function(data){
                if (data == "success"){
                    reloadCurrentPage();
                } else {
                    alert("操作失败");
                }
            }
        );
    }
}

function deleteFinishInvestmentItem(obj){
    var id = $(obj).attr("investment_id");
    if (id != "" && confirm("确认删除该条记录？")){
        $.post("/investment/delete_record.do",
            {"id":id},
            function(data){
                if (data == "success"){
                    reloadCurrentPage();
                } else {
                    alert("删除失败");
                }
            }
        );
    }
}