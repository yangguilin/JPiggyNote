
function HoneycombUnit(index, left, leftTop, rightTop, right, rightBottom, leftBottom){
    this.index = index;
    this.left = left;
    this.leftTop = leftTop;
    this.rightTop = rightTop;
    this.right = right;
    this.rightBottom = rightBottom;
    this.leftBottom = leftBottom;
}

function HoneycombUnit4User(index, front, frontRight, backRight, back, backLeft, frontLeft){
    this.index = index;
    this.front = front;
    this.frontRight = frontRight;
    this.backRight = backRight;
    this.back = back;
    this.backLeft = backLeft;
    this.frontLeft = frontLeft;
}


var g_const_honeycombUnitImgSrc_empty = "/img/honeycombunit_empty.png";
var g_const_honeycombUnitImgSrc_start = "/img/honeycombunit_start.png";
var g_const_honeycombUnitImgSrc_finish = "/img/honeycombunit_finish.png";
var g_const_honeycombUnitImgSrc_pass = "/img/honeycombunit_pass.png";
var g_const_honeycombUnitImgSrc_player = "/img/honeycombunit_player.png";
var g_const_honeycombUnitImgSrc_gold = "/img/honeycombunit_gold.png";

var g_playerCurrentUnit = null;
var g_playerLastUnitIndex = -1;
var g_finishUnitIndex = -1;
var g_honeycombUnitList = {};
var g_singleSideUnitNum = 0;
var g_goldUnitTotalNum = 0;
var g_getGoldUnitNum = 0;
var g_userMoveStepNum = 0;
var g_glanceTimeDuringGame = 0;
var g_playerCostSecond = 0;
var g_gameFinishStatus = ""; // "success":成功，"fail"：没有到达终点，"fail2"：没有收集到全部蜂蜜


$(document).ready(function(){
    initPageData();
    initHoneycombUnitList();
    showHoneycombImageView();
    showStartAndFinishUnit();
    showGoldUnit();
});

function initPageData(){
    var goldUnitData = $("#iIpt_goldUnitData").val();
    var arr = goldUnitData.split(':');
    g_goldUnitTotalNum = arr[0].toNumber();
    $("#iSel_difficultySelect").val(g_goldUnitTotalNum);
    $("#iSpan_difficultyDegree").text(g_goldUnitTotalNum);
}

function showGoldUnit(){
    var goldUnitData = $("#iIpt_goldUnitData").val();
    var arr = goldUnitData.split(':');
    g_goldUnitTotalNum = arr[0].toNumber();
    if (g_goldUnitTotalNum > 0){
        var arr2 = arr[1].split(',');
        for (var i = 0; i < arr2.length; i++){
            $("img[unit_index=" + arr2[i] + "]").attr("src", g_const_honeycombUnitImgSrc_gold);
        }
    }
    $("#iSpan_goldUnitTotalNum").text(" / " + g_goldUnitTotalNum);
}

function initHoneycombUnitList(){
    var data = $("#iIpt_honeycombData").val();
    if (data != ""){
        var arr = data.split(';');
        for (var i = 0; i < arr.length; i++){
            var arr2 = arr[i].split(':');
            var unit = new HoneycombUnit(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5], arr2[6]);
            g_honeycombUnitList[arr2[0]] = unit;
        }
    }
}

function showStartAndFinishUnit(){
    var startSideVal = $("#iIpt_startSide").val();
    var finishSideVal = $("#iIpt_finishSide").val();
    if (startSideVal != "" && finishSideVal != ""){
        var startUnitIndex = startSideVal.split(',')[0];
        var finishUnitIndex = finishSideVal.split(',')[0];
        $("img[unit_index=" + startUnitIndex + "]").attr("src", g_const_honeycombUnitImgSrc_start);
        $("img[unit_index=" + finishUnitIndex + "]").attr("src", g_const_honeycombUnitImgSrc_finish);
        g_finishUnitIndex = finishUnitIndex;

        var curUnit = g_honeycombUnitList[startUnitIndex];
        var front = curUnit.right;
        var frontLeft = curUnit.rightTop;
        var frontRight = curUnit.rightBottom;
        var back = curUnit.left;
        var backLeft = curUnit.leftTop;
        var backRight = curUnit.leftBottom;
        g_playerCurrentUnit = new HoneycombUnit4User(startUnitIndex, front, frontRight, backRight, back, backLeft, frontLeft);
    }
}

