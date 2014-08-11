/**
 * Created by guilin on 2014/8/11.
 */


/**
 * 更新设置信息
 */
function update(){

    var userName = $("#hidden_curUserName").val();
    var monthCostPlan = $("#input_month_cost_plan").val();
    var categorySwitch = $("#select_category_switch").val();
    var prepaySwitch = $("#select_prepay_switch").val();

    // 调用ajax请求
    $.post("/custom_config/update.do", { "userName": userName , "monthCostPlan":monthCostPlan, "categorySwitch":categorySwitch, "prepaySwitch":prepaySwitch },
        function (data) {

            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

/**
 * 返回主菜单
 */
function backToMenu(){

    window.location.href = "/";
}