package com.ygl.snwt.enums;

/**
 * Created by yanggavin on 14/12/11.
 */
public enum RelationshipStatusEnum {

    NO_REGISTER("0", "未注册"),
    REGISTER("1", "已注册"),
    MEMBER_AUTH("2", "社员认证"),
    CONFIRM_FRIEND("3", "互为好友");


    private String value;
    private String des;


    public String getValue() {
        return value;
    }

    RelationshipStatusEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    /**
     * 通过字符值获取枚举值
     * @param val   字符值
     * @return      枚举值
     */
    public static RelationshipStatusEnum getEnumByValue(String val){

        RelationshipStatusEnum type = RelationshipStatusEnum.NO_REGISTER;

        if ("1".equals(val)){
            type = RelationshipStatusEnum.REGISTER;
        } else if ("2".equals(val)){
            type = RelationshipStatusEnum.MEMBER_AUTH;
        }else if ("3".equals(val)){
            type = RelationshipStatusEnum.CONFIRM_FRIEND;
        }

        return type;
    }
}