// 显示迷宫单元编号（备用）
function showHoneycombNumberView(){
    var viewData = $("#iIpt_viewData").val();
    var viewHtml = "";
    if (viewData != "") {
        var arr = viewData.split(',');
        for (var i = 0; i < arr.length; i++){
            var cur = arr[i];
            if (cur == "-"){
                viewHtml += "<span class='cSpan_numberUnit'></span>";
            } else if (cur == "|"){
                viewHtml += "<br/>";
            } else {
                viewHtml += "<span class='cSpan_numberUnit'>" + cur + "</span>";
            }
        }
        $("#iDiv_overlook_c").html(viewHtml);
    }
}

function showHoneycombImageView(){
    var viewData = $("#iIpt_viewData").val();
    g_singleSideUnitNum = $("#iIpt_singleSideUnitNum").val().toNumber();
    var totalUnitNum = $("#iIpt_totalUnitNum").val().toNumber();
    var viewHtml = "";
    if (viewData != "" && g_singleSideUnitNum != -1 && totalUnitNum != -1) {
        var arr = viewData.split(',');
        var rowIndex = 1;
        var rowUnitNum = 7;
        var honeycombIndex = 0;
        for (var i = 0; i < arr.length; i++){
            if (arr[i] == "-"){
                viewHtml += "<span></span>";
            } else if (arr[i] == "|"){
                (rowIndex < g_singleSideUnitNum) ? rowUnitNum++ : rowUnitNum--;
                rowIndex++;
                viewHtml += "</span><br/><span>";
            } else {
                if (honeycombIndex == 0){
                    viewHtml = "<span>"
                }
                viewHtml += "<span class='cSpan_imgUnit'><img unit_index='" + arr[i] + "' class='cImg_unit' src='" + g_const_honeycombUnitImgSrc_empty + "'/></span>";
                if (honeycombIndex == (totalUnitNum - 1)){
                    viewHtml += "</span><br/>"
                }
                honeycombIndex++;
            }
        }
        $("#iDiv_honeycombView").html(viewHtml);
    }
}

function goToNextUnit(position){
    var passSideVal = "";
    if (position == "front"){
        passSideVal = g_playerCurrentUnit.front;
    } else if (position == "front_right"){
        passSideVal = g_playerCurrentUnit.frontRight;
    } else if (position == "back_right"){
        passSideVal = g_playerCurrentUnit.backRight;
    } else if (position == "back"){
        passSideVal = g_playerCurrentUnit.back;
    } else if (position == "back_left"){
        passSideVal = g_playerCurrentUnit.backLeft;
    } else if (position == "front_left"){
        passSideVal = g_playerCurrentUnit.frontLeft;
    }
    var arr = passSideVal.split(',');
    var targetUnitIndex = arr[1];
    checkGoldUnit(targetUnitIndex);
    updateUserActionData();
    if (g_userMoveStepNum > 1) {
        $("img[unit_index=" + g_playerCurrentUnit.index + "]").attr("src", g_const_honeycombUnitImgSrc_pass);
    }
    $("img[unit_index=" + targetUnitIndex + "]").attr("src", g_const_honeycombUnitImgSrc_player);
    if (!checkPlayerFinishGame(targetUnitIndex)) {
        var targetSideVal = arr[1] + "," + arr[0];
        g_playerLastUnitIndex = g_playerCurrentUnit.index;
        g_playerCurrentUnit = getTargetUnit4UserByBackSideVal(targetUnitIndex, targetSideVal);
    } else {
        showGameResult();
    }
}

function checkGoldUnit(unitIndex){
    var beGoldUnit = $("img[unit_index=" + unitIndex + "]").attr("src") == g_const_honeycombUnitImgSrc_gold;
    if (beGoldUnit){
        g_getGoldUnitNum++;
    }
}

function updateUserActionData(){
    $("#iSpan_getGoldUnitNum").text(g_getGoldUnitNum);
    $("#iSpan_moveStepNum").text(++g_userMoveStepNum);
}

function checkPlayerFinishGame(index){
    var finish = true;
    if (index == -1){
        g_gameFinishStatus = "fail";
    } else if (index == g_finishUnitIndex){
        if (g_goldUnitTotalNum == g_getGoldUnitNum) {
            g_gameFinishStatus = "success";
        } else {
            g_gameFinishStatus = "fail2";
        }
    } else {
        finish = false;
    }
    return finish;
}

