package com.ygl.piggynote.enums;

/**
 * Created by yanggavin on 15/5/11.
 */
public enum InvestmentTypeEnum {
    STOCK("0", "股票"),
    HOUSE("1", "地产"),
    BANK("2", "定期存款"),
    YUERBAO("3", "余额宝"),
    LICAI("4", "理财产品"),
    DEFAULT("5", "默认");


    private String value;
    private String des;


    public String getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    InvestmentTypeEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    /**
     * 通过字符值获取枚举值
     * @param val   字符值
     * @return      枚举值
     */
    public static InvestmentTypeEnum getEnumByValue(String val){
        InvestmentTypeEnum type = InvestmentTypeEnum.STOCK;
        if ("1".equals(val)){
            type = InvestmentTypeEnum.HOUSE;
        } else if ("2".equals(val)){
            type = InvestmentTypeEnum.BANK;
        } else if ("3".equals(val)){
            type = InvestmentTypeEnum.YUERBAO;
        }  else if ("4".equals(val)){
            type = InvestmentTypeEnum.LICAI;
        }  else if ("5".equals(val)){
            type = InvestmentTypeEnum.DEFAULT;
        }
        return type;
    }
}
