/**
 * 用户操作相关脚本文件
 * by yanggl
 * 2014-08-01
 */

function deleteUser(){

    var userName = $("#txt_userName").val();

    // confirm the delete action
    var isDeleted = false;
    if (confirm("确认删除用户[" + userName + "]?") == true) {
        isDeleted = true;
    }

    // send
    if (isDeleted) {
        // 调用ajax请求
        $.post("../user/delete", { "user_name": userName },
            function (data) {
                if (data == "success") {
                    alert("删除成功!")
                } else {
                    alert("删除或更新事项失败！" + data);
                }
            });
    }
}