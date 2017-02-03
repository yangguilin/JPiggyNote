package com.ygl.piggynote.controller.dingup;

import com.csvreader.CsvReader;
import com.ygl.piggynote.bean.dingup.*;
import com.ygl.piggynote.common.dingup.CollinsEntityConstant;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;
import com.ygl.piggynote.enums.dingup.WTEListeningSubTypeEnum;
import com.ygl.piggynote.enums.dingup.WTEListeningThirdTypeEnum;
import com.ygl.piggynote.enums.dingup.WordTranExamTypeEnum;
import com.ygl.piggynote.service.dingup.impl.PhraseTranServiceImpl;
import com.ygl.piggynote.service.dingup.impl.WordTranServiceImpl;
import com.ygl.piggynote.util.CollinsDicUtil;
import com.ygl.piggynote.util.CommonUtil;
import com.ygl.piggynote.util.HttpRequestUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yanggavin on 16/2/24.
 */
@Controller
@RequestMapping("/dingup")
public class SearchTranController {
    @Autowired
    private WordTranServiceImpl wordTranService;
    @Autowired
    private PhraseTranServiceImpl phraseTranService;

    @RequestMapping(value = "/searchTran", method = RequestMethod.GET)
    public String index(ModelMap model){
        return "/dingup/searchTran";
    }

    @RequestMapping(value = "/search_word", method = RequestMethod.GET)
    public String searchWord(ModelMap model, HttpServletRequest request){
        String word = request.getParameter("word");
        if (StringUtils.isNotBlank(word)){
            CollinsSearchResult csr = CollinsDicUtil.searchWordFromWebApi(word);
            model.addAttribute("collinsSenseList", csr.getSenseList());
            model.addAttribute("searchWordPron", csr.getHeadWord().getPron());
            model.addAttribute("searchWord", word);
        }
        return "dingup/search_word";
    }

    @RequestMapping(value = "/check_word", method = RequestMethod.GET)
    public String checkWord(ModelMap model, HttpServletRequest request){
        try {
            List<WordTranBean> allList = wordTranService.getAll();
            int passed = 0, deny = 0, waiting = 0;
            for (WordTranBean w : allList){
                if (w.getStatus() == TranStatusEnum.PASSED){
                    passed++;
                } else if (w.getStatus() == TranStatusEnum.DENY){
                    deny++;
                } else {
                    waiting++;
                }
            }
            List<WordTranBean> searchResultList;
            WordTranBean curWordTranBean = new WordTranBean();
            String searchWord = request.getParameter("search_word");
            String latestUpdateIndex = request.getParameter("latest_update_index");
            if (latestUpdateIndex != null && !latestUpdateIndex.isEmpty()){
                searchResultList = wordTranService.getLatestUpdateWord();
            } else if (searchWord != null && !searchWord.isEmpty()){
                searchResultList = wordTranService.searchByWordName(searchWord);
            } else {
                searchResultList = wordTranService.getOneWaitingCheckWord();
            }
            if (searchResultList.size() > 0) {
                curWordTranBean = searchResultList.get(0);
            }
            CollinsSearchResult csr = CollinsDicUtil.searchWordFromWebApi(curWordTranBean.getWordName());

            model.addAttribute("total", allList.size());
            model.addAttribute("waiting", waiting);
            model.addAttribute("passed", passed);
            model.addAttribute("deny", deny);
            model.addAttribute("wtb", curWordTranBean);
            model.addAttribute("collinsSenseList", csr.getSenseList());
            model.addAttribute("searchWordPron", csr.getHeadWord().getPron());
        } catch (Exception e){
            String error = e.getMessage();
        }
        return "dingup/check_word";
    }

