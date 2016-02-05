package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.WeiXinUserMappingBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/7/2.
 */
public class WeiXinUserMappingRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        WeiXinUserMappingBean bean = new WeiXinUserMappingBean();
        bean.setId(rs.getInt("id"));
        bean.setWeiXinUserName(rs.getString("weixin_user_name"));
        bean.setPiggyUserName(rs.getString("piggy_user_name"));
        bean.setCreateTime(rs.getDate("create_time"));
        return bean;
    }
}
