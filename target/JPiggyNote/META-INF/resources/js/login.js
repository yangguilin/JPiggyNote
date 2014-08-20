/**
 * Created by guilin on 2014/8/8.
 */

/**
 * 登录提交前检查
 * @returns {boolean}
 */
function check(){

    var pass = false;
    var msg = "";

    var userName = $("#userName").val();
    var psw = $("#psw").val();

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

    if(check()){
        document.forms[0].submit();
    }
}