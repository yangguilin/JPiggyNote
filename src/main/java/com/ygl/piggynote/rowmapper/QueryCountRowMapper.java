package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.QueryCountBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by guilin on 2014/8/7.
 */
public class QueryCountRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        QueryCountBean bean = new QueryCountBean();
        bean.setNum(rs.getInt("num"));

        return bean;
    }
}
