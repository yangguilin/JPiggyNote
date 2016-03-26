package com.ygl.piggynote.service.dingup;

import com.ygl.piggynote.bean.dingup.PhraseTranBean;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;

import java.util.List;

/**
 * Created by yanggavin on 16/3/2.
 */
public interface PhraseTranService {
    boolean add(PhraseTranBean bean);
    boolean update(PhraseTranBean bean);
    boolean updateRecordTime(int id, String phrase);
    boolean updateStatus(int id, TranStatusEnum status);
    List<PhraseTranBean> getAll();
    List<PhraseTranBean> getOneWaitingCheckWord();
    boolean updatePhraseName(int id, String oldPhraseName, String newPhraseName);
    List<PhraseTranBean> getLatestUpdatePhrase();
    List<PhraseTranBean> getNoSearchResultList();
    List<PhraseTranBean> searchByPhraseName(String phrase);
    boolean existPhrase(String phrase);
}
