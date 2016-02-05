package com.ygl.piggynote.bean;

import java.util.Date;

/**
 * Created by yanggavin on 15/4/14.
 */
public class FcmgRecordBean {
    private int id;
    private String playerName;
    private int sideNum;
    private int goldNum;
    private int glanceTime;
    private int costSecond;
    private Date createTime;
    private int moveStepNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getSideNum() {
        return sideNum;
    }

    public void setSideNum(int sideNum) {
        this.sideNum = sideNum;
    }

    public int getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(int goldNum) {
        this.goldNum = goldNum;
    }

    public int getGlanceTime() {
        return glanceTime;
    }

    public void setGlanceTime(int glanceTime) {
        this.glanceTime = glanceTime;
    }

    public int getCostSecond() {
        return costSecond;
    }

    public void setCostSecond(int costSecond) {
        this.costSecond = costSecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getMoveStepNum() {
        return moveStepNum;
    }

    public void setMoveStepNum(int moveStepNum) {
        this.moveStepNum = moveStepNum;
    }
}
