package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.GroupMemberBean;
import com.ygl.piggynote.service.impl.GroupMemberServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by yanggavin on 15/3/13.
 */
@Controller
@RequestMapping("/group_member")
public class GroupMemberController {
    @Autowired
    private GroupMemberServiceImpl groupMemberService;

    @RequestMapping(value="/add.do", method= RequestMethod.POST)
    public void add(GroupMemberBean bean, HttpServletResponse response){
        Boolean ret = false;
        if (bean != null && !bean.getUserName().isEmpty() && bean.getGroupId() > 0){
            if (!groupMemberService.exist(bean.getUserName(), bean.getGroupId())) {
                ret = groupMemberService.add(bean);
            }
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    @RequestMapping(value="/quite.do", method=RequestMethod.POST)
    public void quite(GroupMemberBean bean, HttpServletResponse response){
        Boolean ret = false;
        if (bean != null && !bean.getUserName().isEmpty() && bean.getGroupId() > 0){
            if (groupMemberService.exist(bean.getUserName(), bean.getGroupId())) {
                ret = groupMemberService.delete(bean.getUserName(), bean.getGroupId());
            }
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
