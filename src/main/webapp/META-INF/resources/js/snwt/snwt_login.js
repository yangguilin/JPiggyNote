
/**
 * 用户登录
 */
function login(){

    var userName = $("#iIptName").val();
    var password = $("#iIptPsw").val();

    // 检查空内容
    if (userName == "" || password == ""){
        $("#iDiv_errorMsg").text("姓名和口令不可为空");
        return;
    }

    // 提交form
    document.forms[0].submit();

}