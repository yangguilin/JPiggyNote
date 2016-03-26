package com.ygl.piggynote.service.dingup;

import com.ygl.piggynote.bean.dingup.ListeningArticle;

import java.util.List;

/**
 * Created by yanggavin on 16/3/24.
 */
public interface ListeningArticleService {
    List<ListeningArticle> getAll();
    ListeningArticle get(Integer id);
}
