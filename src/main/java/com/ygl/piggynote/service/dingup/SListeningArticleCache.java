package com.ygl.piggynote.service.dingup;

import com.ygl.piggynote.bean.dingup.ListeningArticle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * Created by yanggavin on 16/3/24.
 */
public class SListeningArticleCache {
    private static class LazyHolder {
        private static final SListeningArticleCache INSTANCE = new SListeningArticleCache();
    }

    public static SListeningArticleCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final ConcurrentMap<Integer, ListeningArticle> articleMap = new ConcurrentHashMap<Integer, ListeningArticle>();
    private SListeningArticleCache() { }

    public Integer getTotalArticleNum(){
        return articleMap.size();
    }

    public HashMap<Integer, ListeningArticle> searchArticleByWord(String word, ListeningArticleService listeningArticleService){
        HashMap<Integer, ListeningArticle> retMap = new HashMap<Integer, ListeningArticle>();
        // check and load data.
        checkAndLoadData(listeningArticleService);
        // search word in all articles.
        ListeningArticle curLa;
        for (Map.Entry<Integer, ListeningArticle> entry : articleMap.entrySet()){
            curLa = entry.getValue();
            Pattern pattern = Pattern.compile("[\\s]?" + word + "[\\s]?", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(curLa.getArticleContent()).find()){
                ListeningArticle la = new ListeningArticle();
                la.setId(curLa.getId());
                la.setSubjectName(curLa.getSubjectName());
                la.setArticleNum(curLa.getArticleNum());
                la.setArticleType(curLa.getArticleType());
                la.setArticleTitle(curLa.getArticleTitle());
                retMap.put(entry.getKey(), la);
            }
        }
        return retMap;
    }

    public String getArticleContentById(Integer id){
        if (articleMap.containsKey(id)){
            return articleMap.get(id).getArticleContent();
        }
        return null;
    }

    private void checkAndLoadData(ListeningArticleService listeningArticleService){
        if (articleMap.size() == 0 && listeningArticleService != null) {
            List<ListeningArticle> list = listeningArticleService.getAll();
            for (ListeningArticle la : list) {
                articleMap.put(la.getId(), la);
            }
        }
    }
}
