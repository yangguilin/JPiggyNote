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

            // 常量
            var statMenuSelectedClassName = "cA_statMenuSelected_b";

            // 操作id
            var id = $(this).attr("id");
            var statInfoContainerId = "";
            if (id == "iA_curMonthStat_b"){
                statInfoContainerId = "iDiv_curMonthStatInfo_c";
            } else if (id == "iA_monthStat_b"){
                statInfoContainerId = "iDiv_monthStatInfo_c";
            } else if (id == "iA_prepayStat_b"){
                statInfoContainerId = "iDiv_monthPrepayStatInfo_c";
            }

            // 根据点击状态切换菜单状态和内容显示
            if ($(this).hasClass(statMenuSelectedClassName) == false) {

                // 按钮选中状态
                $("#" + id).toggleClass(statMenuSelectedClassName).siblings("a").removeClass(statMenuSelectedClassName);
                // 内容区显示切换
                $("#" + statInfoContainerId).show(300).siblings("div[id]").hide(0);
            }
        }
    );

    // 按月统计每月数据标题点击事件
    $(".cTbl_monthItem_d td[name]").click(

        function(){

            var $detailDataObj = $(this).parent().next("tr[class=cTr_monthDetailData_c]");
            var bVisible = $detailDataObj.is(":visible");

            // 收起其他月份数据
            $(".cTr_monthDetailData_c").hide(0).parent().children("tr:first-child").children("td").css({"font-size":"14px", "color":"gray"});

            // 如果已经打开，则不再打开
            if (bVisible == false) {
                // 下拉本月详细数据
                $(this).css({"font-size":"16px", "color":"darkred"}).parent().next("tr[class=cTr_monthDetailData_c]").show("fast");
            }
        }
    );

    // 本月计划支出进度条
    showCurMonthPlanProcessBar();
    calculateAndShowMonthStatData();


    /**
     * 隐藏详细统计列表
     * @param id    统计菜单id
     */
    function hideDetailData(id){
        $("#" + id).css({"font-size":"14px", "color":"gray"}).parent().next("tr").hide("fast");
    }

    /**
     * 显示详细统计列表
     * @param id    统计菜单id
     */
    function showDetailData(id){
        $("#" + id).css({"font-size":"16px", "color":"darkred"}).parent().next("tr").show("fast");
    }

    function calculateAndShowMonthStatData(){
        var totalIncome = $("#iIpt_totalIncome_h").val().toNumber();
        var totalCost = $("#iIpt_totalCost_h").val().toNumber();
        var monthNum = $("#iIpt_curMonthNum_h").val().toNumber();
        var monthIncome = (totalIncome / monthNum).toFixed(2);
        var monthCost = (totalCost / monthNum).toFixed(2);
        var monthAvg = monthIncome - monthCost;
        $("#iSpan_monthIncome").text(monthIncome);
        $("#iSpan_monthCost").text(monthCost);
        $("#iSpan_monthAvg").text(monthAvg);
    }
});

// 本月计划消费进度条
function showCurMonthPlanProcessBar(){

    // 本月计划支出进度条
    var monthCostPlan = Number($("#iIpt_monthCostPlan_h").val());
    var curMonthCost = Number($("#iIpt_curMonthCost_h").val());

    var curVal = 0;
    var leftVal = 0;
    if (monthCostPlan >= curMonthCost){
        curVal = curMonthCost / monthCostPlan * 100;
    } else {

        curVal = monthCostPlan / curMonthCost * 100;
        $("#iTd_leftVal").css("background-color", "darkred");
    }
    leftVal = 100 - curVal;

    $("#iTd_curVal").css("width", curVal + "%");
    $("#iTd_leftVal").css("width", leftVal + "%");
}