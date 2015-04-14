package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.HoneycombEmptyUnitBean;
import com.ygl.piggynote.bean.HoneycombUnit4UserBean;
import com.ygl.piggynote.bean.HoneycombUnitBaseBean;
import com.ygl.piggynote.bean.HoneycombUnitBean;
import com.ygl.piggynote.service.FcmgService;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanggavin on 15/4/7.
 */
@Service("pnFcmgService")
public class FcmgServiceImpl implements FcmgService {
    private int singleSideUnitNum = 0;
    private int totalGoldUnitNum = 0;
    private List<Integer> rowHeadIndexList = new ArrayList<Integer>();
    private List<Integer> rowTailIndexList = new ArrayList<Integer>();
    List<HoneycombUnitBean> honeycombUnitList = new ArrayList<HoneycombUnitBean>();
    private String startSide;
    private String finishSide;
    private int totalUnitNum;
    private String goldUnitData;

    public String getStartSide() { return startSide; }
    public String getFinishSide() { return finishSide; }
    public int getTotalUnitNum(){ return totalUnitNum; }
    public String getGoldUnitData() { return goldUnitData; }


    @Override
    public void initHoneycomb(int sideUnitNum, int goldUnitNum){
        singleSideUnitNum = sideUnitNum;
        totalGoldUnitNum = goldUnitNum;
        getHoneycombTotalUnitNum();
        getRowHeadAndTailIndex();
        generateHoneycomb();
        generateStartAndFinishUnitSide();
        generateGoldUnitData();
    }

    @Override
    public String generateHoneycombViewData(){
        String viewData = "";
        int index = 0;
        int rowNum = 2 * singleSideUnitNum - 1;
        int rowSize = 4 * singleSideUnitNum - 3;
        int curRowUnitSize = singleSideUnitNum - 1;
        for (int i = 0; i < rowNum; i++){
            if (i < singleSideUnitNum) {
                curRowUnitSize++;
            } else {
                curRowUnitSize--;
            }
            int blankUnitNum = (rowSize - (2 * curRowUnitSize - 1)) / 2;
            for (int j = 0; j < blankUnitNum; j++) {
                viewData += "-,";
            }
            for (int k = 0; k < curRowUnitSize; k++) {
                viewData += (index++) + ",-,";
            }
            for (int m = 0; m < blankUnitNum; m++) {
                viewData += "-,";
            }
            viewData = viewData.substring(0, (viewData.length() - 2));
            viewData += "|,";
        }
        viewData = viewData.substring(0, (viewData.length() - 3));
        return viewData;
    }

    @Override
    public String generateHoneycombData(){
        String data = "";
        String splitWord = ":";
        if (honeycombUnitList.size() > 0){
            for (int i = 0; i < honeycombUnitList.size(); i++){
                HoneycombUnitBean hub = honeycombUnitList.get(i);
                data += hub.getIndex() + splitWord + hub.getLeft() + splitWord + hub.getLeftTop() + splitWord
                        + hub.getRightTop() + splitWord + hub.getRight() + splitWord + hub.getRightBottom() + splitWord + hub.getLeftBottom() + ";";
            }
            data = CommonUtil.removeLastWord(data);
        }
        return data;
    }

