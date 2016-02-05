package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.WeiXinInputMessageBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/6/24.
 */
public class WeiXinInputMessageRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        WeiXinInputMessageBean bean = new WeiXinInputMessageBean();
        bean.setId(rs.getInt("id"));
        bean.setMessageContent(rs.getString("message_content"));
        bean.setCreateTime(rs.getDate("create_time"));
        return bean;
    }
}
