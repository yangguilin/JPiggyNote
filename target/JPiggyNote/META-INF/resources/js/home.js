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
$(document).ready(function(){

    // 默认支出选项选中
    $("#img_cost_button").click();

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
        $(obj).parent().toggleClass("div_button_unselected")
            .end().parent().siblings("div").toggleClass("div_button_unselected");

        // 操作按钮显示切换
        if (selectImgBtnId == "img_cost_button"){
            $("#btn_cost").show().siblings("a").hide();
        } else {
            $("#btn_income").show().css("margin-right", "10px").siblings("a").hide();
        }

        // 保存临时变量
        g_curImgButtonId = selectImgBtnId;
    }
}
