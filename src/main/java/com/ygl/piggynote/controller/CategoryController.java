package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CategoryBean;
import com.ygl.piggynote.bean.CategoryList;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.service.impl.CategoryServiceImpl;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guilin on 2014/8/1.
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private UserServiceImpl userService;


    @RequestMapping(method=RequestMethod.GET)
    public String show(HttpServletRequest request, ModelMap model){

        UserBean ub = getUserFromSession(request);
        CategoryBean cb = categoryService.get(ub.getUserName());
        CategoryList cl = cb.getGetCategoryList();
        model.addAttribute("costMap", cl.costMap);
        model.addAttribute("incomeMap", cl.incomeMap);
        model.addAttribute("userName", cb.getUserName());

        return "/category";
    }

    /**
     * 添加用户分类
     * @param category
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public void add(CategoryBean category, HttpServletResponse response){

        // add category to db.
        Boolean ret = categoryService.add(category);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 更新用户分类
     * @param category  用户分类实例
     * @param response  response实例
     */
    @RequestMapping(value="/update.do", method = RequestMethod.POST)
    public void update(CategoryBean category, HttpServletResponse response){

        // update data to db.
        Boolean ret = categoryService.update(category);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 更新分类条目
     * @param userName  用户名
     * @param categoryId    分类id
     * @param categoryName  分类名
     * @param response  response
     */
    @RequestMapping(value="/update_item.do", method=RequestMethod.POST)
    public void updateItem(String userName, String categoryId, String categoryName,
                           HttpServletRequest request, HttpServletResponse response){

        Boolean ret = true;

        // 检查其他参数
        if (userName == null || userName.isEmpty()
                || categoryId == null || categoryId.isEmpty()
                || categoryName == null || categoryName.isEmpty()){

            ret = false;
        }

        // 检查用户名：不存在或与当前登录用户不一致，均不能操作
        UserBean ub = getUserFromSession(request);
        if (!userService.exist(userName) || !ub.getUserName().equals(userName)){
            ret = false;
        }

        // 符合更新条件
        if (ret) {

            // 更新分类信息
            CategoryBean cb = categoryService.get(userName);
            if (cb != null) {

                CategoryList cl = cb.getGetCategoryList();
                if (categoryId.startsWith("1")) {

                    if (cl.costMap.containsKey(categoryId)) {
                        cl.costMap.put(categoryId, categoryName);
                    }
                } else {

                    if (cl.incomeMap.containsKey(categoryId)) {
                        cl.incomeMap.put(categoryId, categoryName);
                    }
                }

                // 强制更新数据字段
                cb.updateJsonDataByCategoryList();
                // 更新到数据库
                categoryService.update(cb);
            }
        }

        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    @RequestMapping(value="/add_item.do", method = RequestMethod.POST)
    public void addItem(String userName, MoneyTypeEnum moneyType, String categoryName,
                        HttpServletRequest request, HttpServletResponse response){

        Boolean ret = true;

        // 检查其他参数
        if (userName == null || userName.isEmpty()
                || moneyType == null
                || categoryName == null || categoryName.isEmpty()){

            ret = false;
        }

        // 检查用户名：不存在或与当前登录用户不一致，均不能操作
        UserBean ub = getUserFromSession(request);
        if (!userService.exist(userName) || !ub.getUserName().equals(userName)){
            ret = false;
        }

        // 符合更新条件
        String newId = "";
        if (ret) {

            try {
                // 更新分类信息
                CategoryBean cb = categoryService.get(userName);
                if (cb != null) {

                    CategoryList cl = cb.getGetCategoryList();
                    if (moneyType == MoneyTypeEnum.COST) {

                        newId = "c" + (cl.costMap.size() + 1);
                        cl.costMap.put(newId, categoryName);
                    } else {

                        newId = "i" + (cl.incomeMap.size() + 1);
                        cl.costMap.put(newId, categoryName);
                    }

                    // 强制更新数据字段
                    cb.updateJsonDataByCategoryList();

                    // 更新到数据库
                    categoryService.update(cb);
                }
            }catch(Exception e){

                ret = false;
                newId = "";
            }
        }

        CommonUtil.writeResponse4ReturnStrResult(ret, newId, response);
    }

    /**
     * 恢复默认用户分类
     * @param userName  用户名
     * @param response  response实例
     */
    @RequestMapping(value="/reset.do", method = RequestMethod.POST)
    public void reset(String userName, HttpServletResponse response){

        CategoryBean cb = new CategoryBean();
        cb.setUserName(userName);
        cb.fillDefaultData();

        // update data to db.
        Boolean ret = categoryService.update(cb);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
