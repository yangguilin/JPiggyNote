/**
 * Created by guilin on 2014/8/1.
 */

function addCustomConfig(){

    var userName = "yanggl2";
    var monthCostPlan = 5000;

    // 调用ajax请求
    $.post("../custom_config/add", { "userName": userName , "monthCostPlan":monthCostPlan },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function updateCustomConfig(){

    var userName = "yanggl2";
    var monthCostPlan = 5001;

    // 调用ajax请求
    $.post("../custom_config/update", { "userName": userName , "monthCostPlan":monthCostPlan },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function resetCustomConfig(){

    var userName = "yanggl2";

    // 调用ajax请求
    $.post("../custom_config/reset", { "userName": userName },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function addRecord(){

    var userName = "yanggl2";
    var moneyType = "COST";
    var statType = "NORMAL";
    var categoryId = "1";
    var categoryName = "食物";
    var amount = 100;
    var remark = "test";

    // 调用ajax请求
    $.post("../daily_record/add", { "userName": userName , "moneyType":moneyType, "statType":statType,
            "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function addRecord2(){

    var userName = "yanggl2";
    var moneyType = "COST";
    var statType = "NORMAL";
    var categoryId = "1";
    var categoryName = encodeURI("食物");
    var amount = 100;
    var remark = "test";

    // 调用ajax请求
    $.post("../daily_record/add", { "userName": userName , "moneyType":moneyType, "statType":statType,
            "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function updateRecord(){

    var userName = "yanggl2";
    var moneyType = "0";
    var statType = "0";
    var categoryId = "1";
    var categoryName = "食物";
    var amount = 101;
    var remark = "test update";

    // 调用ajax请求
    $.post("../daily_record/update", { "userName": userName , "moneyType":moneyType, "statType":statType, "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function deleteRecord(){

    var userName = "yanggl2";
    var id = "";

    // 调用ajax请求
    $.post("../daily_record/delete", { "userName": userName, "id":id },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}
