package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.CityBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 14-7-17.
 */
public class CityRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        CityBean cb = new CityBean();
        cb.setId(resultSet.getInt("id"));
        cb.setName(resultSet.getString("name"));
        cb.setCountrycode(resultSet.getString("countrycode"));
        cb.setDistrict(resultSet.getString("district"));
        cb.setPopulation(resultSet.getInt("population"));

        return cb;
    }
}
