package com.ygl.piggynote;

import com.ygl.piggynote.bean.CategoryBean;
import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.enums.StatTypeEnum;
import com.ygl.piggynote.service.impl.CategoryServiceImpl;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by yanggavin on 14-7-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:/WEB-INF/mvc-dispatcher-servlet.xml")
public class ServiceTests {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    /**
     * 公用测试数据：用户名
     */
    private String _userName = "yanggl";


    @Test
    public void dailyRecordTest(){

        Boolean ret = false;
        DailyRecordServiceImpl drsi = (DailyRecordServiceImpl)wac.getBean("pnDailyRecordService");

        DailyRecordBean dailyRecord = new DailyRecordBean();
        dailyRecord.setUserName(_userName);
        dailyRecord.setMoneyType(MoneyTypeEnum.COST);
        dailyRecord.setStatType(StatTypeEnum.NORMAL);
        dailyRecord.setAmount(100l);
        dailyRecord.setRemark("nothing");
        dailyRecord.setCategoryName("食");
        dailyRecord.setCategoryId("001");

        // add
        ret = drsi.add(dailyRecord);
        Assert.isTrue(ret);

        // get
        List<DailyRecordBean> list = drsi.getRecordsByDateRange(
                "2014-07-20 00:00:00",
                "2014-07-23 00:00:00",
                _userName);
        DailyRecordBean drb = list.get(0);
        Assert.isTrue(drb.getAmount() == 100l);

        // update
        dailyRecord.setId(drb.getId());
        dailyRecord.setAmount(200l);
        ret = drsi.update(dailyRecord);
        Assert.isTrue(ret);

        // delete
        ret = drsi.delete(list.get(0).getId(), _userName);
        Assert.isTrue(ret);
    }

    /**
     * CustomConfigServiceImpl实现类测试
     */
    @Test
    public void customConfigTest(){

        Boolean ret = false;
        CustomConfigServiceImpl ccsi = (CustomConfigServiceImpl)wac.getBean("pnCustomConfigService");

        CustomConfigBean customeConfig = new CustomConfigBean();
        customeConfig.setUserName(_userName);
        customeConfig.setMonthCostPlan(5000l);

        // add
        ret = ccsi.add(customeConfig);
        Assert.isTrue(ret);

        // update
        customeConfig.setMonthCostPlan(6000l);
        ret = ccsi.update(customeConfig);
        Assert.isTrue(ret);

        // get
        CustomConfigBean ccb = ccsi.getByUserName(_userName);
        Assert.isTrue(ccb.getMonthCostPlan() == 6000l);

        // delete
        ret = ccsi.deleteByUserName(_userName);
        Assert.isTrue(ret);
    }

    /**
     * CategoryServiceImpl实现类测试
     */
    @Test
    public void categoryTest(){

        Boolean ret = false;
        CategoryServiceImpl csi = (CategoryServiceImpl)wac.getBean("pnCategoryService");

        CategoryBean category = new CategoryBean();
        category.setUserName(_userName);
        category.setCategoryXml("category_xml");
        category.setCategoryXmlSorted("category_xml_sorted");
        category.setLatestModifiedDate(new Date());

        // add
        ret = csi.add(category);
        Assert.isTrue(ret);

        // update
        category.setCategoryXml("new xml file");
        ret = csi.update(category);
        Assert.isTrue(ret);

        // get
        CategoryBean cb = csi.getByUserName(_userName);
        Assert.isTrue(cb.getCategoryXml().equals("new xml file"));

        // delete
        ret = csi.deleteByUserName(_userName);
        Assert.isTrue(ret);
    }

    /**
     * UserServiceImpl实现类测试
     */
    @Test
    public void userTest(){

        Boolean ret = false;
        UserServiceImpl usi = (UserServiceImpl)wac.getBean("pnUserService");

        UserBean user = new UserBean();
        user.setUserName(_userName);
        user.setPassword("123456");
        user.setNikeName("酸甜西瓜");
        user.setEmail("yguilin@aliyun.com");
        user.setMobilePhone("13811111111");
        user.setLatestLoginDate(new Date());

        // add
        ret = usi.add(user);
        Assert.isTrue(ret);

        // update
        user.setMobilePhone("18622222222");
        ret = usi.update(user);
        Assert.isTrue(ret);

        // get
        UserBean ub = usi.getByUserName(_userName);
        Assert.isTrue(ub.getMobilePhone().equals("18622222222"));

        // delete
        ret = usi.deleteByUserName(_userName);
        Assert.isTrue(ret);
    }
}
