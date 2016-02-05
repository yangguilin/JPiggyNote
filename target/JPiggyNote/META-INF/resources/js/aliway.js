/**
 * Created by yanggavin on 16/2/3.
 */

$(document).ready(function(){

});

function getPageHtmlContent(){
    var pageUrl = $("#iIpt_pageUrl").val();
    if (pageUrl != "") {
        $.get(pageUrl, null, function(d) {
            alert(d);
        });
    }
}