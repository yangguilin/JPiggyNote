package com.ygl.snwt.enums;

/**
 * Created by yanggavin on 14/12/11.
 */
public enum SocietyDeptEnum {

    BANGONG("0", "办公部"),
    WENTI("1", "文体部"),
    XUANCHUAN("2", "宣传部"),
    SHOUYUDUI("3", "手语队"),
    XIANGMU("4", "项目部"),
    WAILIAN("5", "外联部");


    private String value;
    private String des;


    public String getValue() {
        return value;
    }

    SocietyDeptEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    /**
     * 通过字符值获取枚举值
     * @param val   字符值
     * @return      枚举值
     */
    public static SocietyDeptEnum getEnumByValue(String val){

        SocietyDeptEnum type = SocietyDeptEnum.BANGONG;

        if ("1".equals(val)){
            type = SocietyDeptEnum.WENTI;
        } else if ("2".equals(val)){
            type = SocietyDeptEnum.XUANCHUAN;
        } else if ("3".equals(val)){
            type = SocietyDeptEnum.SHOUYUDUI;
        } else if ("4".equals(val)){
            type = SocietyDeptEnum.XIANGMU;
        } else if ("5".equals(val)){
            type = SocietyDeptEnum.WAILIAN;
        }

        return type;
    }
}
