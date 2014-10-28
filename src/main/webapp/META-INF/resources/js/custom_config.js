/**
 * Created by guilin on 2014/8/11.
 */

/**
 * 页面载入后默认事件
 */
$(document).ready(
    function(){
        check();
    }
);


/**
 * 更新设置信息
 */
function update(){

    var userName = $("#hidden_curUserName").val();
    var monthCostPlan = $("#input_month_cost_plan").val();
    var remarkAmount = $("#input_remark_show_amount").val();
    var categorySwitch = "0";
    var prepaySwitch = "0";

    // 如果数额输入框为空，则默认赋值为原值
    if (monthCostPlan == ""){
        monthCostPlan = $("#iIpt_monthCostPlan_h").val();
    }
    if (remarkAmount == ""){
        remarkAmount = $("#iIpt_remarkAmount_h").val();
    }

    // 调用ajax请求
    $.post("/custom_config/update.do", { "userName": userName , "monthCostPlan":monthCostPlan, "categorySwitch":categorySwitch, "prepaySwitch":prepaySwitch, "remarkAmount":remarkAmount },
        function (data) {

            if (data == "success") {

                // 更新页面隐藏值
                $("#iIpt_monthCostPlan_h").val(monthCostPlan);
                $("#iIpt_remarkAmount_h").val(remarkAmount);
                $("#input_month_cost_plan").attr("placeholder", monthCostPlan).val("");
                $("#input_remark_show_amount").attr("placeholder", remarkAmount).val("");

                // 更新按钮状态
                check();
            } else {
                alert("操作失败！" + data);
            }
        });
}

/**
 * 检查数值格式，更新按钮显示状态
 */
function check(){

    var $monthCostPlanObj = $("#input_month_cost_plan");
    var $remarkAmountObj = $("#input_remark_show_amount");
    var $updateBtnObj = $("#iA_update_b");
    var oldMonthCostPlan = $("#iIpt_monthCostPlan_h").val();
    var oldRemarkAmount = $("#iIpt_remarkAmount_h").val();
    var newMonthCostPlan = $monthCostPlanObj.val();
    var newRemarkAmount = $remarkAmountObj.val();
    var canUpdate = true;

    // 默认值
    if (newMonthCostPlan == ""){
        newMonthCostPlan = oldMonthCostPlan;
    }
    if (newRemarkAmount == ""){
        newRemarkAmount = oldRemarkAmount;
    }

    // 月消费计划
    if (!checkAmountVal(newMonthCostPlan)){

        $monthCostPlanObj.addClass("txt_error"); // 内容变红
        canUpdate = false;
    } else {
        $monthCostPlanObj.removeClass("txt_error"); // 内容恢复
    }

    // 备注显示金额
    if (!checkAmountVal(newRemarkAmount)){

        $remarkAmountObj.addClass("txt_error"); // 内容变红
        canUpdate = false;
    } else {
        $remarkAmountObj.removeClass("txt_error"); // 内容恢复
    }

    // 如果数值没有修改，也无需修改
    if (Number(oldMonthCostPlan) == Number(newMonthCostPlan) && Number(oldRemarkAmount) == Number(newRemarkAmount)) {
        canUpdate = false;
    }

    // 可更新状态，显示更新按钮，否则隐藏
    if (canUpdate){
        $updateBtnObj.show();
    } else {
        $updateBtnObj.hide();
    }
}