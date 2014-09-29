/**
 * Created by guilin on 2014/8/11.
 */


/**
 * 更新设置信息
 */
function update(){

    var userName = $("#hidden_curUserName").val();
    var monthCostPlan = $("#input_month_cost_plan").val();
//    var categorySwitch = $("#select_category_switch").val();
//    var prepaySwitch = $("#select_prepay_switch").val();
    var remarkAmount = $("#input_remark_show_amount").val();
    var categorySwitch = "0";
    var prepaySwitch = "0";

    // 如果数额输入框为空，则默认赋值为0
    if (monthCostPlan == ""){
        monthCostPlan = 0;
    }
    if (remarkAmount == ""){
        remarkAmount = 0;
    }

    // 调用ajax请求
    $.post("/custom_config/update.do", { "userName": userName , "monthCostPlan":monthCostPlan, "categorySwitch":categorySwitch, "prepaySwitch":prepaySwitch, "remarkAmount":remarkAmount },
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