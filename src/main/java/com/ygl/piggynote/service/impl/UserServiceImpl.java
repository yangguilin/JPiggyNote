package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.rowmapper.UserRowMapper;
import com.ygl.piggynote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * 用户服务实现类
 * Created by yanggavin on 14-7-18.
 */
@Service("pnUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 通过用户名获取用户实例
     * @param userName  用户名
     * @return          用户实例
     */
    @Override
    public UserBean get(String userName) {
        return (UserBean)jdbcTemplate.queryForObject("select * from pn_users where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new UserRowMapper());
    }

    /**
     * 添加新用户
     * @param bean  用户实例
     * @return      是否成功
     */
    @Override
    public Boolean add(UserBean bean) {
        int ret = jdbcTemplate.update("insert into pn_users(user_name, password, nike_name, email, mobile_phone, create_date, latest_login_date) value(?, ?, ?, ?, ?, now(), ?)",
                new Object[]{bean.getUserName(), bean.getPassword(), bean.getNikeName(), bean.getEmail(), bean.getMobilePhone(), bean.getLatestLoginDate()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE});
        return ret == 1;
    }

    /**
     * 删除用户
     * @param userName      用户名
     * @return              是否成功
     */
    @Override
    public Boolean delete(String userName) {
        int ret = jdbcTemplate.update("update pn_users set deleted=1 where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR});
        return ret > 0;
    }

    /**
     * 更新用户信息
     * @param bean  用户实例
     * @return      是否成功
     */
    @Override
    public Boolean update(UserBean bean) {
        int ret = jdbcTemplate.update("update pn_users set password=?, nike_name=?, email=?, mobile_phone=?, latest_login_date=? where user_name=?",
                new Object[]{ bean.getPassword(), bean.getNikeName(), bean.getEmail(), bean.getMobilePhone(), bean.getLatestLoginDate(), bean.getUserName()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR});
        return ret > 0;
    }

    /**
     * 用户是否存在
     * @param userName  用户名
     * @return  是否存在
     */
    @Override
    public Boolean exist(String userName) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(id) as num from pn_users where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }
}
