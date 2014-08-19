package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.rowmapper.DailyRecordRowMapper;
import com.ygl.piggynote.service.DailyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * 账目记录服务实现类
 * Created by yanggavin on 14-7-18.
 */
@Service("pnDailyRecordService")
public class DailyRecordServiceImpl implements DailyRecordService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加一条新纪录
     * @param   bean    记录实例
     * @return          是否成功
     */
    @Override
    public Boolean add(DailyRecordBean bean) {

        int ret = jdbcTemplate.update("insert into pn_daily_records(user_name, money_type, stat_type, category_id, category_name, amount, remark, create_date, latest_modified_date)"
                + "value(?, ?, ?, ?, ?, ?, ?, now(), now())",
                new Object[]{
                        bean.getUserName(),
                        bean.getMoneyType().getValue(),
                        bean.getStatType().getValue(),
                        bean.getCategoryId(),
                        bean.getCategoryName(),
                        bean.getAmount(),
                        bean.getRemark()
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.FLOAT,
                        Types.VARCHAR
                });

        return ret == 1;
    }

    /**
     * 通过记录id删除某用户的一条记录
     * @param id            记录id
     * @param userName      用户名
     * @return              是否成功
     */
    @Override
    public Boolean delete(int id, String userName) {

        int ret = jdbcTemplate.update("delete from pn_daily_records where id=? and user_name=?",
                new Object[]{id, userName},
                new int[]{Types.INTEGER, Types.VARCHAR});

        return ret == 1;
    }

    /**
     * 更新一条记录内容
     * @param bean  记录实例
     * @return      是否成功
     */
    @Override
    public Boolean update(DailyRecordBean bean) {

        int ret = jdbcTemplate.update("update pn_daily_records set stat_type=?, category_id=?, category_name=?, amount=?, remark=?, latest_modified_date=now()"
                + " where id=? and user_name=?",
                new Object[]{
                        bean.getStatType().getValue(),
                        bean.getCategoryId(),
                        bean.getCategoryName(),
                        bean.getAmount(),
                        bean.getRemark(),
                        bean.getId(),
                        bean.getUserName()
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.FLOAT,
                        Types.VARCHAR,
                        Types.INTEGER,
                        Types.VARCHAR,
                });

        return ret == 1;
    }

    /**
     * 通过时间区间获取某用户的记录列表
     * @param beginDate     开始时间，格式“2014-07-18 12:11:11”
     * @param endDate       结束时间，格式“2014-07-18 12:11:11”
     * @param userName      用户名
     * @return              记录列表
     */
    @Override
    public List<DailyRecordBean> getRecordsByDateRange(String beginDate, String endDate, String userName) {

        return (List<DailyRecordBean>)jdbcTemplate.query("select * from pn_daily_records where user_name=? and create_date between ? and ? order by create_date desc",
                new Object[]{userName, beginDate, endDate},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR},
                new DailyRecordRowMapper());
    }
}