    @RequestMapping(value = "/check_phrase", method = RequestMethod.GET)
    public String checkPhrase(ModelMap model, HttpServletRequest request){
        List<String> passWordList = new ArrayList<String>();
        passWordList.add("as");
        passWordList.add("the");
        passWordList.add("a");
        passWordList.add("of");
        passWordList.add("up");
        passWordList.add("out");
        passWordList.add("to");
        passWordList.add("get");
        passWordList.add("be");
        passWordList.add("it");
        passWordList.add("into");
        passWordList.add("on");
        passWordList.add("do");
        passWordList.add("with");
        passWordList.add("set");
        passWordList.add("an");
        passWordList.add("in");
        passWordList.add("have");
        passWordList.add("you");
        passWordList.add("me");
        passWordList.add("and");
        passWordList.add("off");
        passWordList.add("go");
        passWordList.add("its");
        passWordList.add("all");

        try {
            List<PhraseTranBean> allList = phraseTranService.getAll();
            int passed = 0, deny = 0, waiting = 0;
            for (PhraseTranBean w : allList){
                if (w.getStatus() == TranStatusEnum.PASSED){
                    passed++;
                } else if (w.getStatus() == TranStatusEnum.DENY){
                    deny++;
                } else {
                    waiting++;
                }
            }

            List<CollinsPhvb> phvbList = new ArrayList<CollinsPhvb>();
            PhraseTranBean cptb = new PhraseTranBean();
            List<PhraseTranBean> searchResultList = new ArrayList<PhraseTranBean>();
            String searchPhrase = request.getParameter("search_phrase");
            String latestUpdateIndex = request.getParameter("latest_update_index");
            if (latestUpdateIndex != null && !latestUpdateIndex.isEmpty()){
                searchResultList = phraseTranService.getLatestUpdatePhrase();
            } else if (searchPhrase != null && !searchPhrase.isEmpty()){
                searchResultList = phraseTranService.searchByPhraseName(searchPhrase);
            } else {
                searchResultList = phraseTranService.getOneWaitingCheckWord();
            }
            if (searchResultList.size() > 0) {
                cptb = searchResultList.get(0);
                String phrase = cptb.getPhrase();
                // 去... 和 / 和 ~
                phrase = phrase.replace("...", " ");
                phrase = phrase.replace("/", "");
                phrase = phrase.replace("~", "");
                // 本身就是短语的,可以查到结果的;本身不是短语,需要拆解单词,查询短语的
                CollinsSearchResult csr = CollinsDicUtil.searchWordFromWebApi(phrase);
                if (csr != null && csr.getPhvbList() != null && csr.getPhvbList().size() > 0){
                    phvbList = csr.getPhvbList();
                } else {
                    String[] wordArr = phrase.split(" ");
                    if (wordArr.length > 0) {
                        for (int i = 0; i < wordArr.length; i++) {
                            String curWord = wordArr[i].toLowerCase().trim();
                            if (passWordList.contains(curWord)) {
                                continue;
                            }
                            csr = CollinsDicUtil.searchWordFromWebApi(curWord);
                            if (csr != null && csr.getPhvbList() != null && csr.getPhvbList().size() > 0){
                                for (int j=0; j< csr.getPhvbList().size(); j++){
                                    phvbList.add(csr.getPhvbList().get(j));
                                }
                            }
                        }
                    }
                }
            }
            model.addAttribute("total", allList.size());
            model.addAttribute("passed", passed);
            model.addAttribute("cptb", cptb);
            model.addAttribute("collinsPhvbList", phvbList);
        } catch (Exception e){
            String error = e.getMessage();
        }

        return "dingup/check_phrase";
    }

