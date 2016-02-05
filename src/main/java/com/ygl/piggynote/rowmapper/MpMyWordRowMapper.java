package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.mp.MyWordBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/10/15.
 */
public class MpMyWordRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MyWordBean bean = new MyWordBean();
        bean.setId(rs.getInt("id"));
        bean.setUserId(rs.getInt("user_id"));
        bean.setShowName(rs.getString("show_name"));
        bean.setContentId(rs.getInt("content_id"));
        bean.setCreateTime(rs.getDate("create_time"));
        return bean;
    }
}