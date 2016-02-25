package com.ygl.piggynote.controller.dingup;

import com.ygl.piggynote.bean.dingup.SearchTranBean;
import com.ygl.piggynote.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yanggavin on 16/2/24.
 */
@Controller
@RequestMapping("/dingup")
public class SearchTranController {
    @RequestMapping(value = "/searchTran", method = RequestMethod.GET)
    public String index(ModelMap model){

        SearchTranBean stb = new SearchTranBean();
        stb.setId(1);
        stb.setWordName("animal");
        stb.setChineseMeaning("n.动物");
        stb.setType("Student&Professor");
        JSONObject jsonObject = searchWordFromRemoteServer(stb);

        return "/dingup/searchTran";
    }

    private JSONObject searchWordFromRemoteServer(SearchTranBean bean){
        String url = "http://www.topschool.com/aj/collins/fetchword?word=" + bean.getWordName();
        return HttpRequestUtils.httpGet(url);
    }

    private void analyseCollinsJsonResult(JSONObject jsonObject){

    }
}
