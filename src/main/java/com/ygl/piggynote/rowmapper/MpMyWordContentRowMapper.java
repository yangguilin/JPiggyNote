package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.mp.MyWordContentBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/10/15.
 */
public class MpMyWordContentRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MyWordContentBean bean = new MyWordContentBean();
        bean.setId(rs.getInt("id"));
        bean.setUserId(rs.getInt("user_id"));
        bean.setContent(rs.getString("content"));
        bean.setCreateTime(rs.getDate("create_time"));
        return bean;
    }
}