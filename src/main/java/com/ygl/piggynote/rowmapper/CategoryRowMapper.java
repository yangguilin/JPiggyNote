package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.CategoryBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 账目分类bean与db数据表映射关系
 * Created by yanggavin on 14-7-18.
 */
public class CategoryRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        CategoryBean bean = new CategoryBean();
        bean.setUserName(rs.getString("user_name"));
        bean.setCategoryData(rs.getString("category_data"));
        bean.setCategoryDataSorted(rs.getString("category_data_sorted"));
        bean.setLatestModifiedDate(rs.getDate("latest_modified_date"));

        return bean;
    }
}
