/**
 * Created by guilin on 2014/8/8.
 */

/**
 * 登录提交前检查
 * @returns {boolean}
 */
function check(){

    var pass = true;

    if ($("#userName").val() == ""){

        $("#userInfoMsg").html("用户名不能为空");
        pass = false;
    } else {

        $("#userInfoMsg").html("");
        pass = true;
    }

    if ($("#psw").val() == ""){

        $("#pswInfoMsg").html("密码不能为空");
        pass = false;
    } else {

        $("#pswInfoMsg").html("");
        pass = true;
    }

    return pass;
}