    @RequestMapping(value="/update_word.do", method = RequestMethod.POST)
    public void updateWord(WordTranBean wtb, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try {
            if (wtb.getId()> 0 && wtb.getWordName() != null && wordTranService.existWordName(wtb.getWordName())){
                ret = wordTranService.update(wtb);
                content = ret ? "success" : "fail";
            }
        } catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value="/update_phrase.do", method = RequestMethod.POST)
    public void updatePhrase(PhraseTranBean ptb, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try {
            if (ptb.getId()> 0 && ptb.getPhrase() != null && phraseTranService.existPhrase(ptb.getPhrase())){
                ret = phraseTranService.update(ptb);
                content = ret ? "success" : "fail";
            }
        } catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/update_record_time.do", method = RequestMethod.POST)
    public void updateRecordTime(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try{
            String idStr = request.getParameter("id");
            String wordName = request.getParameter("wordName");
            if (idStr == null || idStr.isEmpty() || wordName == null || wordName.isEmpty()){
                throw new Exception("参数异常");
            }
            int id = Integer.parseInt(idStr);
            ret  = wordTranService.updateRecordTime(id, wordName);
            content = ret ? "success" : "fail";
        }catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/update_phrase_record_time.do", method = RequestMethod.POST)
    public void updatePhraseRecordTime(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try{
            String idStr = request.getParameter("id");
            String wordName = request.getParameter("wordName");
            if (idStr == null || idStr.isEmpty() || wordName == null || wordName.isEmpty()){
                throw new Exception("参数异常");
            }
            int id = Integer.parseInt(idStr);
            ret  = phraseTranService.updateRecordTime(id, wordName);
            content = ret ? "success" : "fail";
        }catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/update_word_name.do", method = RequestMethod.POST)
    public void updateWordName(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try{
            String idStr = request.getParameter("id");
            String oldWordName = request.getParameter("oldWordName");
            String newWordName = request.getParameter("newWordName");
            if (idStr == null || idStr.isEmpty()
                    || oldWordName == null || oldWordName.isEmpty()
                    || newWordName == null || newWordName.isEmpty()){
                throw new Exception("参数异常");
            }
            int id = Integer.parseInt(idStr);
            ret  = wordTranService.updateWordName(id, oldWordName, newWordName);
            content = ret ? "success" : "fail";
        }catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/update_phrase_name.do", method = RequestMethod.POST)
    public void updatePhraseName(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try{
            String idStr = request.getParameter("id");
            String oldWordName = request.getParameter("oldWordName");
            String newWordName = request.getParameter("newWordName");
            if (idStr == null || idStr.isEmpty()
                    || oldWordName == null || oldWordName.isEmpty()
                    || newWordName == null || newWordName.isEmpty()){
                throw new Exception("参数异常");
            }
            int id = Integer.parseInt(idStr);
            ret  = phraseTranService.updatePhraseName(id, oldWordName, newWordName);
            content = ret ? "success" : "fail";
        }catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/delete_word.do", method = RequestMethod.POST)
    public void deleteWord(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        try{
            String idStr = request.getParameter("id");
            String wordName = request.getParameter("wordName");
            if (idStr == null || idStr.isEmpty()
                    || wordName == null || wordName.isEmpty()){
                throw new Exception("参数异常");
            }
            int id = Integer.parseInt(idStr);
            ret  = wordTranService.updateStatus(id, wordName, TranStatusEnum.DELETED);
            content = ret ? "success" : "fail";
        }catch (Exception e){
            content = "更新失败";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    public void reCheckAndFixData(ModelMap model){
        List<WordTranBean> noSearchResultList = wordTranService.getNoSearchResultList();
        int noResultNum = 0;
        for (WordTranBean wtb : noSearchResultList){
            JSONObject jsonObject = searchWordFromRemoteServer(wtb.getWordName());
            if (jsonObject != null) {
                try {
                    if (checkJSONArray(jsonObject, "result") && jsonObject.getJSONArray("result").size() == 0) {
                        noResultNum++;
                    } else {
                        analyseCollinsJsonResult(wtb, jsonObject);
                        if (wtb.getEntityType() != null && !wtb.getEntityType().isEmpty()) {
                            if (!wordTranService.update(wtb)) {
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    String error = e.getMessage();
                }
            }
        }

        model.addAttribute("count", noSearchResultList.size());
        model.addAttribute("noResult", noResultNum);
    }

    public void batchImportPhraseData(){
        List<SearchTranBean> wordList = getWordListFromCsvFile("ts_listening_conversation_words.csv");
        // List<SearchTranBean> wordList = getWordListFromCsvFile("ts_listening_lecture_words.csv");
        // List<SearchTranBean> wordList = getWordListFromCsvFile("ts_speaking_words_and_phrases.csv");
        // List<SearchTranBean> wordList = getWordListFromCsvFile("ts_listening_conversation_phrases.csv");
        // List<SearchTranBean> wordList = getWordListFromCsvFile("ts_listening_lecture_phrases.csv");

        int wordNum = 0, failNum = 0;
        List<PhraseTranBean> resultList = new ArrayList<PhraseTranBean>();

        for (SearchTranBean stb : wordList) {
            try {
                if (stb.getWordName().indexOf(" ") == -1) {
                    PhraseTranBean ptb = new PhraseTranBean();
                    ptb.setPhrase(stb.getWordName().toLowerCase());
                    ptb.setOriginChMeaning(stb.getChineseMeaning().toLowerCase());

                    ptb.setExamType(WordTranExamTypeEnum.LISTENING);
                    ptb.setExamSubType(WTEListeningSubTypeEnum.LECTURE.getValue() + "");
                    // ptb.setExamSubType(stb.getType().toLowerCase());
                    // ptb.setExamThirdType(stb.getType().toLowerCase());

                    resultList.add(ptb);
                }
            } catch (Exception e){
                failNum++;
            }
        }

        // save data to db.
        for (PhraseTranBean ptb : resultList){
            try {
                if (!phraseTranService.add(ptb)) {
                    continue;
                }
            }catch (Exception e){
                String error = e.getMessage();
            }
        }
    }

    public void batchImportData(ModelMap model){
        List<WordTranBean> existList = wordTranService.getAll();
        List<String> existWordNames = new ArrayList<String>();
        for (WordTranBean wtb : existList){
            existWordNames.add(wtb.getWordName());
        }

        List<SearchTranBean> wordList = getWordListFromCsvFile("ts_speaking_words_and_phrases.csv");
        List<SearchTranBean> noResultList = new ArrayList<SearchTranBean>();
        List<SearchTranBean> failList = new ArrayList<SearchTranBean>();
        List<WordTranBean> resultList = new ArrayList<WordTranBean>();

        for (SearchTranBean stb : wordList) {
            try {
                if (stb.getWordName().indexOf(" ") == -1) {
                    JSONObject jsonObject = searchWordFromRemoteServer(stb);
                    if (jsonObject != null) {
                        WordTranBean wtb = analyseCollinsJsonResult(stb, jsonObject);
                        resultList.add(wtb);
                    } else {
                        WordTranBean wtb2 = new WordTranBean();
                        wtb2.setWordName(stb.getWordName());
                        wtb2.setOriginChMeaning(stb.getChineseMeaning());
                        // wtb2.setExamThirdType(stb.getType());
                        wtb2.setExamSubType(stb.getType().toLowerCase());
                        resultList.add(wtb2);

                        noResultList.add(stb);
                    }
                }
            } catch (Exception e){
                failList.add(stb);
            }
        }

        // save data to db.
        for (WordTranBean wtb : resultList){
            // 临时参数
            wtb.setExamType(WordTranExamTypeEnum.SPEAKING);
            // wtb.setExamSubType(WTEListeningSubTypeEnum.LECTURE.getValue() + "");
            // wtb.setExamSubType("conversation");
            // wtb.setExamSubType("lecture");
            // wtb.setExamThirdType(getEnumByStrVal(wtb.getExamThirdType().toLowerCase()).getValue() + "");
            // wtb.setExamThirdType(wtb.getExamThirdType().toLowerCase());
            try {
                if (!wordTranService.add(wtb)) {
                    continue;
                }
            }catch (Exception e){
                String error = e.getMessage();
            }
        }

        model.addAttribute("count", wordList.size());
        model.addAttribute("success", resultList.size());
        model.addAttribute("noResult", noResultList.size());
        model.addAttribute("fail", failList.size());
    }

    private List<SearchTranBean> getWordListFromCsvFile(String fileName){
        List<SearchTranBean> retList = new ArrayList<SearchTranBean>();
        try {
            URL csvFileUrl = Thread.currentThread().getContextClassLoader().getResource("/data/" + fileName);
            CsvReader reader = new CsvReader(csvFileUrl.getFile(), ',', Charset.forName("UTF-8"));
            reader.readHeaders();
            while (reader.readRecord()){
                SearchTranBean stb = new SearchTranBean();
                String rd = reader.getRawRecord();
                stb.setId(Integer.parseInt(reader.get(0).trim()));
                stb.setWordName(reader.get(1).trim());
                stb.setChineseMeaning(reader.get(2).trim());
                stb.setType(reader.get(3).trim());
                retList.add(stb);
            }

        } catch (Exception e){

        }
        return retList;
    }

    private JSONObject searchWordFromRemoteServer(SearchTranBean bean){
        if (bean.getWordName().indexOf(" ") == -1) { // 有空格的暂且归为短语
            String url = "http://study.topschool.com/aj/collins/fetchword?word=" + bean.getWordName();
            return HttpRequestUtils.httpGet(url);
        } else {
            return null;
        }
    }

    private JSONObject searchWordFromRemoteServer(String wordName){
        if (wordName.isEmpty()){ return null; }
        try{
            String url = "http://www.topschool.com/aj/collins/fetchword?word=" + wordName;
            return HttpRequestUtils.httpGet(url);
        }catch (Exception e){
            return null;
        }
    }

    private WordTranBean analyseCollinsJsonResult(SearchTranBean stb, JSONObject jsonObject){
        WordTranBean wtb = new WordTranBean();
        wtb.setWordName(stb.getWordName().toLowerCase().trim());
        wtb.setOriginChMeaning(stb.getChineseMeaning().toLowerCase().trim());
        // wtb.setExamSubType(stb.getType().toLowerCase());
        // wtb.setExamThirdType(stb.getType().toLowerCase());
        // wtb.setExamThirdType(getEnumByStrVal(stb.getType().toLowerCase()).getValue() + "");
        wtb.setStatus(TranStatusEnum.DEFAULT);
        // wtb.setClsFullResult(jsonObject.toString()); // 暂时不保存
        if (jsonObject != null){
            // get chinese tran
            String chineseMeaning = stb.getChineseMeaning();
            String ct = "", cm = "";
            int index = chineseMeaning.indexOf(".");
            if (index != -1){
                ct = chineseMeaning.substring(0, 1).trim();
                cm = chineseMeaning.substring(index + 1).trim();
            }

            // get collins tran list
            try {
                JSONObject curItem = null;
                JSONObject jResultObj = jsonObject.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_RESULT);
                if (checkJSONArray(jResultObj, CollinsEntityConstant.JSON_NODE_NAME_SENSE)) { // 如果示意较多,需要进行内容匹配,如果匹配不到,则默认选择第一个示意.
                    JSONArray jSenseArr = jResultObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_SENSE);
                    for (int i = 0; i < jSenseArr.size(); i++) {
                        curItem = jSenseArr.getJSONObject(i);
                        if (i == 0 || patternChineseMeaning(cm, curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN))) {
                            wtb.setEntityType(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_POSP));
                            wtb.setClsChMeaning(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN));
                            wtb.setClsEnMeaning(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_DEF));
                            getExample(curItem, wtb);
                            if (i > 0){
                                break;
                            }
                        }
                    }
                } else { // 如果只有一个示意,不进行意思匹配,直接记录.
                    JSONObject jSenseObj = jResultObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_SENSE);
                    wtb.setEntityType(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_POSP));
                    wtb.setClsChMeaning(jSenseObj.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN));
                    wtb.setClsEnMeaning(jSenseObj.getString(CollinsEntityConstant.JSON_NODE_NAME_DEF));
                    getExample(jSenseObj, wtb);
                }
            }catch (Exception e){
                String s = e.getMessage();
            }
        }
        return wtb;
    }

    private void analyseCollinsJsonResult(WordTranBean wtb, JSONObject jsonObject){
        if (jsonObject != null){
            // get chinese tran
            String chineseMeaning = wtb.getOriginChMeaning();
            String ct = "", cm = "";
            int index = chineseMeaning.indexOf(".");
            if (index != -1){
                ct = chineseMeaning.substring(0, 1).trim();
                cm = chineseMeaning.substring(index + 1).trim();
            }

            // get collins tran list
            try {
                JSONObject jResultObj = jsonObject.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_RESULT);
                if (checkJSONArray(jResultObj, CollinsEntityConstant.JSON_NODE_NAME_SENSE)) { // 如果示意较多,需要进行内容匹配,如果匹配不到,则默认选择第一个示意.
                    JSONArray jSenseArr = jResultObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_SENSE);
                    for (int i = 0; i < jSenseArr.size(); i++) {
                        JSONObject curItem = jSenseArr.getJSONObject(i);
                        if (i == 0 || patternChineseMeaning(cm, curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN))) {
                            wtb.setEntityType(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_POSP));
                            wtb.setClsChMeaning(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN));
                            wtb.setClsEnMeaning(curItem.getString(CollinsEntityConstant.JSON_NODE_NAME_DEF));
                            getExample(curItem, wtb);
                            if (i > 0){
                                break;
                            }
                        }
                    }
                } else { // 如果只有一个示意,不进行意思匹配,直接记录.
                    JSONObject jSenseObj = jResultObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_SENSE);
                    wtb.setEntityType(jSenseObj.getString(CollinsEntityConstant.JSON_NODE_NAME_POSP));
                    wtb.setClsChMeaning(jSenseObj.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN));
                    wtb.setClsEnMeaning(jSenseObj.getString(CollinsEntityConstant.JSON_NODE_NAME_DEF));
                    getExample(jSenseObj, wtb);
                }
            }catch (Exception e){
                String s = e.getMessage();
            }
        }
    }

    private WTEListeningThirdTypeEnum getEnumByStrVal(String str){
        if (str.equalsIgnoreCase("student&student")){
            return WTEListeningThirdTypeEnum.STUDENT_STUDENT;
        } else if (str.equalsIgnoreCase("student&professor")){
            return WTEListeningThirdTypeEnum.STUDENT_PROFESSOR;
        } else if (str.equalsIgnoreCase("student&employee")){
            return WTEListeningThirdTypeEnum.STUDENT_EMPLOYEE;
        } else if (str.equalsIgnoreCase("student&librarian")){
            return WTEListeningThirdTypeEnum.STUDENT_LIBRARIAN;
        } else if (str.equalsIgnoreCase("professor&student")){
            return WTEListeningThirdTypeEnum.PROFESSOR_STUDENT;
        } else if (str.equalsIgnoreCase("professor&professor")){
            return WTEListeningThirdTypeEnum.PROFESSOR_PROFESSOR;
        } else if (str.equalsIgnoreCase("professor&employee")){
            return WTEListeningThirdTypeEnum.PROFESSOR_EMPLOYEE;
        } else if (str.equalsIgnoreCase("professor&librarian")){
            return WTEListeningThirdTypeEnum.PROFESSOR_LIBRARIAN;
        } else if (str.equalsIgnoreCase("employee&student")){
            return WTEListeningThirdTypeEnum.EMPLOYEE_STUDENT;
        } else if (str.equalsIgnoreCase("employee&professor")){
            return WTEListeningThirdTypeEnum.EMPLOYEE_PROFESSOR;
        } else if (str.equalsIgnoreCase("employee&employee")){
            return WTEListeningThirdTypeEnum.EMPLOYEE_EMPLOYEE;
        } else if (str.equalsIgnoreCase("employee&librarian")){
            return WTEListeningThirdTypeEnum.EMPLOYEE_LIBRARIAN;
        } else if (str.equalsIgnoreCase("librarian&student")){
            return WTEListeningThirdTypeEnum.LIBRARIAN_STUDENT;
        } else if (str.equalsIgnoreCase("librarian&professor")){
            return WTEListeningThirdTypeEnum.LIBRARIAN_PROFESSOR;
        } else if (str.equalsIgnoreCase("librarian&employee")){
            return WTEListeningThirdTypeEnum.LIBRARIAN_EMPLOYEE;
        } else if (str.equalsIgnoreCase("librarian&librarian")){
            return WTEListeningThirdTypeEnum.LIBRARIAN_LIBRARIAN;
        } else {
            return WTEListeningThirdTypeEnum.DEFAULT;
        }
    }

    /**
     * 读取示例
     * @param jSenseItemObj
     * @param wtb
     */
    private void getExample(JSONObject jSenseItemObj, WordTranBean wtb){
        if (jSenseItemObj.containsKey(CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE)) { // 没有示例时,留空.
            if (checkJSONArray(jSenseItemObj, CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE)) { // 多个示例,仅取第一条.
                JSONArray curJExampleArr = jSenseItemObj.getJSONArray(CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE);
                wtb.setClsChEx(curJExampleArr.getJSONObject(0).getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN));
                wtb.setClsEnEx(curJExampleArr.getJSONObject(0).getString(CollinsEntityConstant.JSON_NODE_NAME_EX));
            } else {
                JSONObject curJExampleObj = jSenseItemObj.getJSONObject(CollinsEntityConstant.JSON_NODE_NAME_EXAMPLE);
                wtb.setClsChEx(curJExampleObj.getString(CollinsEntityConstant.JSON_NODE_NAME_TRAN));
                wtb.setClsEnEx(curJExampleObj.getString(CollinsEntityConstant.JSON_NODE_NAME_EX));
            }
        } else {
            wtb.setClsChEx("");
            wtb.setClsEnEx("");
        }
    }

    private boolean checkJSONArray(JSONObject jsonObject, String nodeName){
        if (jsonObject.containsKey(nodeName)) {
            String className = jsonObject.get(nodeName).getClass().getName();
            return (className.equalsIgnoreCase("net.sf.json.jsonarray"));
        } else {
            return false;
        }
    }

    private boolean patternChineseMeaning(String s, String content){
        boolean match = false;
        List<String> conditionList = new ArrayList<String>();
        if (!s.isEmpty()) {
            if (s.indexOf(",") > 0) {
                for (String i : s.split(",")) {
                    conditionList.add(i.trim());
                }
            } else if (s.indexOf(";") > 0) {
                for (String i : s.split(";")) {
                    conditionList.add(i.trim());
                }
            } else {
                conditionList.add(s);
            }
        }
        Pattern pattern = null;
        for (String i : conditionList) {
            pattern = Pattern.compile(".*" + i + ".*");
            match = pattern.matcher(content).matches();
            if (match){ break; }
        }
        return match;
    }
}
