


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
 * 检查字符串是否为合法的数值金额
 * @param amount    金额字符
 */
function checkAmountVal(amount){
    return (amount != "" && !isNaN(amount) && parseInt(amount) > 0);
}