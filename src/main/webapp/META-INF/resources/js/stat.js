/**
 * Created by guilin on 2014/10/8.
 *
 * 1. 方法内部需要的方法，尽量定义方法内部，且与其他内容相隔三行空格
 *
 */


/**
 * 页面加载自动执行
 */
$(document).ready(function(){

    // 收入标题点击事件
    $("#iTd_incomeData_t").click(

        function() {

            // 隐藏其他数据
            if ($("#iTr_costDetail_c").is(":visible")) {
                hideDetailData("iTd_costData_t");
            }

            // 根据状态显示或隐藏
            if ($("#iTr_incomeDetail_c").is(":visible")) {
                hideDetailData("iTd_incomeData_t");
            } else {
                showDetailData("iTd_incomeData_t");
            }
        }
    );

    // 支出标题点击事件
    $("#iTd_costData_t").click(

        function() {

            // 隐藏其他数据
            if ($("#iTr_incomeDetail_c").is(":visible")) {
                hideDetailData("iTd_incomeData_t");
            }

            // 根据状态显示或隐藏
            if ($("#iTr_costDetail_c").is(":visible")) {
                hideDetailData("iTd_costData_t");
            } else {
                showDetailData("iTd_costData_t");
            }
        }
    );

    // 统计分类按钮点击事件
    $("a.cA_statMenu_b").click(

        function(){
            $("a.cA_statMenu_b").toggleClass("cA_statMenuSelected_b");
        }
    );



    /**
     * 隐藏详细统计列表
     * @param id    统计菜单id
     */
    function hideDetailData(id){
        $("#" + id).css({"font-size":"14px", "color":"gray"}).parent().next("tr").hide("normal");
    }

    /**
     * 显示详细统计列表
     * @param id    统计菜单id
     */
    function showDetailData(id){
        $("#" + id).css({"font-size":"16px", "color":"darkred"}).parent().next("tr").show("fast");
    }
});