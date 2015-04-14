


/**
 * 退出登录
 */
function logout(){

    var userName = $("#hidden_userName").val();
    if (userName == null || userName == ""){ return; }

    // 调用ajax请求
    $.post("/logout.do", { "userName": userName },
        function (data) {

            if (data == "success") {

                alert("成功退出");
                window.location.href = "/";
            } else {
                alert("退出失败");
            }
        });
}

/**
 * 检查字符串是否为合法的数值金额
 * @param amount    金额字符
 */
function checkAmountVal(amount){
    return (amount != "" && !isNaN(amount) && parseInt(amount) > 0);
}

function reloadCurrentPage(){
    window.location.reload();
}

Date.prototype.format =function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length==1? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

String.prototype.toNumber = function(){
    var num = 0;
    try {
        num = Number(this);
    }catch(e){
        num = -1;
    }
    return num;
}

String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function arrayCount(o){
    var t = typeof o;
    if(t == 'string'){
        return o.length;
    }else if(t == 'object'){
        var n = 0;
        for(var i in o){n++;}
        return n;
    }
    return false;
}