    @Override
    public HoneycombUnitBaseBean getCurrentUnit4User(String unitSide){
        HoneycombUnitBaseBean unit = null;
        String[] arr = unitSide.split(",");
        if (arr[1].isEmpty() || arr[1].equals("-1")){
            unit = new HoneycombEmptyUnitBean();
        } else {
            int unitIndex = Integer.parseInt(arr[1]);
            HoneycombUnitBean targetHubUnit = honeycombUnitList.get(unitIndex);
            HoneycombUnit4UserBean huub = new HoneycombUnit4UserBean();
            huub.setIndex(targetHubUnit.getIndex());

            String targetUnitSide = arr[1] + "," + arr[0];
            if (targetHubUnit.getLeft().equals(targetUnitSide)){
                huub.setBack(targetHubUnit.getLeft());
                huub.setFront(targetHubUnit.getRight());
                huub.setBackLeft(targetHubUnit.getLeftTop());
                huub.setBackRight(targetHubUnit.getLeftBottom());
                huub.setFrontLeft(targetHubUnit.getRightTop());
                huub.setFrontRight(targetHubUnit.getRightBottom());
            } else if (targetHubUnit.getLeftTop().equals(targetUnitSide)){
                huub.setBack(targetHubUnit.getLeftTop());
                huub.setFront(targetHubUnit.getRightBottom());
                huub.setBackLeft(targetHubUnit.getRightTop());
                huub.setBackRight(targetHubUnit.getLeft());
                huub.setFrontLeft(targetHubUnit.getRight());
                huub.setFrontRight(targetHubUnit.getLeftBottom());
            } else if (targetHubUnit.getLeftBottom().equals(targetUnitSide)){
                huub.setBack(targetHubUnit.getLeftBottom());
                huub.setFront(targetHubUnit.getRightTop());
                huub.setBackLeft(targetHubUnit.getLeft());
                huub.setBackRight(targetHubUnit.getRightBottom());
                huub.setFrontLeft(targetHubUnit.getLeftTop());
                huub.setFrontRight(targetHubUnit.getRight());
            } else if (targetHubUnit.getRight().equals(targetUnitSide)){
                huub.setBack(targetHubUnit.getRight());
                huub.setFront(targetHubUnit.getLeft());
                huub.setBackLeft(targetHubUnit.getRightBottom());
                huub.setBackRight(targetHubUnit.getRightTop());
                huub.setFrontLeft(targetHubUnit.getLeftBottom());
                huub.setFrontRight(targetHubUnit.getLeftTop());
            } else if (targetHubUnit.getRightTop().equals(targetUnitSide)){
                huub.setBack(targetHubUnit.getRightTop());
                huub.setFront(targetHubUnit.getLeftBottom());
                huub.setBackLeft(targetHubUnit.getRight());
                huub.setBackRight(targetHubUnit.getLeftTop());
                huub.setFrontLeft(targetHubUnit.getRightBottom());
                huub.setFrontRight(targetHubUnit.getLeft());
            } else if (targetHubUnit.getRightBottom().equals(targetUnitSide)){
                huub.setBack(targetHubUnit.getRightBottom());
                huub.setFront(targetHubUnit.getLeftTop());
                huub.setBackLeft(targetHubUnit.getLeftBottom());
                huub.setBackRight(targetHubUnit.getRight());
                huub.setFrontLeft(targetHubUnit.getLeft());
                huub.setFrontRight(targetHubUnit.getRightTop());
            }
            unit = huub;
        }
        return unit;
    }

    private void generateStartAndFinishUnitSide(){
        startSide = "";
        finishSide = "";
        if (rowHeadIndexList.size() > 0) {
            startSide = honeycombUnitList.get(
                    rowHeadIndexList.get(
                            (int)(rowHeadIndexList.size() * Math.random())
                    )
            ).getLeft();
        }
        if (rowTailIndexList.size() > 0) {
            finishSide = honeycombUnitList.get(
                    rowTailIndexList.get(
                            (int)(rowTailIndexList.size() * Math.random())
                    )
            ).getRight();
        }
    }

    private void generateGoldUnitData(){
        goldUnitData = "";
        String startIndex = startSide.split(",")[0];
        String finishIndex = finishSide.split(",")[0];
        List<Integer> list = new ArrayList<Integer>();
        while (list.size() < totalGoldUnitNum){
            int n = (int)(totalUnitNum * Math.random());
            String randomIndex = n + "";
            if (!randomIndex.equals(startIndex) && !randomIndex.equals(finishIndex)){
                list.add(n);
            }
        }
        goldUnitData = list.size() + ":";
        for (int index : list){
            goldUnitData += index + ",";
        }
        if (list.size() > 0){
            goldUnitData = CommonUtil.removeLastWord(goldUnitData);
        }
    }

