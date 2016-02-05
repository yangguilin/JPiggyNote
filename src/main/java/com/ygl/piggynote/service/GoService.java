package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.go.ArticleBean;
import com.ygl.piggynote.bean.go.MonsterBean;

import java.util.List;

/**
 * Created by yanggavin on 15/9/1.
 */
public interface GoService {
    List<MonsterBean> loadMonsterData();
    List<ArticleBean> loadArticleData();
}
