/**
 * Created by guilin on 2014/8/8.
 */

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