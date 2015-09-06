/**
 * Created by guilin on 2014/8/1.
 */

function addCustomConfig(){

    var userName = "yanggl2";
    var monthCostPlan = 5000;

    // 调用ajax请求
    $.post("../custom_config/add", { "userName": userName , "monthCostPlan":monthCostPlan },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function updateCustomConfig(){

    var userName = "yanggl2";
    var monthCostPlan = 5001;

    // 调用ajax请求
    $.post("../custom_config/update", { "userName": userName , "monthCostPlan":monthCostPlan },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function resetCustomConfig(){

    var userName = "yanggl2";

    // 调用ajax请求
    $.post("../custom_config/reset", { "userName": userName },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function addRecord(){

    var userName = "yanggl2";
    var moneyType = "COST";
    var statType = "NORMAL";
    var categoryId = "1";
    var categoryName = "食物";
    var amount = 100;
    var remark = "test";

    // 调用ajax请求
    $.post("../daily_record/add", { "userName": userName , "moneyType":moneyType, "statType":statType,
            "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

/**
 * 带编码格式的参数
 */
function addRecord2(){

    var userName = "yanggl2";
    var moneyType = "COST";
    var statType = "NORMAL";
    var categoryId = "1";
    var categoryName = encodeURI("食物");
    var amount = 100;
    var remark = "test";

    // 调用ajax请求
    $.post("../daily_record/add", { "userName": userName , "moneyType":moneyType, "statType":statType,
            "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function updateRecord(){

    var userName = "yanggl2";
    var moneyType = "COST";
    var statType = "NORMAL";
    var categoryId = "1";
    var categoryName = "食物";
    var amount = 101;
    var remark = "test update";

    // 调用ajax请求
    $.post("../daily_record/update", { "userName": userName , "moneyType":moneyType, "statType":statType, "categoryId":categoryId, "categoryName":categoryName, "amount":amount, "remark":remark },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function deleteRecord(){

    var userName = "yanggl2";
    var id = 2346;

    // 调用ajax请求
    $.post("../daily_record/delete", { "userName": userName, "id":id },
        function (data) {
            if (data == "success") {
                alert("操作成功!")
            } else {
                alert("操作失败！" + data);
            }
        });
}

function sendWeiXinPostMsg() {
    var url1 = "../weixin?signature=4831bfd4d48f5730c3f44a5f0011b54d9a2a6f3e&timestamp=1435222462&nonce=1847001623";
    var url2 = "http://www.piggylife.com/weixin?signature=4831bfd4d48f5730c3f44a5f0011b54d9a2a6f3e&timestamp=1435222462&nonce=1847001623";
    var xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><xml><URL><![CDATA[http://localhost:8080/weixin]]></URL><ToUserName><![CDATA[stxg2015]]></ToUserName><FromUserName><![CDATA[11111]]></FromUserName><CreateTime>1435199696</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[aaa]]></Content><MsgId>1234567</MsgId></xml>";
    var userXmlData = "<xml><ToUserName><![CDATA[gh_3be816dd0023]]></ToUserName><FromUserName><![CDATA[oFdYyt7lkbvdNIMr3ssF-1ufgdco]]></FromUserName><CreateTime>1435899493</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[#账号关联#ygl:123]]></Content><MsgId>6167141362987789850</MsgId></xml>";
    var costXmlData = "<xml><ToUserName><![CDATA[gh_3be816dd0023]]></ToUserName><FromUserName><![CDATA[oFdYyt7lkbvdNIMr3ssF-1ufgdco]]></FromUserName><CreateTime>1435904770</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[¥60]]></Content><MsgId>6167164027530213425</MsgId></xml>";
    var helpXmlData = "<xml><ToUserName><![CDATA[gh_3be816dd0023]]></ToUserName><FromUserName><![CDATA[oFdYyt5tStjh0OHQTHFk47GYFOpg]]></FromUserName><CreateTime>1435828256</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[#帮助#]]></Content><MsgId>6166835402402493623</MsgId></xml>";
    var showXmlData = "<xml><ToUserName><![CDATA[gh_3be816dd0023]]></ToUserName><FromUserName><![CDATA[oFdYyt5tStjh0OHQTHFk47GYFOpg]]></FromUserName><CreateTime>1435828256</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[#记录#]]></Content><MsgId>6166835402402493623</MsgId></xml>";

    $.ajax({
        type: "POST",
        dataType: 'xml',
        url: url1,
        processData: false,
        data: costXmlData,
        async: true,
        success: function (d) {
            alert("ok");
        }
    });

}