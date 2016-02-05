package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.bean.WeiXinInputMessageBean;
import com.ygl.piggynote.bean.WeiXinTokenBean;
import com.ygl.piggynote.bean.WeiXinUserMappingBean;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.rowmapper.WeiXinInputMessageRowMapper;
import com.ygl.piggynote.rowmapper.WeiXinUserMappingRowMapper;
import com.ygl.piggynote.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 15/6/23.
 */
@Service("weixinService")
public class WeiXinServiceImpl implements WeiXinService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<WeiXinInputMessageBean> getAll() {
        return (List<WeiXinInputMessageBean>)jdbcTemplate.query("select * from pn_weixin_message order by create_date desc",
                new Object[]{},
                new int[]{},
                new WeiXinInputMessageRowMapper());
    }

    @Override
    public Boolean addWeiXinMsg(WeiXinInputMessageBean bean) {
        int ret = jdbcTemplate.update("insert into pn_weixin_message(message_content, create_time) value(?, now())",
                new Object[]{bean.getMessageContent()},
                new int[]{Types.VARCHAR});
        return ret == 1;
    }

    @Override
    public Boolean deleteWeiXinMsg(int id) {
        int ret = jdbcTemplate.update("delete from pn_weixin_message where id=?",
                new Object[]{id},
                new int[]{Types.INTEGER});
        return ret > 0;
    }

    @Override
    public Boolean addWeiXinLog(WeiXinTokenBean bean, String requestType) {
        int ret = jdbcTemplate.update("insert into pn_weixin_log(signature, timestamp, nonce, echostr, create_time, request_type) value(?, ?, ?, ?, now(), ?)",
                new Object[]{bean.getSignature(), bean.getTimestamp(), bean.getNonce(), bean.getEchostr(), requestType},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        return ret == 1;
    }

    @Override
    public Boolean addWeiXinUserMapping(WeiXinUserMappingBean bean) {
        int ret = jdbcTemplate.update("insert into pn_weixin_user_mapping(weixin_user_name, piggy_user_name, create_time) value(?, ?, now())",
                new Object[]{bean.getWeiXinUserName(), bean.getPiggyUserName()},
                new int[]{Types.VARCHAR, Types.VARCHAR});
        return ret == 1;
    }

    @Override
    public Boolean deleteWeiXinUserMapping(WeiXinUserMappingBean bean){
        int ret = jdbcTemplate.update("delete from pn_weixin_user_mapping where piggy_user_name=? and weixin_user_name=?",
                new Object[]{bean.getPiggyUserName(), bean.getWeiXinUserName()},
                new int[]{Types.VARCHAR, Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean weiXinUserMappingExist(String weiXinUserName) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(id) as num from pn_weixin_user_mapping where weixin_user_name=?",
                new Object[]{weiXinUserName},
                new int[]{Types.VARCHAR},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }

    @Override
    public WeiXinUserMappingBean getPiggyUserNameByWeiXinUserName(String weiXinUserName) {
        return (WeiXinUserMappingBean)jdbcTemplate.queryForObject("select * from pn_weixin_user_mapping where weixin_user_name=?",
                new Object[]{weiXinUserName},
                new int[]{Types.VARCHAR},
                new WeiXinUserMappingRowMapper());
    }
}
