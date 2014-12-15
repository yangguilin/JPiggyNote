package com.ygl.snwt.bean;

import com.ygl.snwt.enums.AppTypeEnum;
import com.ygl.snwt.enums.SocietyDeptEnum;

import java.util.Date;

/**
 * 你我他用户bean
 * Created by yanggavin on 14/12/11.
 */
public class NwtUser {

    /**
     * id
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 新生入学年
     */
    private String year;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 微信
     */
    private String weiXin;
    /**
     * qq
     */
    private String qq;
    /**
     * 邮件
     */
    private String email;
    /**
     * 现居住城市
     */
    private String city;
    /**
     * 当前工作职位
     */
    private String job;
    /**
     * 当前服务公司
     */
    private String company;
    /**
     * 家乡
     */
    private String hometown;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 是否完成社员认证
     */
    private Boolean auth;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 爱心社所属部门
     */
    private SocietyDeptEnum societyDept;
    /**
     * 客户端类型：android,ios,web
     */
    private AppTypeEnum appType;
    /**
     * 社员认证号：唯一
     */
    private String authCode;
    /**
     * 花名：限定2个汉字，且唯一
     */
    private String nikeName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户是否被删除
     */
    private Boolean deleted;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public SocietyDeptEnum getSocietyDept() {
        return societyDept;
    }

    public void setSocietyDept(SocietyDeptEnum societyDept) {
        this.societyDept = societyDept;
    }

    public AppTypeEnum getAppType() {
        return appType;
    }

    public void setAppType(AppTypeEnum appType) {
        this.appType = appType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
