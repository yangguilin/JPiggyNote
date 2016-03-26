package com.ygl.piggynote.util;

import com.ygl.piggynote.bean.dingup.*;
import com.ygl.piggynote.common.dingup.CollinsEntityConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 柯林斯字典工具类
 * Created by yanggavin on 16/3/4.
 */
public class CollinsDicUtil {
    private static String COLLINS_SEARCH_API_URL = "http://www.topschool.com/aj/collins/fetchword?word=";
    /**
     * 查询单词
     * @param word 单词
     * @return 查询结果
     */
    public static CollinsSearchResult searchWordFromWebApi(String word){
        CollinsSearchResult csr = null;
        if (word != null && !word.isEmpty()) {
            try {
                // 去空格并统一小写
                word = word.replace(" ", "").toLowerCase().trim();
                String reqUrl = COLLINS_SEARCH_API_URL + word;
                JSONObject jsonObject = HttpRequestUtils.httpGet(reqUrl);
                csr = autoFillDataIntoSearchResult4Word(jsonObject);
                csr.setSearchWord(word);
            } catch (Exception e) {
                csr = null;
            }
        }
        return csr;
    }

    private static CollinsSearchResult autoFillDataIntoSearchResult4Word(JSONObject jsonObject){
        CollinsSearchResult csr = new CollinsSearchResult();
        try {
            if (!jsonObject.containsKey(CollinsEntityConstant.JSON_NODE_NAME_RESULT)){
                return null;
            }
            JSONObject jResultObj = jsonObject.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_RESULT);
            csr.setType(jResultObj.getString(CollinsEntityConstant.JSON_NODE_NAME_TYPE));
            csr.setHeadWord(getHeadWord(jResultObj));
            csr.setInflection(getInflectionList(jResultObj));
            csr.setFreq(getFreq(jResultObj));
            csr.setSenseList(getSenseList(jResultObj));
            csr.setPhvbList(getPhvbList(jResultObj));
        } catch (Exception e){
            csr = null;
        }
        finally {
            return csr;
        }
    }

    /**
     * 获取词条列表(短语)
     * @param jParentObj
     * @return
     */
    private static List<CollinsPhvb> getPhvbList(JSONObject jParentObj){
        List<CollinsPhvb> retList = new ArrayList<CollinsPhvb>();
        if (!checkJSONObjectNull(jParentObj) && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_PHVB)) {
            if (checkJSONArray(jParentObj, CollinsEntityConstant.JSON_NODE_NAME_PHVB)) {
                JSONArray jsonArray = jParentObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_PHVB);
                for (int i = 0; i < jsonArray.size(); i++) {
                    retList.add(getPhvb(jsonArray.getJSONObject(i)));
                }
            } else {
                JSONObject jPhvbObj = jParentObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_PHVB);
                retList.add(getPhvb(jPhvbObj));
            }
        }
        return retList;
    }

    private static CollinsPhvb getPhvb(JSONObject jPhvbObj){
        if (checkJSONObjectNull(jPhvbObj)){
            return null;
        }
        CollinsPhvb cp = new CollinsPhvb();
        cp.setHeadWord(getHeadWord(jPhvbObj));
        cp.setSenseList(getSenseList(jPhvbObj));
        return cp;
    }

    private static List<CollinsSense> getSenseList(JSONObject jParentObj){
        List<CollinsSense> retList = new ArrayList<CollinsSense>();
        if (!checkJSONObjectNull(jParentObj) && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_SENSE)) {
            if (checkJSONArray(jParentObj, CollinsEntityConstant.JSON_NODE_NAME_SENSE)) {
                JSONArray jSenseArr = jParentObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_SENSE);
                for (int i = 0; i < jSenseArr.size(); i++) {
                    CollinsSense cs = getSense(jSenseArr.getJSONObject(i));
                    if (cs != null && cs.getDef() != null && cs.getTran() != null) {
                        retList.add(cs);
                    }
                }
            } else {
                JSONObject jSenseObj = jParentObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_SENSE);
                CollinsSense cs = getSense(jSenseObj);
                if (cs != null && cs.getDef() != null && cs.getTran() != null) {
                    retList.add(cs);
                }
            }
        }
        return retList;
    }

    private static CollinsSense getSense(JSONObject jSenseObj){
        if (checkJSONObjectNull(jSenseObj)){
            return null;
        }
        CollinsSense cs = new CollinsSense();
        cs.setId(getId(jSenseObj));
        cs.setPosp(getPosp(jSenseObj));
        cs.setDef(getDef(jSenseObj));
        cs.setTran(getTran(jSenseObj));
        cs.setExamples(getExampleList(jSenseObj));
        return cs;
    }

    private static List<CollinsExample> getExampleList(JSONObject jSenseItemObj){
        List<CollinsExample> retList = new ArrayList<CollinsExample>();
        if (!checkJSONObjectNull(jSenseItemObj) && jSenseItemObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE)) {
            if (checkJSONArray(jSenseItemObj, CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE)) {
                JSONArray curJExampleArr = jSenseItemObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE);
                for (int i=0; i<curJExampleArr.size(); i++){
                    retList.add(getExample(curJExampleArr.getJSONObject(i)));
                }
            } else {
                JSONObject curJExampleObj = jSenseItemObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE);
                retList.add(getExample(curJExampleObj));
            }
        }
        return retList;
    }

    private static CollinsExample getExample(JSONObject jExampleObj){
        if (checkJSONObjectNull(jExampleObj)){
            return null;
        }
        CollinsExample ce = new CollinsExample();
        ce.setGram(getGram(jExampleObj));
        ce.setEx(getEx(jExampleObj));
        ce.setTran(getTran(jExampleObj));
        return ce;
    }

    private static List<String> getInflectionList(JSONObject jParentObj){
        List<String> retList = new ArrayList<String>();
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_INFLECTION)){
            if (checkJSONArray(jParentObj, CollinsEntityConstant.JSON_NODE_NAME_INFLECTION)){
                JSONArray jsonArray = jParentObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_INFLECTION);
                for (int i=0; i<jsonArray.size(); i++){
                    retList.add(jsonArray.getString(i));
                }
            } else {
                retList.add(jParentObj.getString(CollinsEntityConstant.JSON_NODE_NAME_INFLECTION));
            }
        }
        return retList;
    }

    private static CollinsHeadWord getHeadWord(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_HEAD_WORD)){
            JSONObject curJObject = jParentObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_HEAD_WORD);
            CollinsHeadWord chw = new CollinsHeadWord();
            if (curJObject.containsKey(CollinsEntityConstant.JSON_NODE_NAME_SEARCH)){
                chw.setSearch(curJObject.getString(CollinsEntityConstant.JSON_NODE_NAME_SEARCH));
            }
            if (curJObject.containsKey(CollinsEntityConstant.JSON_NODE_NAME_WORD)){
                chw.setWord(curJObject.getString(CollinsEntityConstant.JSON_NODE_NAME_WORD));
            }
            if (curJObject.containsKey(CollinsEntityConstant.JSON_NODE_NAME_PRON)){
                chw.setPron(curJObject.getString(CollinsEntityConstant.JSON_NODE_NAME_PRON));
            }
            return chw;
        }
        return null;
    }

    private static int getId(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_ID)){
            return jParentObj.getInt(CollinsEntityConstant.JSON_NODE_NAME_ID);
        }
        return 0;
    }

    private static String getPosp(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_POSP)){
            return jParentObj.getString(CollinsEntityConstant.JSON_NODE_NAME_POSP);
        }
        return null;
    }

    private static String getDef(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_DEF)){
            return jParentObj.getString(CollinsEntityConstant.JSON_NODE_NAME_DEF);
        }
        return null;
    }

    private static String getEx(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_EX)){
            return jParentObj.getString(CollinsEntityConstant.JSON_NODE_NAME_EX);
        }
        return null;
    }

    private static String getTran(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_TRAN)){
            return jParentObj.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN);
        }
        return null;
    }

    private static String getGram(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_GRAM)){
            return jParentObj.getString(CollinsEntityConstant.JSON_NODE_NAME_GRAM);
        }
        return null;
    }

    private static int getFreq(JSONObject jParentObj){
        if (jParentObj != null && jParentObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_FREQ)){
            return jParentObj.getInt(CollinsEntityConstant.JSON_NODE_NAME_FREQ);
        }
        return 0;
    }

    private static boolean checkJSONArray(JSONObject jsonObject, String nodeName){
        if (jsonObject.containsKey(nodeName)) {
            String className = jsonObject.get(nodeName).getClass().getName();
            return (className.equalsIgnoreCase("net.sf.json.jsonarray"));
        } else {
            return false;
        }
    }

    private static boolean checkJSONObjectNull(JSONObject jsonObject){
        return (jsonObject == null || jsonObject.size() == 0);
    }
}
