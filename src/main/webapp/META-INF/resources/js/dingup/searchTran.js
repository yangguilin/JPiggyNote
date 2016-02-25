/**
 * Created by yanggavin on 16/2/24.
 */

$(function(){
    init();
});

var init = function(){
    $("#iIpt_btn").click(events.searchTranFromRemoteServer);
};

var events = {
    searchTranFromRemoteServer: function(){
        var url = "http://www.topschool.com/aj/collins/fetchword?word=animal";
        $.get(url, null, function(d){
            if (d != null){
                alert(d);
            }
        },"json"
        );
    }
};