    private void generateHoneycomb(){
        honeycombUnitList.clear();
        for (int i = 0; i < totalUnitNum; i++){
            HoneycombUnitBean hub = new HoneycombUnitBean();
            hub.setIndex(i);
            String emptySide = i + ",-1";
            String sidePart = i + ",";
            int rowNum = getRowNum(i);
            if (i < singleSideUnitNum){ // 最上面一排
                if (i == 0){
                    hub.setLeft(emptySide);
                } else {
                    hub.setLeft(sidePart + (i - 1));
                }
                if (i == singleSideUnitNum - 1){
                    hub.setRight(emptySide);
                } else {
                    hub.setRight(sidePart + (i + 1));
                }
                hub.setLeftTop(emptySide);
                hub.setRightTop(emptySide);
                hub.setRightBottom(sidePart + (i + singleSideUnitNum + 1));
                hub.setLeftBottom(sidePart + (i + singleSideUnitNum));
            } else if (i >= (totalUnitNum - singleSideUnitNum)) { // 最下面一排
                if (i == (totalUnitNum - singleSideUnitNum)){
                    hub.setLeft(emptySide);
                } else {
                    hub.setLeft(sidePart + (i - 1));
                }
                if (i == totalUnitNum - 1){
                    hub.setRight(emptySide);
                } else {
                    hub.setRight(sidePart + (i + 1));
                }
                hub.setLeftBottom(emptySide);
                hub.setRightBottom(emptySide);
                hub.setLeftTop(sidePart + (i - singleSideUnitNum - 1));
                hub.setRightTop(sidePart + (i - singleSideUnitNum));
            } else { // 中部内容：按照行首、行中部和行尾来分别处理，其中还要针对上部、中部和下部三部分来分别处理
                if (rowHeadIndexList.contains(i)) {
                    hub.setLeft(emptySide);
                    hub.setRight(sidePart + (i + 1));
                    if (rowNum < singleSideUnitNum) {
                        hub.setLeftTop(emptySide);
                        hub.setLeftBottom(sidePart + (i + rowNum + singleSideUnitNum - 1));
                        hub.setRightTop(sidePart + (i - singleSideUnitNum - rowNum + 2));
                        hub.setRightBottom(sidePart + (i + singleSideUnitNum + rowNum));
                    } else if (rowNum > singleSideUnitNum) {
                        hub.setLeftTop(sidePart + (i - (3 * singleSideUnitNum - rowNum)));
                        hub.setLeftBottom(emptySide);
                        hub.setRightTop(sidePart + (i - (3 * singleSideUnitNum - rowNum) + 1));
                        hub.setRightBottom(sidePart + (i + (3 * singleSideUnitNum - rowNum) - 1));
                    } else if (rowNum == singleSideUnitNum) {
                        hub.setLeftTop(emptySide);
                        hub.setLeftBottom(emptySide);
                        hub.setRightTop(sidePart + (i - rowNum - singleSideUnitNum + 2));
                        hub.setRightBottom(sidePart + (i + rowNum +singleSideUnitNum - 1));
                    }
                } else if (rowTailIndexList.contains(i)){
                    hub.setLeft(sidePart + (i - 1));
                    hub.setRight(emptySide);
                    if (rowNum < singleSideUnitNum){
                        hub.setLeftTop(sidePart + (i - rowNum - singleSideUnitNum + 1));
                        hub.setLeftBottom(sidePart + (i + rowNum + singleSideUnitNum - 1));
                        hub.setRightTop(emptySide);
                        hub.setRightBottom(sidePart + (i + singleSideUnitNum + rowNum));
                    } else if (rowNum > singleSideUnitNum) {
                        hub.setLeftTop(sidePart + (i - (3 * singleSideUnitNum - rowNum)));
                        hub.setLeftBottom(sidePart + (i + (3 * singleSideUnitNum - rowNum) - 2));
                        hub.setRightTop(sidePart + (i - (3 * singleSideUnitNum - rowNum) + 1));
                        hub.setRightBottom(emptySide);
                    } else if (rowNum == singleSideUnitNum){
                        hub.setLeftTop(sidePart + (i - rowNum - singleSideUnitNum + 1));
                        hub.setLeftBottom(sidePart + (i + rowNum + singleSideUnitNum - 2));
                        hub.setRightTop(emptySide);
                        hub.setRightBottom(emptySide);
                    }
                } else {
                    hub.setLeft(sidePart + (i - 1));
                    hub.setRight(sidePart + (i + 1));
                    if (rowNum < singleSideUnitNum){
                        hub.setLeftTop(sidePart + (i - rowNum - singleSideUnitNum + 1));
                        hub.setLeftBottom(sidePart + (i + rowNum + singleSideUnitNum - 1));
                        hub.setRightTop(sidePart + (i - rowNum - singleSideUnitNum + 2));
                        hub.setRightBottom(sidePart + (i + rowNum + singleSideUnitNum));
                    } else if (rowNum > singleSideUnitNum) {
                        hub.setLeftTop(sidePart + (i - (3 * singleSideUnitNum - rowNum)));
                        hub.setLeftBottom(sidePart + (i + (3 * singleSideUnitNum - rowNum) - 2));
                        hub.setRightTop(sidePart + (i - (3 * singleSideUnitNum - rowNum) + 1));
                        hub.setRightBottom(sidePart + (i + (3 * singleSideUnitNum - rowNum) - 1));
                    } else if (rowNum == singleSideUnitNum){
                        hub.setLeftTop(sidePart + (i - rowNum - singleSideUnitNum + 1));
                        hub.setLeftBottom(sidePart + (i + rowNum + singleSideUnitNum - 2));
                        hub.setRightTop(sidePart + (i - rowNum - singleSideUnitNum + 2));
                        hub.setRightBottom(sidePart + (i + rowNum + singleSideUnitNum - 1));
                    }
                }
            }
            honeycombUnitList.add(hub);
        }
    }

