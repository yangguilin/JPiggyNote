/**
 * Created by guilin on 2014/8/8.
 */


/**
 * 添加日常记录
 */
function addRecord(){

    var userName = $("#hidden_user_name").val();
    var moneyType = $("#money_type").val();
    var amount = $("#amount").val();
    var categoryId = $("#category").val();
    var categoryName = $("#category").find("option:selected").text();
    var statType = $("#stat_type").val();
    var remark = $("#remark").val();

    // 调用ajax请求
    $.post("/daily_record/add.do", { "userName": userName , "moneyType":moneyType, "statType":statType, "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {

            if (data == "success") {
                alert("操作成功!");
            } else {
                alert("操作失败！" + data);
            }
        });
}


/**
 * 返回主菜单
 */
function backToMenu(){

    window.location.href = "/";
}