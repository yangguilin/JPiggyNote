package com.ygl.snwt.rowmapper;

import com.ygl.snwt.bean.NwtUser;
import com.ygl.snwt.enums.AppTypeEnum;
import com.ygl.snwt.enums.SocietyDeptEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 你我他用户bean与db数据表映射关系
 * Created by yanggavin on 14/12/12.
 */
public class NwtUserRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        NwtUser bean = new NwtUser();
        bean.setId(rs.getInt("id"));
        bean.setName(rs.getString("name"));
        bean.setYear(rs.getString("year"));
        bean.setMobilePhone(rs.getString("mobile_phone"));
        bean.setWeiXin(rs.getString("weixin"));
        bean.setQq(rs.getString("qq"));
        bean.setEmail(rs.getString("email"));
        bean.setCity(rs.getString("city"));
        bean.setJob(rs.getString("job"));
        bean.setCompany(rs.getString("company"));
        bean.setHometown(rs.getString("hometown"));
        bean.setLastLoginTime(rs.getDate("last_login_time"));
        bean.setAuth(rs.getBoolean("auth"));
        bean.setRegisterTime(rs.getDate("register_time"));
        bean.setSocietyDept(SocietyDeptEnum.getEnumByValue(rs.getString("society_dept")));
        bean.setAppType(AppTypeEnum.getEnumByValue(rs.getString("app_type")));
        bean.setAuthCode(rs.getString("auth_code"));
        bean.setNikeName(rs.getString("nike_name"));
        bean.setPassword(rs.getString("password"));
        bean.setDeleted(rs.getBoolean("deleted"));

        return bean;
    }
}
