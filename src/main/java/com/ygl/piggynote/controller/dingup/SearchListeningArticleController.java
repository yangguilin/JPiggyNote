package com.ygl.piggynote.controller.dingup;

import com.ygl.piggynote.bean.dingup.ListeningArticle;
import com.ygl.piggynote.service.dingup.SListeningArticleCache;
import com.ygl.piggynote.service.dingup.impl.ListeningArticleServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yanggavin on 16/3/22.
 */
@Controller
@RequestMapping("/dingup/search_listening_article")
public class SearchListeningArticleController {
    @Autowired
    private ListeningArticleServiceImpl listeningArticleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request){
        HashMap<Integer, ListeningArticle> searchMap = new HashMap<Integer, ListeningArticle>();
        String searchWord = request.getParameter("search_word");
        if (searchWord != null){
            searchMap = SListeningArticleCache.getInstance().searchArticleByWord(searchWord, listeningArticleService);
        }

        model.addAttribute("totalNum", SListeningArticleCache.getInstance().getTotalArticleNum());
        model.addAttribute("searchNum", searchMap.size());
        model.addAttribute("resultList", searchMap.values());
        model.addAttribute("searchWord", searchWord);
        return "dingup/search_listening_article";
    }

    @RequestMapping(value = "/get_article_content.do", method = RequestMethod.POST)
    public void getArticleContent(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try {
            String articleId = request.getParameter("article_id");
            String searchWord = request.getParameter("search_word");
            if (articleId != null && !articleId.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
                content = SListeningArticleCache.getInstance().getArticleContentById(Integer.parseInt(articleId));
                content = beautifulArticleContent(content, searchWord);
                ret = content != null;
            }
        } catch (Exception e){
            content = "获取文章内容失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    private String beautifulArticleContent(String articleContent, String searchWord){
        String retContent = "";
        if (articleContent != null && !articleContent.isEmpty()
                && searchWord != null && !searchWord.isEmpty()){
            retContent = articleContent.trim();
            retContent = getHtmlStr(retContent, searchWord);
            String upperSearchWord = searchWord.substring(0, 1).toUpperCase() + searchWord.substring(1);
            retContent = getHtmlStr(retContent, upperSearchWord);
            retContent = retContent.replaceAll("\\n\\n", "\\n");
            List<String> paraList = Arrays.asList(retContent.split("\\n"));
            StringBuilder sb = new StringBuilder();
            for (String para : paraList){
                sb.append("<p class='cP_show'>" + para + "</p>");
            }
            retContent = sb.toString();
        }
        return retContent;
    }

    private String getHtmlStr(String content, String word){
        return content.replaceAll("[\\s]?" + word + "[\\s]?", "<span class='cSpan_selected'>" + word + "</span>");

    }
}
