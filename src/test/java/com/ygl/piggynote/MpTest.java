package com.ygl.piggynote;

import com.ygl.piggynote.bean.mp.MyPasswordBean;
import com.ygl.piggynote.bean.mp.MyWordBean;
import com.ygl.piggynote.bean.mp.MyWordContentBean;
import com.ygl.piggynote.service.impl.MpMyPasswordServiceImpl;
import com.ygl.piggynote.service.impl.MpMyWordContentServiceImpl;
import com.ygl.piggynote.service.impl.MpMyWordServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by yanggavin on 15/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml",
        "file:src/main/resources/database.xml"
})
public class MpTest {
    private MockMvc mockMvc;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected MpMyPasswordServiceImpl mpMyPasswordService;
    @Autowired
    protected MpMyWordServiceImpl mpMyWordService;
    @Autowired
    protected MpMyWordContentServiceImpl mpMyWordContentService;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void mainTest(){
    }

    @Test
    public void wordTest(){
        int userId = 17;

        MyWordBean bean = new MyWordBean();
        bean.setUserId(userId);
        bean.setContentId(1);
        bean.setShowName("常用数字");
        Assert.isTrue(mpMyWordService.add(bean));

        List<MyWordBean> list = mpMyWordService.get(userId);
        Assert.isTrue(list.size() == 1);
        MyWordBean nBean = list.get(0);
        nBean.setShowName("我的姓");
        Assert.isTrue(mpMyWordService.update(nBean));
        Assert.isTrue(mpMyWordService.delete(nBean.getId(), userId));
    }

    @Test
    public void wordContentTest(){
        int userId = 17;

        MyWordContentBean bean = new MyWordContentBean();
        bean.setUserId(userId);
        bean.setContent("123456");
        int newId = mpMyWordContentService.add(bean);
        Assert.isTrue(newId > 0);

        MyWordContentBean nBean = mpMyWordContentService.get(newId);
        Assert.isTrue(nBean != null);
        nBean.setContent("abcd");
        Assert.isTrue(mpMyWordContentService.update(nBean));
        Assert.isTrue(mpMyWordContentService.delete(nBean.getId(), userId));
    }

    @Test
    public void passwordTest(){
        int userId = 17;

        MyPasswordBean bean = new MyPasswordBean();
        bean.setUserId(userId);
        bean.setShowName("百度");
        bean.setPassword("1,5,78");
        Assert.isTrue(mpMyPasswordService.add(bean));

        List<MyPasswordBean> list = mpMyPasswordService.getByUserId(userId);
        Assert.isTrue(list.size() == 1);
        MyPasswordBean nBean = list.get(0);
        nBean.setPassword("34,2,44");
        Assert.isTrue(mpMyPasswordService.update(nBean));
        Assert.isTrue(mpMyPasswordService.delete(nBean.getId(), userId));
    }
}
