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
            $(this).animate({"font-size": "18px"}, 100, null);
        }
    }, function(){

        if (!$(this).hasClass("h3_selected")){
            $(this).animate({"font-size":"16px"}, 100, null);
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
        var $curInputObj = $(this).children("input");
        if (!$curInputObj.is(":visible")) {
            $curInputObj.show("fast");
        }
    });

    // 默认支出选项选中
    $("#img_cost_button").click();

    // 默认显示今天列表
    $("#div_history_container table").css("display", "none");
    $("#h3_today").click();
});


/**
 * 检查输入金额
 */
function checkAmountAndAdd(event){

    var $amountObj = $("#txt_amount");

    var amount = $amountObj.val();
    if (!checkAmountVal(amount)){
        // 内容变红
        $amountObj.addClass("txt_error");
    } else {

        // 内容恢复正常
        $amountObj.removeClass("txt_error");

        // 根据备注金额来判断是否添加备注，如果为垫付和收回，则必须添加备注
        var remarkAmount = Number($("#hidden_remarkAmount").val());
        if (Number(amount) >= remarkAmount || g_curImgButtonId == "img_prepay_button"){
            $("#tr_remark").show("normal");
        } else {
            $("#tr_remark").hide("fast");
        }
    }

    // 判断是否为回车键，如果是直接添加
    if (event != null) {

        var theEvent = window.event || event;
        var code = theEvent.keyCode || theEvent.which;
        // 回车键
        if (code == 13) {

            // 当前选择的操作类型
            var $curObj = $("#div_pro_buttons").children("a:visible");
            // 添加新记录
            addNewRecord($curObj[0]);
        }
    }
}

/**
 * 选择图片按钮
 * @param obj
 */
function selectImgButton(obj){
    var selectImgBtnId = $(obj).attr("id");
    if (selectImgBtnId == g_curImgButtonId){
        // 如果为临时类型，可再次切换“垫付/收回”按钮
        if (selectImgBtnId == "img_prepay_button"){
            if ($("#btn_prepay").is(":visible")){
                $("#btn_prepay_back").show().css("margin-left", "-25px").siblings("a").hide();
            } else {
                $("#btn_prepay").show().siblings("a").hide();
            }
        } else if (selectImgBtnId == "img_cost_button") {
            if ($("#btn_cost").is(":visible")){
                $("#btn_cost_period").show().siblings("a").hide();
            } else if ($("#btn_cost_period").is(":visible")) {
                $("#btn_cost_big").show().siblings("a").hide();
            } else if ($("#btn_cost_big").is(":visible")){
                $("#btn_cost").show().siblings("a").hide();
            }
        } else {
            return;
        }
    } else {

        // 图标按钮样式更换
        $(obj).parent().removeClass("div_button_unselected")
            .end().parent().siblings("div:not(.div_button_unselected)").addClass("div_button_unselected");

        // 操作按钮显示切换
        if (selectImgBtnId == "img_cost_button"){
            $("#btn_cost").show().siblings("a").hide();
        } else if (selectImgBtnId == "img_income_button") {
            $("#btn_income").show().siblings("a").hide();
        } else if (selectImgBtnId == "img_prepay_button") {
            $("#btn_prepay").show().siblings("a").hide();
        }

        // 保存临时变量
        g_curImgButtonId = selectImgBtnId;
        // 更新备注显示状态
        checkAmountAndAdd(null);
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

    // 备注
    var remark = "";
    var $remarkObj = $("#input_remark");
    if ($remarkObj.is(":visible")){
        remark = $.trim($remarkObj.val());
    }

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
    var statType = "NORMAL";
    if (curId == "btn_cost_big"){
        statType = "SPECIAL";
    } else if (curId == "btn_cost_period"){
        statType = "PERIOD";
    }

    var requestUrl = "/daily_record/add.do";
    var dayIndex = 0;
    // 根据选中的历史记录时间，来调整新加记录的创建时间，及请求的链接
    var $curHistoryBar = $("h3.h3_selected");
    if ($curHistoryBar != null){

        var id = $curHistoryBar.attr("id");
        if (id == "h3_yesterday"){
            dayIndex = -1;
        } else if (id == "h3_dayafteryesterday"){
            dayIndex = -2;
        }
    }
    // 请求链接
    if (dayIndex != 0){
        requestUrl = "/daily_record/add2.do";
    }

    // 调用ajax请求
    $.post(requestUrl, { "userName": userName , "moneyType":moneyType, "statType":statType, "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark, "index":dayIndex },
        function (data) {

            if (data == "success") {

                // 列表中添加数据
                addItemIntoTable(userName, statType, moneyType, amount, remark);
                // 恢复初始值
                $amountObj.val("");
                $remarkObj.val("");
                $("#img_cost_button").click();
                $("#btn_cost").show().siblings("a").hide();
                // 隐藏备注栏
                $("#tr_remark").hide("fast");

            } else {
                alert("操作失败！");
                reloadCurrentPage();
            }
        });
}


/**
 * 向列页面表中添加一条记录
 */
function addItemIntoTable(userName, statType, moneyType, amount, remark){

    if (moneyType == "COST"){
        if (statType == 'PERIOD'){
            moneyType = "分期";
        } else if (statType == 'SPECIAL'){
            moneyType = "特殊";
        } else {
            moneyType = "支出";
        }
    } else if (moneyType == "INCOME"){
        moneyType = "收入";
    } else if (moneyType == "PREPAY"){
        moneyType = "垫付";
    } else if (moneyType == "PAYBACK"){
        moneyType = "收回";
    }

    // 新记录html字符串
    var trObjHtml = '<tr><td>[New]&nbsp;&nbsp;|&nbsp;&nbsp;'
        + moneyType + '&nbsp;&nbsp;|&nbsp;&nbsp;' + amount + '&nbsp;元';
    if (remark != "") {
        trObjHtml += '&nbsp;&nbsp;|&nbsp;&nbsp;' + remark;
    }
    trObjHtml += '</div></td></tr>';

    // 插入到列表
    var id = $("h3.h3_selected").attr("id");
    var listId = "tbl_today_list";
    if (id == "h3_yesterday"){
        listId = "tbl_yesterday_list";
    } else if (id == "h3_dayafteryesterday"){
        listId = "tbl_dayBeforeYesterday_list";
    } else {
        id = "h3_today";
    }

    $("#" + listId).prepend($(trObjHtml));

    // 更新标题
    updateHistoryTitleNum(id);
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
                $(obj).parent().parent().parent().hide("slow").remove();
                // 更新标题
                updateHistoryTitleNum(titleId);
            } else {
                alert("操作失败！");
                reloadCurrentPage();
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

/**
 * 密码输入框回车快捷登陆
 */
function quickLogin(event){
    var theEvent = window.event || event;
    var code = theEvent.keyCode || theEvent.which;

    if ($("#txt_psw").val() != ""){

        // 回车键
        if (code == 13){
            login();
        }
    }
}
