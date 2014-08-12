/**
 * Created by guilin on 2014/8/1.
 */

/**
 * 解锁分类条目为可编辑状态
 */
function unlockCategoryItemForEdit(obj){

    // 用的页面对象
    var $ctgItemTr = $(obj).parent().parent();
    var $ctgNameTd = $ctgItemTr.children("td:eq(1)");
    var $ctgOpTd = $ctgItemTr.children("td:eq(3)");

    // 将其他正在编辑的条目恢复初始
    restoreCtgItem();

    // 可输入状态和操作按钮input
    var ctgName = $ctgNameTd.text();
    var ctgId = $ctgItemTr.attr("categoryid");
    var ctgNameInput = "<input type='text' value='" + ctgName + "' class='txt_category_item' onchange='updateOpBtnText(this)' />";
    var ctgUpInput = "<input type='button' value='更新' oldname='" + ctgName + "' categoryid='" + ctgId + "' onclick='updateCtgItem(this)' />";

    // 清空原有内容
    $ctgNameTd.text("");

    // 插入输入框
    $ctgNameTd.append($(ctgNameInput));

    // 更新操作按钮
    $ctgOpTd.children("span").hide("fast");
    $ctgOpTd.append($(ctgUpInput));
}

/**
 * 将某行正在修改的分类恢复为初始未修改状态
 */
function restoreCtgItem(){

    var $editTr = $("#table_category_list tr:has(td:has(input[oldname]))");
    if ($editTr.length > 0){

        // 可用jq对象
        $ctgNameTd_edit = $editTr.children("td:eq(1)");
        $ctgOpTd_edit = $editTr.children("td:eq(3)");

        // 恢复
        var oldName = $ctgOpTd_edit.children("input").attr("oldname");
        $ctgNameTd_edit.empty().text(oldName);
        $ctgOpTd_edit.children("span").show().end().children("input").remove();
    }
}

/**
 * 更新分类条目
 * @param obj
 */
function updateCtgItem(obj){

    // 发送更新请求
    var userName = $("#hidden_user_name").val();
    var ctgid = $(obj).attr("categoryid");
    var oldCtgName = $(obj).attr("oldname");
    var ctgName = $(obj).parent().parent().children("td:eq(1)").children("input").val();

    if (oldCtgName == ctgName) {
        restoreCtgItem();
        return;
    }

    $.post("/category/update_item.do",
        { "userName": userName, "categoryId":ctgid, "categoryName":ctgName },
        function (data) {
            if (data == "success") {

                $(obj).attr("oldname", ctgName);
                restoreCtgItem();
                // alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

/**
 * 添加新的分类
 * @param obj
 */
function addNewCategory(obj){

    var $opTr = $(obj).parent().parent();

    var typeTd = "<td><select><option value='COST'>支出</option><option value='INCOME'>收入</option></select></td>";
    var categoryTd = "<td><input type='text' style='width:50px' /></td>";
    var opTd = "<td colspan='2'><input type='button' onclick='addNewCategoryToDB(this)' value='添加' /></td>";
    var newTr = "<tr>" + typeTd + categoryTd + opTd + "</tr>";

    // 插入新行
    $opTr.before($(newTr));

    // 隐藏掉“添加新分类”按钮
    $("#opTr").css("display", "none");
}


/**
 * 将新分类添加到数据库
 * @param obj
 */
function addNewCategoryToDB(obj){

    var userName = $("#hidden_user_name").val();

    var $opTr = $(obj).parent().parent();
    var moneyType = $opTr.children("td:eq(0)").children("select").val();
    var categoryName = removeEnterCharForString($opTr.children("td:eq(1)").children("input").val());

    // 发送
    $.post("/category/add_item.do",
        { "userName": userName, "moneyType": moneyType, "categoryName":categoryName },
        function (data) {

            if (data != null && data != "" && data != "fail") {

                // 清除可编辑行
                $opTr.empty();

                // 添加新加分类行
                addOneCategoryItem(moneyType, data, categoryName, "0");

                // 重新显示“添加分类”按钮
                $("#opTr").css("display", "");

            } else { alert("添加分类失败"); }
        });
}

/**
 * 添加一行分类项到列表
 * @param type
 * @param id
 * @param name
 * @param des
 * @param time
 */
function addOneCategoryItem(type, id, name, time){

    var money_type = type == "COST" ? "支出" : "收入";
    var $title_row = $("#table_category_list tr:last");
    var categoryRow = "<tr categoryid='" + id + "'><td>" + money_type + "</td><td>" + name + "</td><td>" + time + "</td>><td><span onclick='unlockCategoryItemForEdit(this)' class='span_button'>[修改]</span></td></tr>";
    $title_row.before($(categoryRow));
}

/**
 * 去除字符串中含有的回车符\n
 * @param str
 * @returns {XML|string|void}
 */
function removeEnterCharForString(str){
    return str.replace(/\n/g, "");
}

/**
 * 返回主菜单
 */
function backToMenu(){

    window.location.href = "/";
}
