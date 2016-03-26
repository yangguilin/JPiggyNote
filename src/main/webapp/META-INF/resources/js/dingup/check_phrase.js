/**
 * Created by yanggavin on 16/3/3.
 */
/**
 * Created by yanggavin on 16/3/1.
 */
var doms;

var eventHandler = {
    init: function(){
        doms = {
            id:$("#iIpt_id"),
            wordName:$("#iIpt_wordName"),
            entityType:$("#iIpt_entityType"),
            examType:$("#iSel_examType"),
            examSubType:$("#iIpt_examSubType"),
            examThirdType:$("#iIpt_examThirdType"),
            originChMeaning:$("#iIpt_originChMeaning"),
            clsChMeaning:$("#iIpt_clsChMeaning"),
            clsEnMeaning:$("#iTxt_clsEnMeaning"),
            clsChEx:$("#iTxt_clsChEx"),
            clsEnEx:$("#iTxt_clsEnEx"),
            newWordName:$("#iIpt_newWordName"),
            searchedWord:$("#iIpt_searchedWord")
        };
    },
    updateWordContent: function(status){
        var id = doms.id.val();
        var wordName = doms.wordName.val();
        var examType = doms.examType.val();
        var examSubType = doms.examSubType.val();
        var examThirdType = doms.examThirdType.val();
        var originChMeaning = doms.originChMeaning.val();
        var clsChMeaning = doms.clsChMeaning.val();
        var clsEnMeaning = doms.clsEnMeaning.val();
        var clsChEx = doms.clsChEx.val();
        var clsEnEx = doms.clsEnEx.val();
        var status = (status == null) ? "DEFAULT" : status;
        if (id != "" && wordName != "" && examType != "" && originChMeaning != ""){
            $.post("/dingup/update_phrase.do",
                {
                    "id":id,
                    "phrase":wordName,
                    "examType":examType,
                    "examSubType":examSubType,
                    "examThirdType":examThirdType,
                    "originChMeaning":originChMeaning,
                    "clsEnExTran":clsChMeaning,
                    "clsEnMeaning":clsEnMeaning,
                    "clsChEx":clsChEx,
                    "clsEnEx":clsEnEx,
                    "status":status
                },function(d){
                    if (d != null && d.code == 1){
                        if (d.message == "success"){
                            eventHandler.reloadCurrentPage();
                        }
                    } else {
                        alert("更新失败");
                    }
                })
        }
    },
    updateRecordTime:function(){
        var id = doms.id.val();
        var wordName = doms.wordName.val();
        if (id != "" && wordName != ""){
            $.post("/dingup/update_phrase_record_time.do",
                {
                    "id":id,
                    "wordName":wordName,
                },function(d){
                    if (d != null && d.code == 1){
                        if (d.message == "success"){
                            eventHandler.reloadCurrentPage();
                        }
                    } else {
                        alert("更新失败");
                    }
                });
        }
    },
    beautifulClsEnMeaningContent: function(){
        doms.clsEnMeaning.html(replaceText(doms.clsEnMeaning.text()));
    },
    beautifulPageEnContent: function(){
        var regPre = new RegExp('\\[c\\]', 'ig');
        var regSuf = new RegExp('\\[\/c\\]', 'ig');
        // 1. collins english meaning part.
        doms.clsEnMeaning.text(removeExtraText(doms.clsEnMeaning.text()));
        // 2. collins search result item part.
        var $arr = $(".cTd_def");
        if ($arr.length > 1){
            for (var i = 0; i < $arr.length; i++){
                $arr[i].innerHTML = replaceText($arr[i].innerText);
            }
        } else if ($arr.length == 1) {
            $arr.html(replaceText($arr.text()));
        }
        function replaceText(originText){
            return originText.replace(regPre, "&nbsp;<span class='cSpan_tranWord'>").replace(regSuf, "</span>&nbsp;");
        }
        function removeExtraText(originText){
            return originText.replace(regPre, "").replace(regSuf, "");
        }
    },
    fillDataToLeftForm: function(obj){
        var $trs = $(obj).children("tbody").children("tr");
        var def = removeExtraBlank($trs.children("td.cTd_def").text());
        var tran = $trs.children("td.cTd_tran").text();
        var $exampleEx = $trs.children("td.cTd_example_ex");
        var $exampleTran = $trs.children("td.cTd_example_tran");
        doms.clsChMeaning.val(tran);
        doms.clsEnMeaning.text(def);
        if ($exampleEx != null) {
            doms.clsEnEx.text($exampleEx.text());
        }
        if ($exampleTran != null) {
            doms.clsChEx.text($exampleTran.text());
        }

        function removeExtraBlank(originText){
            return originText.replace("  ", " ").replace("  ", " ");
        }
        function removeArrayTags(originText){
            var regex = new RegExp('[\\[|\\]\"]', 'ig');
            return originText.replace(regex, "").trim();
        }
    },
    showModifyWordNameBar: function(obj){
        $(obj).hide().siblings("div.cDiv_hidden").show();
    },
    modifyWordName: function(obj){
        var id = doms.id.val();
        var oWordName = doms.wordName.val();
        var nWordName = doms.newWordName.val();
        if (id != "" && nWordName != "" && oWordName != ""){
            $.post("/dingup/update_phrase_name.do",
                {
                    "id":id,
                    "oldWordName":oWordName,
                    "newWordName":nWordName,
                },function(d){
                    if (d != null && d.code == 1){
                        if (d.message == "success"){
                            eventHandler.reloadCurrentPage();
                        }
                    } else {
                        alert("更新失败");
                    }
                });
        }
    },
    resetWordName: function(obj){
        $(obj).parent().hide().siblings("span").show();
    },
    searchWord: function(obj){
        var word = doms.searchedWord.val();
        if (word != null && word != ""){
            window.location.href = "/dingup/check_phrase?search_phrase=" + word;
        }
    },
    showLatestUpdateWord: function(){
        window.location.href = "/dingup/check_phrase?latest_update_index=1";
    },
    reloadCurrentPage: function(){
        window.location.href = "/dingup/check_phrase";
    }
};

$(function(){
    eventHandler.init();
    eventHandler.beautifulPageEnContent();
})