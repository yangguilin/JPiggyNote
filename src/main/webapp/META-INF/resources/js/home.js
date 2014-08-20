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

        // 重置删除按钮
        $(".div_record_item input:visible").hide();

        if ($(this).next("table").is(":visible")){

            // 收起下拉框
            $(this).next("table").css("display", "none");
            // 恢复未选样式
            $(this).removeClass("h3_selected");
        } else {

            // 显示切换
            $("#div_history_container table").css("display", "none");
            $(this).next("table").css("display", "block");
            // 选中样式调整
            $(this).addClass("h3_selected").siblings("h3").removeClass("h3_selected");
        }
    });

    // 历史条目鼠标悬停效果及点击事件
    $(".div_record_item").hover(function(){
        $(this).css("font-weight", "bold");
    }, function(){
        $(this).css("font-weight", "normal");
    }).click(function(){

        $(".div_record_item input:visible").hide("fast");
        $(this).children("input").show("fast");
    });

    // 默认支出选项选中
    $("#img_cost_button").click();

    // 默认显示今天列表
    $("#div_history_container table").css("display", "none");
    $("#h3_today").click();
});

/**
 * 退出登录
 */
function logout(){

    var userName = $("#hidden_userName").val();
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
    var userName = $("#hidden_userName").val();
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

                // 列表中添加数据
                addItemIntoTable(userName, moneyType, amount);
                // 恢复初始值
                $amountObj.val("0");

            } else {
                alert("操作失败！" + data);
            }
        });
}


/**
 * 向列页面表中添加一条记录
 */
function addItemIntoTable(userName, moneyType, amount){

    if (moneyType == "COST"){
        moneyType = "支出";
    } else if (moneyType == "INCOME"){
        moneyType = "收入";
    } else if (moneyType == "PREPAY"){
        moneyType = "垫付";
    } else if (moneyType == "PAYBACK"){
        moneyType = "收回";
    }

    // 新记录html字符串
    var trObjHtml = '<tr><td>[New]&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;'
        + userName + '>&nbsp;&nbsp;' + moneyType + '&nbsp;' + amount + '&nbsp;元</div></td></tr>';
    // 插入到列表
    $("#tbl_today_list").prepend($(trObjHtml));
    // 更新标题
    updateHistoryTitleNum("h3_today");
}

/**
 * 更新历史记录标题
 * @param titleId   历史标题id
 */
function updateHistoryTitleNum(titleId){

    var newTitle = "";

    // 区别是哪一个条目
    if (titleId == "h3_today"){
        newTitle = "今天";
    } else if (titleId == "h3_yesterday"){
        newTitle = "昨天";
    } else if (titleId == "h3_dayafteryesterday"){
        newTitle = "前天";
    } else {
        return;
    }

    // 统计最新记录条数
    var newNum = $("#" + titleId).next("table").children("tbody").children("tr").length;

    // 赋值
    newTitle += "(" + newNum + ")";
    $("#" + titleId).text(newTitle);
}


/**
 * 删除记录
 * @param obj   当前button对象
 */
function deleteRecord(obj){

    var id = $(obj).attr("record_id");
    var userName = $("#hidden_userName").val();
    if (userName == null || userName == ""){
        return;
    }

    // ajax request
    $.post("/daily_record/delete.do", { "userName": userName, "id":id },
        function (data) {
            if (data == "success") {

                var titleId = $(obj).attr("title_id");
                // 删除列表记录
                $(obj).parent().parent().parent().remove();
                // 更新标题
                updateHistoryTitleNum(titleId);
            } else {
                alert("操作失败！" + data);
            }
        });
}

/**
 * 登录提交前检查
 * @returns {boolean}
 */
function checkBeforeLogin(){

    var pass = false;
    var msg = "";

    var userName = $("#txt_user_name").val();
    var psw = $("#txt_psw").val();

    if (userName == "" && psw == ""){
        msg = "账号/口令不能为空";
    } else if (userName == ""){
        msg = "账号不能为空";
    } else if (psw == ""){
        msg = "口令不能为空";
    } else {
        pass = true;
    }

    $("#div_login_msg").text(msg);

    return pass;
}

/**
 * 登录
 */
function login(){

    if(!checkBeforeLogin()){
        return;
    }

    var userName = $("#txt_user_name").val();
    var psw = $("#txt_psw").val();

    // ajax request
    $.post("/login_ajax.do", { "userName": userName, "password":psw },
        function (data) {
            if (data == "success") {
                window.location.reload();
            } else {

                // 错误提示
                $("#div_login_msg").text("账号/口令不匹配");
                // 重置输入框
                $("#txt_user_name").val("");
                $("#txt_psw").val("");
            }
        });
}
