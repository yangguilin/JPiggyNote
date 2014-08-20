package com.ygl.piggynote.enums;

/**
 * 统计类型枚举
 * 日常/阶段/大额
 * Created by yanggavin on 14-7-18.
 */
public enum StatTypeEnum {

    SIMPLE("0", "精简模式"),
    NORMAL("1", "日常"),
    PERIOD("2", "阶段"),
    BIG("3", "大额");


    private String value;
    private String des;

    public String getValue() {
        return value;
    }

    StatTypeEnum(String val, String des) {
        this.value = val;
        this.des = des;
    }

    /**
     * 通过字符值获取枚举值
     * @param val   字符值
     * @return      枚举值
     */
    public static StatTypeEnum getEnumByValue(String val){

        StatTypeEnum statType = StatTypeEnum.NORMAL;

        for (StatTypeEnum ste : StatTypeEnum.values()){
            if (ste.getValue().equals(val)){
                statType = ste;
                break;
            }
        }

        return statType;
    }
}
