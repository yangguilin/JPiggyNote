package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.GroupBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/3/17.
 */
public class GroupRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        GroupBean bean = new GroupBean();
        bean.setGroupId(rs.getInt("group_id"));
        bean.setGroupName(rs.getString("group_name"));
        bean.setActiveNum(rs.getInt("active_num"));
        bean.setDeleted(rs.getBoolean("deleted"));
        return bean;
    }
}
