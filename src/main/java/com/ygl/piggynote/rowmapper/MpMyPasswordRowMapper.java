package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.mp.MyPasswordBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/10/16.
 */
public class MpMyPasswordRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MyPasswordBean bean = new MyPasswordBean();
        bean.setId(rs.getInt("id"));
        bean.setUserId(rs.getInt("user_id"));
        bean.setPassword(rs.getString("password"));
        bean.setShowName(rs.getString("show_name"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setLatestUpdateTime(rs.getDate("latest_update_time"));
        bean.setAccountId(rs.getInt("account_id"));
        return bean;
    }
}