    private void getHoneycombTotalUnitNum(){
        totalUnitNum = 0;
        // 分为三部分来计算：
        // 奇数：上半部分（两两相加） * 2 + 中间部分
        // 偶数：(上半部分（两两相加） + 上半部分（中间）) * 2 + 中间部分
        totalUnitNum = ((singleSideUnitNum + (2 * (singleSideUnitNum - 1))) * ((singleSideUnitNum - 1) / 2)) * 2 + (singleSideUnitNum * 2 - 1);
        if (singleSideUnitNum % 2 == 0){
            totalUnitNum += ((singleSideUnitNum - 1) / 2 + singleSideUnitNum) * 2;
        }
    }

    private void getRowHeadAndTailIndex(){
        int index = -1;
        for (int i = 0; i < (singleSideUnitNum * 2 - 1); i++){
            int rowUnitNum = singleSideUnitNum + i;
            if (i >= singleSideUnitNum){
                rowUnitNum = 3 * singleSideUnitNum - i - 2;
            }
            for (int j = 0; j < rowUnitNum; j++){
                index++;
                if (j == 0){
                    rowHeadIndexList.add(index);
                }
                if (j == (rowUnitNum - 1)){
                    rowTailIndexList.add(index);
                }
            }
        }
    }

    private int getRowNum(int index){
        int rowNum = 0;
        int unitIndex = -1;
        for (int i = 0; i < (singleSideUnitNum * 2 - 1); i++){
            rowNum++;
            int rowUnitNum = singleSideUnitNum + i;
            if (i >= singleSideUnitNum){
                rowUnitNum = 3 * singleSideUnitNum - i - 2;
            }
            int rowHeadIndex = unitIndex + 1;
            unitIndex += rowUnitNum;
            int rowTailIndex = unitIndex;
            if (index >= rowHeadIndex && index <= rowTailIndex){
                break;
            }
        }
        return rowNum;
    }
}
