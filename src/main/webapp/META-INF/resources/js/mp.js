
function searchShowName(){
    var showName = $("#iIpt_searchContent").val();
    if (showName == "") return;

    $.post(
        "/mp/search.do",
        { "userId":"112", "showName":showName },
        function (data) {
            if (data.code == "1") {
                var resultArr = data.message;
                if (resultArr.length > 0){
                    var result = "";
                    for( var item in resultArr){
                        result += resultArr[item].showName + " " + resultArr[item].passwordTip;
                    }
                    alert(result);
                }
            } else {
                alert("查询失败，请重试");
            }
        }
    );
}