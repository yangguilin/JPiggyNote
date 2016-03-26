package com.ygl.piggynote.service.dingup;

import com.ygl.piggynote.bean.dingup.WordTranBean;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;

import java.util.List;

/**
 * Created by yanggavin on 16/2/26.
 */
public interface WordTranService {
    boolean add(WordTranBean bean);
    boolean update(WordTranBean bean);
    boolean updateRecordTime(int id, String wordName);
    boolean updateWordName(int id, String oldWordName, String newWordName);
    boolean updateStatus(int id, String wordName, TranStatusEnum status);
    List<WordTranBean> getAll();
    List<WordTranBean> getOneWaitingCheckWord();
    List<WordTranBean> getLatestUpdateWord();
    List<WordTranBean> getNoSearchResultList();
    List<WordTranBean> searchByWordName(String wordName);
    boolean existWordName(String wordName);
}
