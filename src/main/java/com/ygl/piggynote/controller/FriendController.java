package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.FriendBean;
import com.ygl.piggynote.service.impl.FriendServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by yanggavin on 15/3/12.
 */
@Controller
@RequestMapping("/friend")
public class FriendController extends BaseController {
    @Autowired
    private FriendServiceImpl friendService;

    @RequestMapping(value="/add.do", method= RequestMethod.POST)
    public void add(FriendBean fb, HttpServletResponse response){
        Boolean ret = false;
        if (fb != null && !fb.getUserName().isEmpty() && !fb.getFriendName().isEmpty()){
            if (!friendService.exist(fb)) {
                ret = friendService.add(fb);
            }
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    @RequestMapping(value="/add_laoniu.do", method=RequestMethod.POST)
    public void addLaoniu(FriendBean fb, HttpServletResponse response){
        Boolean ret = false;
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    @RequestMapping(value="/delete_laoniu.do", method=RequestMethod.POST)
    public void deleteLaoniu(FriendBean fb, HttpServletResponse response){
        Boolean ret = false;
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
