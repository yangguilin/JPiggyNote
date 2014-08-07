/**
 * Created by guilin on 2014/8/7.
 */

/**
 * 用户注册信息前端检测
 */
function checkPassword(){

    var psw = $("#psw").val();
    var psw2 = $("#psw2").val();

    // 检查用户名及密码一致性
    var usernameStatus = $("#username").attr("username_status");
    var errorMsg = "";

    // 情况判断
    if (usernameStatus == "false"){
        errorMsg = "用户名已经存在";
    } else if (usernameStatus == ""){
        errorMsg = "用户名不能为空";
    } else if (psw != psw2){
        errorMsg = "两次输入的密码不一致";
    }

    // 是否需要提示错误信息
    if (errorMsg != ""){

        alert(errorMsg);
        return false;
    } else {
        return true;
    }
}

/**
 * 检查用户名是否存在
 */
function checkUserName(){

    var $msgObj = $("#userInfoMsg");
    var $userNameObj = $("#username");
    var userName = $userNameObj.val();

    // 检测前默认为不可注册用户名标记
    $userNameObj.attr("username_status", "");

    if ($msgObj == null || userName == ""){

        $msgObj.html("请输入用户名").attr({"class":"username_msg_default"});
        return;
    }

    // 调用ajax请求
    $.post("/user/exist.do", { "userName": userName },
        function (data) {

            if (data == "success") {

                $msgObj.html(">> 用户名已存在").attr({"class":"username_exist_msg"});
                $userNameObj.attr("username_status", "false");
            } else {

                $msgObj.html(">> 用户名可以使用").attr({"class":"username_not_exist_msg"});
                $userNameObj.attr("username_status", "true");
            }
        });
}