package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.*;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.enums.StatTypeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 账目记录bean与db数据表映射关系
 * Created by yanggavin on 14-7-18.
 */
public class DailyRecordRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        DailyRecordBean bean = new DailyRecordBean();
        bean.setId(rs.getInt("id"));
        bean.setUserName(rs.getString("user_name"));
        bean.setMoneyType(MoneyTypeEnum.getEnumByValue(rs.getString("money_type")));
        bean.setStatType(StatTypeEnum.getEnumByValue(rs.getString("stat_type")));
        bean.setCategoryId(rs.getString("category_id"));
        bean.setCategoryName(rs.getString("category_name"));
        bean.setAmount(rs.getFloat("amount"));
        bean.setRemark(rs.getString("remark"));
        bean.setCreateDate(rs.getDate("create_date"));
        bean.setLatestModifiedDate(rs.getDate("latest_modified_date"));

        return bean;
    }
}
