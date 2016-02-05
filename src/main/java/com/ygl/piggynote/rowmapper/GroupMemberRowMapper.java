package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.GroupMemberBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/3/13.
 */
public class GroupMemberRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        GroupMemberBean bean = new GroupMemberBean();
        bean.setId(rs.getInt("id"));
        bean.setUserName(rs.getString("user_name"));
        bean.setGroupId(rs.getInt("group_id"));
        return bean;
    }
}
