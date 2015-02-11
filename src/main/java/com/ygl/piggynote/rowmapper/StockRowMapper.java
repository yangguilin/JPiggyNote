package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.StockBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/2/9.
 */
public class StockRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        StockBean bean = new StockBean();
        bean.setId(rs.getInt("id"));
        bean.setUserId(rs.getInt("user_id"));
        bean.setStockCode(rs.getString("stock_code"));
        bean.setStockName(rs.getString("stock_name"));
        bean.setBuyPrice(rs.getFloat("buy_price"));
        bean.setGainPrice(rs.getFloat("gain_price"));
        bean.setLosePrice(rs.getFloat("lose_price"));
        bean.setBuyDate(rs.getDate("buy_date"));
        return bean;
    }
}
