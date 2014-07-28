package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.CityBean;
import com.ygl.piggynote.rowmapper.CityRowMapper;
import com.ygl.piggynote.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 14-7-17.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void save(CityBean cityBean) {

        jdbcTemplate.update("insert into city(name, countrycode, population) value(?, ?, ?)",
                new Object[]{cityBean.getName(), cityBean.getCountrycode(), cityBean.getPopulation()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
    }

    @Override
    public void update(CityBean cityBean) {

        jdbcTemplate.update("update city set name=?, countrycode=?, population=? where id=?",
                new Object[]{cityBean.getName(), cityBean.getCountrycode(), cityBean.getPopulation(), cityBean.getId()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
    }

    @Override
    public void delete(int id) {

        jdbcTemplate.update("delete from city where id=?",
                new Object[]{id},
                new int[]{Types.INTEGER});
    }

    @Override
    public CityBean getCity(int id) {

        return (CityBean)jdbcTemplate.queryForObject("select * from city where id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new CityRowMapper());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CityBean> getCityBean() {

        return (List<CityBean>)jdbcTemplate.query("select * from city",
                new CityRowMapper());
    }
}

