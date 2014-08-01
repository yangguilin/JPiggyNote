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