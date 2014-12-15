package com.ygl.snwt.service.impl;

import com.ygl.snwt.bean.NwtUser;
import com.ygl.snwt.rowmapper.NwtUserRowMapper;
import com.ygl.snwt.service.NwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sun.rmi.rmic.iiop.Type;

import java.sql.Types;

/**
 * Created by yanggavin on 14/12/12.
 */
@Service("nwtUserService")
public class NwtUserServiceImpl implements NwtUserService {

    @Autowired
    private JdbcTemplate jdbcTemplateSnwt;


    /**
     * 通过userId获取用户实例
     * @param userId    userId
     * @return  用户实例
     */
    @Override
    public NwtUser get(int userId) {

        return (NwtUser)jdbcTemplateSnwt.queryForObject("select * from user where id=?",
                new Object[]{userId},
                new int[]{Type.INT},
                new NwtUserRowMapper());
    }

    /**
     * 添加新用户
     * @param user  user
     * @return  是否成功
     */
    @Override
    public Boolean add(NwtUser user) {

        int ret = jdbcTemplateSnwt.update("insert into user(name, password, year, mobile_phone, auth, register_time, deleted) value(?, ?, ?, ?, 0, now(), 0)",
                new Object[]{user.getName(), user.getPassword(), user.getYear(), user.getMobilePhone()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});

        return ret == 1;
    }

    /**
     * 更新用户信息
     * @param user  user
     * @return  是否成功
     */
    @Override
    public Boolean update(NwtUser user) {

        int ret = jdbcTemplateSnwt.update("update user set name=?, year=?, mobile_phone=?, weixin=?, qq=?, email=?, city=?, job=?, company=?, hometown=?, last_login_time=now(), auth=?, society_dept=?, app_type=?, auth_code=?, nike_name=?, password=?, deleted=? where id=?",
                new Object[]{user.getName(), user.getYear(), user.getMobilePhone(), user.getWeiXin(), user.getQq(), user.getEmail(), user.getCity(), user.getJob(), user.getCompany(), user.getHometown(), user.getAuth(), user.getSocietyDept(), user.getAppType(), user.getAuthCode(), user.getNikeName(), user.getPassword(), user.getDeleted(), user.getId()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.INTEGER});

        return ret > 0;
    }

    /**
     * 清除用户
     * @param userId
     * @return  是否成功
     */
    @Override
    public Boolean delete(int userId) {

        int ret = jdbcTemplateSnwt.update("update user set deleted=1 where id=?",
                new Object[]{userId},
                new int[]{Types.INTEGER});

        return ret > 0;
    }
}
