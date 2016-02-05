package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.GroupMemberBean;
import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.rowmapper.GroupMemberRowMapper;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 15/3/13.
 */
@Service("pn_groupMemberService")
public class GroupMemberServiceImpl implements GroupMemberService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupMemberBean> getListByUserName(String userName) {
        return (List<GroupMemberBean>)jdbcTemplate.query("select * from pn_group_members where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new GroupMemberRowMapper());
    }

    @Override
    public List<GroupMemberBean> getListByGroupId(int groupId) {
        return (List<GroupMemberBean>)jdbcTemplate.query("select * from pn_group_members where group_id=?",
                new Object[]{groupId},
                new int[]{Types.INTEGER},
                new GroupMemberRowMapper());
    }

    @Override
    public Boolean add(GroupMemberBean bean) {
        int ret = jdbcTemplate.update("insert into pn_group_members(user_name, group_id) value(?, ?)",
                new Object[]{bean.getUserName(), bean.getGroupId()},
                new int[]{Types.VARCHAR, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public Boolean update(GroupMemberBean bean) {
        return null;
    }

    @Override
    public Boolean delete(String userName, int groupId) {
        int ret = jdbcTemplate.update("delete from pn_group_members where user_name=? and group_id=?",
                new Object[]{userName, groupId},
                new int[]{Types.VARCHAR, Types.INTEGER});
        return ret > 0;
    }

    @Override
    public Boolean exist(String userName, int groupId) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(id) as num from pn_group_members where user_name=? and group_id=?",
                new Object[]{userName, groupId},
                new int[]{Types.VARCHAR, Types.INTEGER},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }
}
