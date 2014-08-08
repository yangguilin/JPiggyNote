/**
 * Created by guilin on 2014/8/8.
 */

/**
 * 登录提交前检查
 * @returns {boolean}
 */
function check(){

    if ($("#userName").val() == ""){

        $("#userInfoMsg").html("用户名不能为空");
        return false;
    }

    return true;
}