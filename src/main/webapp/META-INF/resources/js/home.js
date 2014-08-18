/**
 * Created by guilin on 2014/8/8.
 */

/**
 * 页面全局变量
 */
var g_curImgButtonId = "";


/**
 * 页面载入后默认执行
 */
$(document).ready(function() {

    // 历史下拉列表事件
    $("#div_history_container h3").hover(function(){

        if (!$(this).hasClass("h3_selected")) {
            $(this).animate({"font-size": "14px"}, 100, null);
        }
    }, function(){

        if (!$(this).hasClass("h3_selected")){
            $(this).animate({"font-size":"12px"}, 100, null);
        }
    });

    // 历史查看页事件
    $("#div_history_container h3").click(function () {

        // 显示切换
        $("#div_history_container table").css("display", "none");
        $(this).next("table").css("display", "block");
        // 选中样式调整
        $(this).addClass("h3_selected").siblings("h3").removeClass("h3_selected");
    });

    // 默认支出选项选中
    $("#img_cost_button").click();

    // 默认显示今天的记录
    $("#h3_today").click();
});

/**
 * 退出登录
 */
function logout(){

    var userName = $("#span_curUserName").html();
    if (userName == null || userName == ""){ return; }

    // 调用ajax请求
    $.post("/logout.do", { "userName": userName },
        function (data) {

            if (data == "success") {

                alert("成功退出");
                window.location.href = "/";
            } else {
                alert("退出失败");
            }
        });
}

/**
 * 检查输入金额
 */
function checkAmount(){

    var $amountObj = $("#txt_amount");

    var amount = $amountObj.val();
    if (amount == "" || isNaN(amount) || parseInt(amount) <= 0){
        // 内容变红
        $amountObj.addClass("txt_error");
    } else {
        // 内容恢复正常
        $amountObj.removeClass("txt_error");
    }
}

/**
 * 选择图片按钮
 * @param obj
 */
function selectImgButton(obj){

    var selectImgBtnId = $(obj).attr("id");
    if (selectImgBtnId == g_curImgButtonId){
        return;
    } else {

        // 图标按钮样式更换
        $(obj).parent().removeClass("div_button_unselected")
            .end().parent().siblings("div:not(.div_button_unselected)").addClass("div_button_unselected");

        // 操作按钮显示切换
        if (selectImgBtnId == "img_cost_button"){
            $("#btn_cost").show().siblings("a").hide();
        } else if (selectImgBtnId == "img_income_button") {
            $("#btn_income").show().css("margin-right", "10px").siblings("a").hide();
        } else if (selectImgBtnId == "img_prepay_button") {

            $("#btn_prepay").show().css("margin-right", "10px").siblings("a").hide();
            $("#btn_prepay_back").show();
        }

        // 保存临时变量
        g_curImgButtonId = selectImgBtnId;
    }
}

/**
 * 添加一条记录
 */
function addNewRecord(obj){

    // 金额
    var amount = 0;
    var $amountObj = $("#txt_amount");
    if ($amountObj.hasClass("txt_error")){

        alert("请输入正确的金额");
        return;
    }
    amount = $amountObj.val();

    // moneyType
    var curId = $(obj).attr("id");
    var moneyType = "COST";
    if (curId == "btn_income"){
        moneyType = "INCOME";
    } else if (curId == "btn_prepay"){
        moneyType = "PREPAY";
    } else if (curId == "btn_prepay_back"){
        moneyType = "PAYBACK";
    }

    // userName
    var userName = $("#span_curUserName").text();
    if (userName == null || userName == ""){
        return;
    }

    // default
    var categoryId = "0";
    var categoryName = "";
    var statType = "SIMPLE";
    var remark = "";

    // 调用ajax请求
    $.post("/daily_record/add.do", { "userName": userName , "moneyType":moneyType, "statType":statType, "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {

            if (data == "success") {

                alert("操作成功!");
                // 恢复初始值
                $amountObj.val("0");

            } else {
                alert("操作失败！" + data);
            }
        });
}
