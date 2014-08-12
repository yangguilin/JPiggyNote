package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.CategoryBean;
import com.ygl.piggynote.rowmapper.CategoryRowMapper;
import com.ygl.piggynote.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * 账目分类服务实现类
 * Created by yanggavin on 14-7-18.
 */
@Service("pnCategoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 通过用户id获取分类信息
     * @param userName  用户名
     * @return          账目分类实例
     */
    @Override
    public CategoryBean get(String userName) {

        return (CategoryBean)jdbcTemplate.queryForObject("select * from pn_category where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new CategoryRowMapper());
    }

    /**
     * 为用户添加新的分类
     * @param cb    分类实例
     * @return      是否成功
     */
    @Override
    public Boolean add(CategoryBean cb) {

        int ret = jdbcTemplate.update("insert into pn_category(user_name, category_data, category_data_sorted, latest_modified_date) value(?, ?, ?, now())",
                new Object[]{cb.getUserName(), cb.getCategoryData(), cb.getCategoryDataSorted()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});

        return ret == 1;
    }

    /**
     * 更新用户分类
     * @param cb    更新后的category实例
     * @return      成败
     */
    @Override
    public Boolean update(CategoryBean cb) {

        int ret = jdbcTemplate.update("update pn_category set category_data=?, category_data_sorted=?, latest_modified_date=now() where user_name=?",
                new Object[]{cb.getCategoryData(), cb.getCategoryDataSorted(), cb.getUserName()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});

        return ret == 1;
    }
}
