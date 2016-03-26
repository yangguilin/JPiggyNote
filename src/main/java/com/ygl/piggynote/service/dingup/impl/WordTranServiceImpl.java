package com.ygl.piggynote.service.dingup.impl;

import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.bean.dingup.WordTranBean;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.rowmapper.dingup.WordTranRowMapper;
import com.ygl.piggynote.service.dingup.WordTranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 16/2/27.
 */
@Service("dpWordTranService")
public class WordTranServiceImpl implements WordTranService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean add(WordTranBean bean) {
        int ret = jdbcTemplate.update("insert into dp_word_tran(entity_type, pron, exam_type, exam_sub_type, exam_third_type, word_name, origin_ch_meaning, cls_ch_meaning, cls_en_meaning, cls_ch_ex, cls_en_ex, cls_full_result, create_time, last_update_time, status, freq) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now(), ?, ?)",
                new Object[]{
                        bean.getEntityType(),
                        bean.getPron(),
                        bean.getExamType().getValue(),
                        bean.getExamSubType(),
                        bean.getExamThirdType(),
                        bean.getWordName(),
                        bean.getOriginChMeaning(),
                        bean.getClsChMeaning(),
                        bean.getClsEnMeaning(),
                        bean.getClsChEx(),
                        bean.getClsEnEx(),
                        bean.getClsFullResult(),
                        bean.getStatus().getValue(),
                        bean.getFreq()
                },
                new int[]{ Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER });
        return ret == 1;
    }

    @Override
    public boolean update(WordTranBean bean) {
        int ret = jdbcTemplate.update("update dp_word_tran set entity_type=?, pron=?, exam_type=?, exam_sub_type=?, exam_third_type=?, word_name=?, origin_ch_meaning=?, cls_ch_meaning=?, cls_en_meaning=?, cls_ch_ex=?, cls_en_ex=?, cls_full_result=?, last_update_time=now(), status=? where id=?",
                new Object[]{
                        bean.getEntityType(),
                        bean.getPron(),
                        bean.getExamType().getValue(),
                        bean.getExamSubType(),
                        bean.getExamThirdType(),
                        bean.getWordName(),
                        bean.getOriginChMeaning(),
                        bean.getClsChMeaning(),
                        bean.getClsEnMeaning(),
                        bean.getClsChEx(),
                        bean.getClsEnEx(),
                        bean.getClsFullResult(),
                        bean.getStatus().getValue(),
                        bean.getId()
                },
                new int[]{ Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER });
        return ret == 1;
    }

    @Override
    public boolean updateRecordTime(int id, String wordName) {
        int ret = jdbcTemplate.update("update dp_word_tran set last_update_time=now() where id=? and word_name=?",
                new Object[]{ id, wordName },
                new int[]{ Types.INTEGER, Types.VARCHAR });
        return ret == 1;
    }

    @Override
    public boolean updateWordName(int id, String oldWordName, String newWordName) {
        int ret = jdbcTemplate.update("update dp_word_tran set word_name=? where id=? and word_name=?",
                new Object[]{ newWordName, id, oldWordName },
                new int[]{ Types.VARCHAR, Types.INTEGER, Types.VARCHAR });
        return ret == 1;
    }

    @Override
    public boolean updateStatus(int id, String wordName, TranStatusEnum status) {
        int ret = jdbcTemplate.update("update dp_word_tran set status=? where id=? and word_name=?",
                new Object[]{ status.getValue(), id, wordName },
                new int[]{ Types.INTEGER, Types.INTEGER, Types.VARCHAR });
        return ret > 0;
    }

    @Override
    public List<WordTranBean> getAll() {
        return (List<WordTranBean>)jdbcTemplate.query("select * from dp_word_tran where status<>2 order by create_time desc",
                new Object[]{},
                new int[]{},
                new WordTranRowMapper());
    }

    @Override
    public List<WordTranBean> getOneWaitingCheckWord(){
        return (List<WordTranBean>)jdbcTemplate.query("select * from dp_word_tran where status=0 order by last_update_time asc limit 1",
                new Object[]{}, new int[]{}, new WordTranRowMapper());
    }

    @Override
    public List<WordTranBean> getLatestUpdateWord(){
        return (List<WordTranBean>)jdbcTemplate.query("select * from dp_word_tran where status<>2 order by last_update_time DESC limit 1",
                new Object[]{}, new int[]{}, new WordTranRowMapper());
    }

    @Override
    public List<WordTranBean> getNoSearchResultList() {
        return (List<WordTranBean>)jdbcTemplate.query("select * from dp_word_tran where status<>2 and entity_type is null order by create_time desc",
                new Object[]{},
                new int[]{},
                new WordTranRowMapper());
    }

    @Override
    public List<WordTranBean> searchByWordName(String wordName) {
        return (List<WordTranBean>)jdbcTemplate.query("select * from dp_word_tran where status<>2 and word_name=? order by create_time desc",
                new Object[]{ wordName },
                new int[]{ Types.VARCHAR },
                new WordTranRowMapper());
    }

    @Override
    public boolean existWordName(String wordName) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(id) as num from dp_word_tran where word_name=? and status<>2",
                new Object[]{ wordName },
                new int[]{Types.VARCHAR},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }
}
