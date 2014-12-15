package com.ygl.snwt.enums;

/**
 * 操作客户端类型枚举
 * Created by yanggavin on 14/12/11.
 */
public enum AppTypeEnum {

    WEB("0", "网页版"),
    ANDROID("1", "安卓"),
    IOS("2", "苹果");


    private String value;
    private String des;


    public String getValue() {
        return value;
    }

    AppTypeEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    /**
     * 通过字符值获取枚举值
     * @param val   字符值
     * @return      枚举值
     */
    public static AppTypeEnum getEnumByValue(String val){

        AppTypeEnum type = AppTypeEnum.WEB;

        if ("1".equals(val)){
            type = AppTypeEnum.ANDROID;
        } else if ("2".equals(val)){
            type = AppTypeEnum.IOS;
        }

        return type;
    }
}
