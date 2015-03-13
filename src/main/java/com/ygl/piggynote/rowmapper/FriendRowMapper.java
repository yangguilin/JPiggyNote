package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.FriendBean;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/3/12.
 */
public class FriendRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        FriendBean bean = new FriendBean();
        bean.setId(rs.getInt("id"));
        bean.setUserName(rs.getString("user_name"));
        bean.setFriendName(rs.getString("friend_name"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setStatus(rs.getInt("status"));
        return bean;
    }
}
