package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.StockCookieBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/2/9.
 */
public class StockCookieRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        StockCookieBean bean = new StockCookieBean();
        bean.setUserName(rs.getString("user_name"));
        bean.setStockCookie(rs.getString("stock_cookie"));
        bean.setSavedDate(rs.getDate("saved_date"));
        bean.setQuickCookie(rs.getString("quick_cookie"));
        return bean;
    }
}
