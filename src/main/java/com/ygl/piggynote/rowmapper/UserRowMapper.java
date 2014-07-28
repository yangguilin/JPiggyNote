package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.UserBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户bean与db数据表映射关系
 * Created by yanggavin on 14-7-18.
 */
public class UserRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        UserBean bean = new UserBean();
        bean.setId(rs.getInt("id"));
        bean.setEmail(rs.getString("email"));
        bean.setNikeName(rs.getString("nike_name"));
        bean.setUserName(rs.getString("user_name"));
        bean.setPassword(rs.getString("password"));
        bean.setMobilePhone(rs.getString("mobile_phone"));
        bean.setCreateDate(rs.getDate("create_date"));
        bean.setLatestLoginDate(rs.getDate("latest_login_date"));

        return bean;
    }
}
