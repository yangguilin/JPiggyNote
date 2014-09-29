package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.CustomConfigBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户自定义配置bean与db数据表映射关系
 * Created by yanggavin on 14-7-18.
 */
public class CustomConfigRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        CustomConfigBean bean = new CustomConfigBean();
        bean.setUserName(rs.getString("user_name"));
        bean.setMonthCostPlan(rs.getFloat("month_cost_plan"));
        bean.setCategorySwitch(rs.getInt("category_switch"));
        bean.setPrepaySwitch(rs.getInt("prepay_switch"));
        bean.setRemarkAmount(rs.getFloat("remark_amount"));

        return bean;
    }
}
