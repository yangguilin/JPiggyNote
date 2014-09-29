package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.rowmapper.CustomConfigRowMapper;
import com.ygl.piggynote.service.CustomConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * 用户自定义配置服务实现类
 * Created by yanggavin on 14-7-18.
 */
@Service("pnCustomConfigService")
public class CustomConfigServiceImpl implements CustomConfigService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 通过用户名获取自定义配置实例
     * @param userName  用户名
     * @return          自定义配置实例
     */
    @Override
    public CustomConfigBean get(String userName) {

        return (CustomConfigBean)jdbcTemplate.queryForObject("select * from pn_custom_config where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new CustomConfigRowMapper());
    }

    /**
     * 添加用户自定义配置
     * @param bean  自定义配置
     * @return      是否成功
     */
    @Override
    public Boolean add(CustomConfigBean bean) {

        int ret = jdbcTemplate.update("insert into pn_custom_config(user_name, month_cost_plan, remark_amount) value(?, ?, ?)",
                new Object[]{bean.getUserName(), bean.getMonthCostPlan(), bean.getRemarkAmount()},
                new int[]{Types.VARCHAR, Types.FLOAT, Types.FLOAT});

        return ret == 1;
    }

    /**
     * 通过用户名删除用户自定义配置
     * @param userName  用户名
     * @return          是否成功
     */
    @Override
    public Boolean delete(String userName) {

        int ret = jdbcTemplate.update("delete from pn_custom_config where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR});

        return ret == 1;
    }

    /**
     * 更新用户自定义配置
     * @param bean  自定义配置
     * @return      是否成功
     */
    @Override
    public Boolean update(CustomConfigBean bean) {

        int ret = jdbcTemplate.update("update pn_custom_config set month_cost_plan=?, category_switch=?, prepay_switch=?, remark_amount=? where user_name=?",
                new Object[]{ bean.getMonthCostPlan(), bean.getCategorySwitch(), bean.getPrepaySwitch(), bean.getRemarkAmount(), bean.getUserName() },
                new int[]{Types.FLOAT, Types.TINYINT, Types.TINYINT, Types.FLOAT, Types.VARCHAR});

        return ret == 1;
    }
}
