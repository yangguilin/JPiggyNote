package com.ygl.snwt.bean;

import java.util.Date;

/**
 * 你我他伙伴关系bean
 * Created by yanggavin on 14/12/11.
 */
public class NwtRelationship {

    /**
     * 用户id
     */
    private int userId;
    /**
     * 伙伴id
     */
    private int friendId;
    /**
     * 伙伴关系状态
     */
    private String status;
    /**
     * 伙伴最近更新信息时间
     */
    private Date friendInfoUpdateTime;
}
