package com.ygl.piggynote.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanggavin on 15/5/14.
 */
public class InvestmentStatDataBean {
    private String userName;
    private List<InvestmentBean> currentInvestmentList;
    private List<InvestmentBean> finishedInvestmentList;
    private float currentInvestmentTotalNum;
    private float currentInvestmentPrincipalTotalNum;
    private float currentInvestmentSurplusTotalNum;
    private float finishedInvestmentTotalSurplusTotalNum;

    public List<InvestmentBean> getFinishedInvestmentList() {
        return finishedInvestmentList;
    }

    public List<InvestmentBean> getCurrentInvestmentList() {
        return currentInvestmentList;
    }

    public float getCurrentInvestmentTotalNum() {
        return currentInvestmentTotalNum;
    }

    public float getCurrentInvestmentPrincipalTotalNum() {
        return currentInvestmentPrincipalTotalNum;
    }

    public float getCurrentInvestmentSurplusTotalNum() {
        return currentInvestmentSurplusTotalNum;
    }

    public float getFinishedInvestmentTotalSurplusTotalNum() {
        return finishedInvestmentTotalSurplusTotalNum;
    }

    public InvestmentStatDataBean(String userName){
        this.userName = userName;
        currentInvestmentTotalNum = 0;
        currentInvestmentSurplusTotalNum = 0;
        currentInvestmentPrincipalTotalNum = 0;
        finishedInvestmentTotalSurplusTotalNum = 0;
        currentInvestmentList = new ArrayList<InvestmentBean>();
        finishedInvestmentList = new ArrayList<InvestmentBean>();
    }
    public void fillDataAndCalculateStatData(List<InvestmentBean> allList){
        for (InvestmentBean bean : allList){
            if (bean.isFinishStatus()){
                finishedInvestmentTotalSurplusTotalNum += bean.getFinalNum() - bean.getPrincipalNum();
                finishedInvestmentList.add(bean);
            } else {
                currentInvestmentTotalNum += bean.getCurrentNum();
                currentInvestmentPrincipalTotalNum += bean.getPrincipalNum();
                currentInvestmentSurplusTotalNum += bean.getCurrentNum() - bean.getPrincipalNum();
                currentInvestmentList.add(bean);
            }
        }
    }
}
