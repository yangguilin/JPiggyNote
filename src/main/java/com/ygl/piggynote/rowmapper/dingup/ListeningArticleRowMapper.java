package com.ygl.piggynote.rowmapper.dingup;

import com.ygl.piggynote.bean.dingup.ListeningArticle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 16/3/24.
 */
public class ListeningArticleRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ListeningArticle bean = new ListeningArticle();
        bean.setId(rs.getInt("id"));
        bean.setSubjectName(rs.getString("subject_name"));
        bean.setArticleType(rs.getString("article_type"));
        bean.setArticleNum(rs.getInt("article_num"));
        bean.setArticleTitle(rs.getString("article_title"));
        bean.setArticleContent(rs.getString("article_content"));
        bean.setCreateTime(rs.getDate("create_time"));
        return bean;
    }
}