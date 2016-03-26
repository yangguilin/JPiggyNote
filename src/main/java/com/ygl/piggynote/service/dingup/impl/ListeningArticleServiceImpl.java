package com.ygl.piggynote.service.dingup.impl;

import com.ygl.piggynote.bean.dingup.ListeningArticle;
import com.ygl.piggynote.rowmapper.dingup.ListeningArticleRowMapper;
import com.ygl.piggynote.service.dingup.ListeningArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 16/3/24.
 */
@Service("dpListeningArticleService")
public class ListeningArticleServiceImpl implements ListeningArticleService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ListeningArticle> getAll() {
        return (List<ListeningArticle>)jdbcTemplate.query("select * from dp_listening_article",
                new Object[]{},
                new int[]{},
                new ListeningArticleRowMapper());
    }

    @Override
    public ListeningArticle get(Integer id){
        return (ListeningArticle)jdbcTemplate.queryForObject("select * from dp_listening_article where id=?",
                new Object[]{ id },
                new int[]{Types.INTEGER },
                new ListeningArticleRowMapper());
    }
}
