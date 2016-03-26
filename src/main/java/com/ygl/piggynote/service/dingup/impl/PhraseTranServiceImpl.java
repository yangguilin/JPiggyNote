package com.ygl.piggynote.service.dingup.impl;

import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.bean.dingup.PhraseTranBean;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.rowmapper.dingup.PhraseTranRowMapper;
import com.ygl.piggynote.service.dingup.PhraseTranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 16/3/2.
 */
@Service("dpPhraseTranService")
public class PhraseTranServiceImpl implements PhraseTranService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean add(PhraseTranBean bean) {
        int ret = jdbcTemplate.update("insert into dp_phrase_tran(exam_type, exam_sub_type, exam_third_type, phrase, origin_ch_meaning, cls_ch_meaning, cls_en_meaning, cls_en_ex_tran, cls_en_ex, create_time, last_update_time, status, freq) value(?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now(), 0, 0)",
                new Object[]{
                        bean.getExamType().getValue(),
                        bean.getExamSubType(),
                        bean.getExamThirdType(),
                        bean.getPhrase(),
                        bean.getOriginChMeaning(),
                        bean.getClsChMeaning(),
                        bean.getClsEnMeaning(),
                        bean.getClsEnExTran(),
                        bean.getClsEnEx()
                },
                new int[]{ Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
        return ret == 1;
    }

    @Override
    public boolean update(PhraseTranBean bean) {
        int ret = jdbcTemplate.update("update dp_phrase_tran set exam_type=?, exam_sub_type=?, exam_third_type=?, phrase=?, origin_ch_meaning=?, cls_ch_meaning=?, cls_en_meaning=?, cls_en_ex_tran=?, cls_en_ex=?, last_update_time=now(), status=? where id=?",
                new Object[]{
                        bean.getExamType().getValue(),
                        bean.getExamSubType(),
                        bean.getExamThirdType(),
                        bean.getPhrase(),
                        bean.getOriginChMeaning(),
                        bean.getClsChMeaning(),
                        bean.getClsEnMeaning(),
                        bean.getClsEnExTran(),
                        bean.getClsEnEx(),
                        bean.getStatus().getValue(),
                        bean.getId()
                },
                new int[]{ Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER });
        return ret == 1;
    }

    @Override
    public boolean updatePhraseName(int id, String oldPhraseName, String newPhraseName) {
        int ret = jdbcTemplate.update("update dp_phrase_tran set phrase=? where id=? and phrase=?",
                new Object[]{ newPhraseName, id, oldPhraseName },
                new int[]{ Types.VARCHAR, Types.INTEGER, Types.VARCHAR });
        return ret == 1;
    }

    @Override
    public boolean updateRecordTime(int id, String phrase) {
        int ret = jdbcTemplate.update("update dp_phrase_tran set last_update_time=now() where id=? and phrase=?",
                new Object[]{ id, phrase },
                new int[]{ Types.INTEGER, Types.VARCHAR });
        return ret == 1;
    }

    @Override
    public boolean updateStatus(int id, TranStatusEnum status) {
        int ret = jdbcTemplate.update("update dp_phrase_tran set status=? where id=?",
                new Object[]{ status.getValue(), id },
                new int[]{ Types.INTEGER, Types.INTEGER });
        return ret > 0;
    }

    @Override
    public List<PhraseTranBean> getAll() {
        return (List<PhraseTranBean>)jdbcTemplate.query("select * from dp_phrase_tran where status<>2 order by create_time desc",
                new Object[]{},
                new int[]{},
                new PhraseTranRowMapper());
    }

    @Override
    public List<PhraseTranBean> getOneWaitingCheckWord(){
        return (List<PhraseTranBean>)jdbcTemplate.query("select * from dp_phrase_tran where status=0 order by last_update_time asc limit 1",
                new Object[]{}, new int[]{}, new PhraseTranRowMapper());
    }

    @Override
    public List<PhraseTranBean> getLatestUpdatePhrase(){
        return (List<PhraseTranBean>)jdbcTemplate.query("select * from dp_phrase_tran where status<>2 order by last_update_time DESC limit 1",
                new Object[]{}, new int[]{}, new PhraseTranRowMapper());
    }

    @Override
    public List<PhraseTranBean> getNoSearchResultList() {
        return (List<PhraseTranBean>)jdbcTemplate.query("select * from dp_phrase_tran where status<>2 and entity_type is null order by create_time desc",
                new Object[]{},
                new int[]{},
                new PhraseTranRowMapper());
    }

    @Override
    public List<PhraseTranBean> searchByPhraseName(String phrase) {
        return (List<PhraseTranBean>)jdbcTemplate.query("select * from dp_phrase_tran where status<>2 and phrase=? order by create_time desc",
                new Object[]{ phrase },
                new int[]{ Types.VARCHAR },
                new PhraseTranRowMapper());
    }

    @Override
    public boolean existPhrase(String phrase) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(id) as num from dp_phrase_tran where phrase=?",
                new Object[]{ phrase },
                new int[]{Types.VARCHAR},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }
}

