/**
 * Created by yanggavin on 16/3/22.
 */
var doms;

var eventHandler = {
    init: function(){
        doms = {
            articleContent: $("#iDiv_articleContent"),
            searchedWord: $("#iIpt_searchedWord"),
            curSearchedWord: $("#iIpt_curSearchedWord")
        };
    },
    showArticleContent: function(obj){
        $(obj).addClass("cTr_selected").siblings("tr").removeClass("cTr_selected");
        var articleId = $(obj).attr("article_id");
        var searchWord = doms.curSearchedWord.val();
        if (articleId != null && articleId != "" && searchWord != null && searchWord != ""){
            $.post("/dingup/search_listening_article/get_article_content.do",
                {
                    "article_id":articleId,
                    "search_word":searchWord,
                },function(d){
                    if (d != null && d.code == 1){
                        if (d.message != ""){
                            var html = "<span>" + d.message + "</span>";
                            doms.articleContent.html(html);
                            $("body").scrollTop($('.cSpan_selected').eq(0).offset().top - 100);
                            //var topVal = $('.cSpan_selected').eq(0).offset().top - $('.cDiv_selectTranBox').offset().top - 20;
                            //$('.cDiv_selectTranBox').scrollTop(topVal);
                        }
                    } else {
                        alert("获取文章原文失败");
                    }
                })
        }
    },
    searchWord: function(){
        var searchWord = doms.searchedWord.val();
        if (searchWord != null && searchWord != ""){
            window.location.href = "/dingup/search_listening_article?search_word=" + searchWord;
        }
    },
    quickSearchWord: function(){
        var theEvent = window.event || event;
        var code = theEvent.keyCode || theEvent.which;
        if (doms.searchedWord.val() != ""){
            // 回车键
            if (code == 13){
                eventHandler.searchWord();
            }
        }
    }
};
$(function(){
    eventHandler.init();
})