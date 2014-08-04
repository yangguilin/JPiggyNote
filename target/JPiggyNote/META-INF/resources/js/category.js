/**
 * Created by guilin on 2014/8/1.
 */
/**
 * 用户账目分类相关脚本
 */

function addCategory(){

    var userName = "yanggl2";
    var xml = "xml";
    var xml_sorted = "xml_sorted";

    // 调用ajax请求
    $.post("../category/add", { "userName": userName , "categoryXml":xml, "categoryXmlSorted":xml_sorted },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function resetCategory(){

    var userName = "yanggl2";

    // 调用ajax请求
    $.post("../category/reset", { "userName": userName },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function updateCategory(){

    var userName = "yanggl2";
    var xml = "xml_update";
    var xml_sorted = "xml_sorted_update";

    // 调用ajax请求
    $.post("../category/update", { "userName": userName, "categoryXml":xml, "categoryXmlSorted":xml_sorted },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}