function stopTimer(){
    $(".cDiv_rememberTimer").TimeCircles().stop();
    g_playerCostSecond = (0 - $(".cDiv_rememberTimer").TimeCircles().getTime()).toFixed(0);
}

function getTargetUnit4UserByBackSideVal(unitIndex, backSideVal){
    var unit = g_honeycombUnitList[unitIndex];
    var back = backSideVal;
    var front, frontLeft, frontRight, backLeft, backRight;
    if (unit.left == back){
        front = unit.right;
        frontLeft = unit.rightTop;
        frontRight = unit.rightBottom;
        backLeft = unit.leftTop;
        backRight = unit.leftBottom;
    } else if (unit.leftTop == back){
        front = unit.rightBottom;
        frontLeft = unit.right;
        frontRight = unit.leftBottom;
        backLeft = unit.rightTop;
        backRight = unit.left;
    } else if (unit.rightTop == back){
        front = unit.leftBottom;
        frontLeft = unit.rightBottom;
        frontRight = unit.left;
        backLeft = unit.right;
        backRight = unit.leftTop;
    } else if (unit.right == back){
        front = unit.left;
        frontLeft = unit.leftBottom;
        frontRight = unit.leftTop;
        backLeft = unit.rightBottom;
        backRight = unit.rightTop;
    } else if (unit.rightBottom == back){
        front = unit.leftTop;
        frontLeft = unit.left;
        frontRight = unit.rightTop;
        backLeft = unit.leftBottom;
        backRight = unit.right;
    } else if (unit.leftBottom == back){
        front = unit.rightTop;
        frontLeft = unit.leftTop;
        frontRight = unit.right;
        backLeft = unit.left;
        backRight = unit.rightBottom;
    }
    return new HoneycombUnit4User(unitIndex, front, frontRight, backRight, back, backLeft, frontLeft);
}

function showHoneycombViewToUser(){
    $("#iDiv_introduce_c").hide("slow");
    $("#iDiv_overlook_c").show("slow");
    showRememberTimer();
}

function startGame(){
    g_getGoldUnitNum = 0;
    g_userMoveStepNum = 0;
    g_glanceTimeDuringGame = 0;
    $("#iDiv_honeycombView").hide();
    $("#iDiv_userOperatePanel_c").show();
    $("#iA_restart").show();
    $("#iA_start").hide();
    $("#iA_hint").show();
}

function showHoneycomb4AMoment(){
    $("#iDiv_userOperatePanel_c").hide();
    $("#iDiv_honeycombView").show();
    setTimeout(function(){
        $("#iDiv_honeycombView").hide("fast");
        $("#iDiv_userOperatePanel_c").show("normal");
    }, 1000);
    $("#iSpan_glanceTime").text(++g_glanceTimeDuringGame);
}

function showHoneycombViewWhenFail(){
    $("#iDiv_userOperatePanel_c").hide();
    $("#iDiv_honeycombView").show();
    $("#iA_restart").show();
    $("#iA_start").hide();
    $("#iA_hint").hide();
}

function showRememberTimer(){
    $(".cDiv_rememberTimer").TimeCircles({
        time : {
            Days: {
                show: false,
                text: "天",
                color: "#FC6"
            },
            Hours: {
                show: false,
                text: "时",
                color: "#9CF"
            },
            Minutes: {
                show: true,
                text: "",
                color: "#BFB"
            },
            Seconds: {
                show: true,
                text: "",
                color: "#F99"
            }
        },
        refresh_interval: 0.1,
        count_past_zero: true,
        circle_bg_color: "#eee",
        fg_width: 0.05,
        bg_width: 1
    });
}

