package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.FcmgRecordBean;
import com.ygl.piggynote.service.impl.FcmgRecordServiceImpl;
import com.ygl.piggynote.service.impl.FcmgServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanggavin on 15/4/7.
 */
@Controller
@RequestMapping("/fcmg")
public class FcmgController {
    @Autowired
    private FcmgServiceImpl fcmgService;
    @Autowired
    private FcmgRecordServiceImpl fcmgRecordService;

    @RequestMapping(method= RequestMethod.GET)
    public String show(ModelMap model){
        int singleSideUnitNum = 7;
        int goldUnitNum = 1;
        fcmgService.initHoneycomb(singleSideUnitNum, goldUnitNum);
        model.addAttribute("startSide", fcmgService.getStartSide());
        model.addAttribute("finishSide", fcmgService.getFinishSide());
        model.addAttribute("viewData", fcmgService.generateHoneycombViewData());
        model.addAttribute("honeycombData", fcmgService.generateHoneycombData());
        model.addAttribute("singleSideUnitNum", singleSideUnitNum);
        model.addAttribute("totalUnitNum", fcmgService.getTotalUnitNum());
        model.addAttribute("goldUnitData", fcmgService.getGoldUnitData());
        // model.addAttribute("top10Records", fcmgRecordService.getTop10Record(singleSideUnitNum, goldUnitNum, 0));
        return "fcmg";
    }

    @RequestMapping(value="/{singleSideUnitNum}/{goleUnitNum}", method= RequestMethod.GET)
    public String customShow(@PathVariable("singleSideUnitNum") int singleSideUnitNum,
                             @PathVariable("goleUnitNum") int goldUnitNum,
                             ModelMap model){
        fcmgService.initHoneycomb(singleSideUnitNum, goldUnitNum);
        model.addAttribute("startSide", fcmgService.getStartSide());
        model.addAttribute("finishSide", fcmgService.getFinishSide());
        model.addAttribute("viewData", fcmgService.generateHoneycombViewData());
        model.addAttribute("honeycombData", fcmgService.generateHoneycombData());
        model.addAttribute("singleSideUnitNum", singleSideUnitNum);
        model.addAttribute("totalUnitNum", fcmgService.getTotalUnitNum());
        model.addAttribute("goldUnitData", fcmgService.getGoldUnitData());
        return "fcmg";
    }

    @RequestMapping(value="/{goleUnitNum}", method= RequestMethod.GET)
    public String customShow(@PathVariable("goleUnitNum") int goldUnitNum, ModelMap model){
        int singleSideUnitNum = 7;
        if (goldUnitNum > 7){ // 目前仅开放到7星
            goldUnitNum = 7;
        }
        fcmgService.initHoneycomb(singleSideUnitNum, goldUnitNum);
        model.addAttribute("startSide", fcmgService.getStartSide());
        model.addAttribute("finishSide", fcmgService.getFinishSide());
        model.addAttribute("viewData", fcmgService.generateHoneycombViewData());
        model.addAttribute("honeycombData", fcmgService.generateHoneycombData());
        model.addAttribute("singleSideUnitNum", singleSideUnitNum);
        model.addAttribute("totalUnitNum", fcmgService.getTotalUnitNum());
        model.addAttribute("goldUnitData", fcmgService.getGoldUnitData());
        // model.addAttribute("top10Records", fcmgRecordService.getTop10Record(singleSideUnitNum, goldUnitNum, 0));
        return "fcmg";
    }

    @RequestMapping(value="/add_record.do", method=RequestMethod.POST)
    public void addRecord(FcmgRecordBean bean, HttpServletResponse response){
        Boolean ret = fcmgRecordService.add(bean);
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    @RequestMapping(value="/get_top10.do", method=RequestMethod.POST)
    public void getTop10Records(HttpServletRequest request, HttpServletResponse response){
        int sideNum, goldNum;
        String retContent = "";
        Boolean ret = true;
        List<FcmgRecordBean> noGlanceList = new ArrayList<FcmgRecordBean>();
        List<FcmgRecordBean> glanceList = new ArrayList<FcmgRecordBean>();
        try{
            sideNum = Integer.parseInt(request.getParameter("sideNum").toString());
            goldNum = Integer.parseInt(request.getParameter("goldNum").toString());
            if (sideNum > 0 && goldNum > 0){
                noGlanceList = fcmgRecordService.getTop10Record(sideNum, goldNum, 0);
                glanceList = fcmgRecordService.getTop10GlanceRecord(sideNum, goldNum);
            }
        }catch(Exception e){
            ret = false;
        }
        retContent = getTop10RecordsContent(noGlanceList, glanceList);
        CommonUtil.writeResponse4ReturnStrResult(ret, retContent, response);
    }

    private String getTop10RecordsContent(List<FcmgRecordBean> noGlanceList, List<FcmgRecordBean> glanceList){
        String ret = "";
        for (int i = 0; i < noGlanceList.size(); i++){
            FcmgRecordBean bean = noGlanceList.get(i);
            ret += bean.getPlayerName() + "," + bean.getCostSecond() + "," + bean.getGlanceTime() + "," + bean.getMoveStepNum() + ";";
        }
        for (int i = 0; i < glanceList.size(); i++){
            FcmgRecordBean bean = glanceList.get(i);
            ret += bean.getPlayerName() + "," + bean.getCostSecond() + "," + bean.getGlanceTime() + "," + bean.getMoveStepNum() + ";";
        }
        if (ret != ""){
            ret = CommonUtil.removeLastWord(ret);
        }
        return ret;
    }
}
