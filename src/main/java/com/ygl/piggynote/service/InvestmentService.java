package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.InvestmentBean;

import java.util.List;

/**
 * Created by yanggavin on 15/5/11.
 */
public interface InvestmentService {
    Boolean add(InvestmentBean bean);
    Boolean update(InvestmentBean bean);
    Boolean finish(InvestmentBean bean);
    Boolean delete(int id, String userName);
    InvestmentBean get(int id);
    List<InvestmentBean> get(String userName);
}