function showGameResult(){
    stopTimer();
    showHoneycombViewWhenFail();
    var retTitle = "<span class='cSpan_failTitle'>挑战失败</span>";
    var retDesc = "";
    if (g_gameFinishStatus == "success"){
        retTitle = "<span class='cSpan_successTitle'>挑战成功</span>";
        retDesc = "<span class='cSpan_successInfo'>难度：" + g_getGoldUnitNum + "&nbsp;星，&nbsp;&nbsp;用时：" + g_playerCostSecond + "&nbsp;秒,&nbsp;&nbsp;移动：" + g_userMoveStepNum + "&nbsp;步</span>";
    } else if (g_gameFinishStatus == "fail"){
        retDesc = "亲没有到达指定终点";
    } else if (g_gameFinishStatus == "fail2"){
        retDesc = "亲没有收集到全部的蜂蜜，就跑到了终点";
    }
    if (g_gameFinishStatus == "success"){
        $("#iA_playAgain").show();
        $("#iA_addDifficulty").show();
        $("#iP_uploadAchievement_c").show();
        $("#iP_uploadResult_c").hide();
        $("#iA_reduceDifficulty").hide();
    } else {
        $("#iA_playAgain").show();
        $("#iA_addDifficulty").hide();
        $("#iP_uploadAchievement_c").hide();
        $("#iP_uploadResult_c").hide();
        if (g_goldUnitTotalNum > 1) {
            $("#iA_reduceDifficulty").show();
        } else {
            $("#iA_reduceDifficulty").hide();
        }
    }
    ($.remodal.lookup[$('[data-remodal-id=showGameResult]').data('remodal')]).open();
    $("#iH_gameResultTitle").html(retTitle);
    $("#iSpan_resultDescription").html(retDesc);
}

function increaseDifficulty(){
    window.location.href = "/fcmg/" + (++g_goldUnitTotalNum);
}

function reduceDifficulty(){
    window.location.href = "/fcmg/" + (--g_goldUnitTotalNum);
}

function playThisDifficultyGameAgain(){
    window.location.href = "/fcmg/" + g_goldUnitTotalNum;
}

function checkNikeName(obj){
    if ($(obj).val() != ""){
        $("#iA_uploadAchievement").show();
    } else {
        $("#iA_uploadAchievement").hide();
    }
}

function uploadCurrentAchievement(){
    var uploadingHtml = "<span class=''>上传数据中...</span>";
    $("#iP_uploadAchievement_c").hide();
    $("#iP_uploadResult_c").html(uploadingHtml).show();

    var playerName = $("#iIpt_nikeName").val();
    var costSecond = g_playerCostSecond;
    var sideNum = $("#iIpt_singleSideUnitNum").val();
    var goldNum = g_goldUnitTotalNum;
    var glanceTime = g_glanceTimeDuringGame;
    var moveStepNum = g_userMoveStepNum;
    $.post("/fcmg/add_record.do", {
            "playerName": playerName,
            "costSecond": costSecond,
            "sideNum": sideNum,
            "goldNum": goldNum,
            "glanceTime": glanceTime,
            "moveStepNum": moveStepNum
        },
        function (data) {
            var html = (data == "success") ?
                "<span class='cSpan_uploadSuccess'>上传成功!</span>" : "<span class='cSpan_uploadFail'>上传失败!</span>";
            $("#iIpt_nikeName").val("");
            $("#iP_uploadResult_c").html(html);
        });
}

function showTop10RecordsModal(){
    getTop10RecordsDataAndFillData();
    ($.remodal.lookup[$('[data-remodal-id=showTop10Records]').data('remodal')]).open();
}

function getTop10RecordsDataAndFillData(){
    $.post("/fcmg/get_top10.do", { "sideNum": g_singleSideUnitNum, "goldNum": g_goldUnitTotalNum },
        function (data) {
            showTop10Records(data);
        });
}

function showTop10Records(data){
    var recordsHtml = "";
    if (data != "fail") {
        if (data != "") {
            var arr = data.split(";");
            for (var i = 0; i < arr.length; i++) {
                if (i < 10) {
                    var arr2 = arr[i].split(',');
                    recordsHtml += "<tr class='cTr_content'><td>" + (i + 1) + "</td><td>" + arr2[0] + "</td><td class='cTd_orangeText'>" + arr2[1] + "s</td><td>" + (arr2[2]=="0"?"--":arr2[2]) + "</td><td>" + (arr2[3]=="0"?"--":arr2[3]) + "</td></tr>";
                }
            }
        } else {
            recordsHtml = "<tr><td class='cTd_noRecords' colspan='5'>前无古人，后待来者</td></tr>";
        }
    } else {
        recordsHtml = "<tr><td class='cTd_noRecords' colspan='5'>获取排行榜信息失败，请稍后重试！</td></tr>";
    }
    $("#iSpan_difficulty").text("难度系数：" + g_goldUnitTotalNum + "星");
    $("#iTbl_top10Records tr:gt(0)").remove();
    $("#iTbl_top10Records tbody").append($(recordsHtml));
}

function updateDifficultyDegree(obj){
    var degree = $(obj).val();
    window.location.href = "/fcmg/" + degree;
}

