package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.WeiXinTokenBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/6/24.
 */
public class WeiXinLogRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        WeiXinTokenBean bean = new WeiXinTokenBean();
        bean.setId(rs.getInt("id"));
        bean.setSignature(rs.getString("signature"));
        bean.setTimestamp(rs.getString("timestamp"));
        bean.setNonce(rs.getString("nonce"));
        bean.setEchostr(rs.getString("echostr"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setRequestType(rs.getString("request_type"));
        return bean;
    }
}
