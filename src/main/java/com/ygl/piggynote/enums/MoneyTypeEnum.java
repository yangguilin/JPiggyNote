package com.ygl.piggynote.enums;

/**
 * 账目类型枚举
 * 支出/收入
 * Created by yanggavin on 14-7-18.
 */
public enum MoneyTypeEnum {

    COST("0", "支出"),
    INCOME("1", "收入"),
    PREPAY("2", "垫付"),
    PAYBACK("3", "收回");


    private String value;
    private String des;


    public String getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    MoneyTypeEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    /**
     * 通过字符值获取枚举值
     * @param val   字符值
     * @return      枚举值
     */
    public static MoneyTypeEnum getEnumByValue(String val){

        MoneyTypeEnum moneyType = MoneyTypeEnum.COST;

        if ("1".equals(val)){
            moneyType = MoneyTypeEnum.INCOME;
        } else if ("2".equals(val)){
            moneyType = MoneyTypeEnum.PREPAY;
        } else if ("3".equals(val)){
            moneyType = MoneyTypeEnum.PAYBACK;
        }

        return moneyType;
    }

}
