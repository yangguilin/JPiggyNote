package com.ygl.piggynote.enums.dingup;

/**
 * Created by yanggavin on 16/2/27.
 */
public enum TranStatusEnum {
    DEFAULT(0, "默认,未核对"),
    PASSED(1, "通过"),
    DENY(2, "拒绝"),
    DELETED(3, "删除");

    private int value;
    private String des;


    public int getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    TranStatusEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public static TranStatusEnum getByValue(int value){
        switch (value){
            case 1:
                return PASSED;
            case 2:
                return DENY;
            case 3:
                return DELETED;
            default:
                return DEFAULT;
        }
    }
}
