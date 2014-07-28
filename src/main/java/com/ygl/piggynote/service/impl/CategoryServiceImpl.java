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
    public CategoryBean getByUserName(String userName) {

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

        int ret = jdbcTemplate.update("insert into pn_category(user_name, category_xml, category_xml_sorted, latest_modified_date) value(?, ?, ?, ?)",
                new Object[]{cb.getUserName(), cb.getCategoryXml(), cb.getCategoryXmlSorted(), cb.getLatestModifiedDate()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE});

        return ret == 1;
    }

    /**
     * 删除用户分类
     * @param userName  用户名
     * @return          是否成功
     */
    @Override
    public Boolean deleteByUserName(String userName) {

        int ret = jdbcTemplate.update("delete from pn_category where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR});

        return ret == 1;
    }

    @Override
    public Boolean update(CategoryBean cb) {

        int ret = jdbcTemplate.update("update pn_category set category_xml=?, category_xml_sorted=?, latest_modified_date=? where user_name=?",
                new Object[]{cb.getCategoryXml(), cb.getCategoryXmlSorted(), cb.getLatestModifiedDate(), cb.getUserName()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR});

        return ret == 1;
    }
}
