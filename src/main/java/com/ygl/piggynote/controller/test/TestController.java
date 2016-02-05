package com.ygl.piggynote.controller.test;

import com.ygl.piggynote.bean.CategoryBean;
import com.ygl.piggynote.bean.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yanggavin on 14-7-22.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        // 只查发布开始时间为近60天的数据
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -60);
        Date beforeDate = cal.getTime();

        return "test";
    }

    @RequestMapping(value="/category_test")
    public String categoryPage(ModelMap model){

        CategoryBean cb = new CategoryBean();
        cb.setUserName("ygl");
        cb.setCategoryData("category_xml");
        cb.setCategoryDataSorted("category_xml_sorted");

        model.addAttribute("category_new", cb);

        return "/test/category_test";
    }

    @RequestMapping(value="/user_t")
    public String showPage(ModelMap model){

        UserBean newUser = new UserBean();
        UserBean updateUser = new UserBean();
        updateUser.setUserName("username");
        updateUser.setEmail("email@abc.com");
        updateUser.setNikeName("nikename");
        updateUser.setMobilePhone("186111111111");
        updateUser.setPassword("123456");

        model.addAttribute("user_new", newUser);
        model.addAttribute("user_update", updateUser);

        return "/test/user_t";
    }

    @RequestMapping(value="/test")
    public String testPage(){
        return "/test/test";
    }
}
