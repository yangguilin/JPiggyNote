
/**
 * 注册用户
 */
function register(){

    if (checkPassword()){
        document.forms[0].submit();
    } else {

        $("#iIptPsw").val("");
        $("#iIptPswConfirm").val("");
    }
}

/**
 * 用户注册信息前端检测
 */
function checkPassword(){

    var psw = $("#iIptPsw").val();
    var psw2 = $("#iIptPswConfirm").val();

    // 检查用户名及密码一致性
    var usernameStatus = $("#iIptName").attr("username_status");
    var errorMsg = "";

    // 情况判断
    if (usernameStatus == "false"){
        errorMsg = "账号已经存在";
    } else if (usernameStatus == ""){
        errorMsg = "账号不能为空";
    } else if (psw != psw2){
        errorMsg = "两次输入口令不同";
    }

    // 是否需要提示错误信息
    if (errorMsg != ""){

        $("#iDiv_errorMsg").text(errorMsg);
        return false;
    } else {
        return true;
    }
}

/**
 * 检查用户名是否存在
 */
function checkUserName(){

    var $msgObj = $("#iDiv_errorMsg");
    var $userNameObj = $("#iIptName");
    var userName = $userNameObj.val();
    var year = $("#iIptYear").val();

    if ($msgObj == null || userName == ""){

        $msgObj.text("请输入姓名");
        return;
    }

    // 检测前默认为不可注册用户名标记
    $userNameObj.attr("username_status", "");

    // 调用ajax请求
    $.post("snwt/user/exist.do", { "userName": userName, "year":year},
        function (data) {

            if (data == "success") {

                $msgObj.text("账号已存在");
                $userNameObj.attr("username_status", "false");
            } else {
                $msgObj.text("");
                $userNameObj.attr("username_status", "true");
            }
        });
}