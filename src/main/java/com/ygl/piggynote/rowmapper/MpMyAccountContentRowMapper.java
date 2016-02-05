package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.mp.MyAccountContentBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/10/22.
 */
public class MpMyAccountContentRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MyAccountContentBean bean = new MyAccountContentBean();
        bean.setId(rs.getInt("id"));
        bean.setUserId(rs.getInt("user_id"));
        bean.setContent(rs.getString("content"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setShowName(rs.getString("show_name"));
        return bean;
    }
}