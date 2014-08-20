/**
 * Created by guilin on 2014/8/7.
 */

/**
 * 用户注册信息前端检测
 */
function checkPassword(){

    var psw = $("#txt_psw").val();
    var psw2 = $("#txt_psw_confirm").val();

    // 检查用户名及密码一致性
    var usernameStatus = $("#txt_user_name").attr("username_status");
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

        $("#div_login_msg").text(errorMsg);
        return false;
    } else {
        return true;
    }
}

/**
 * 检查用户名是否存在
 */
function checkUserName(){

    var $msgObj = $("#div_login_msg");
    var $userNameObj = $("#txt_user_name");
    var userName = $userNameObj.val();

    // 检测前默认为不可注册用户名标记
    $userNameObj.attr("username_status", "");

    if ($msgObj == null || userName == ""){

        $msgObj.text("请输入账号");
        return;
    }

    // 调用ajax请求
    $.post("/user/exist.do", { "userName": userName },
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


/**
 * 注册用户
 */
function register(){

    if (checkPassword()){
        document.forms[0].submit();
    } else {

        $("#txt_psw").val("");
        $("#txt_psw_confirm").val("");
    }
}