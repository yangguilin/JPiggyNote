<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 15/4/3
  Time: AM11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
  <title>蜂巢迷宫</title>
  <script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" src="/js/jquery.timecircles.js"></script>
  <script type="text/javascript" src="/js/jquery.cookie.js"></script>
  <script type="text/javascript" src="/js/jquery.remodal.min.js"></script>
  <script type="text/javascript" src="/js/common.js"></script>
  <script type="text/javascript" src="/js/fcmg.js"></script>
  <link href="/css/common.css" rel="stylesheet" type="text/css">
  <link href="/css/fcmg.css" rel="stylesheet" type="text/css">
  <link href="/css/jquery.timecircles.css" rel="stylesheet" type="text/css">
  <link href="/css/jquery.remodal.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div>
    <div>
      <input id="iIpt_viewData" type="hidden" value="${viewData}" />
      <input id="iIpt_singleSideUnitNum" type="hidden" value="${singleSideUnitNum}" />
      <input id="iIpt_totalUnitNum" type="hidden" value="${totalUnitNum}" />
      <input id="iIpt_startSide" type="hidden" value="${startSide}" />
      <input id="iIpt_finishSide" type="hidden" value="${finishSide}" />
      <input id="iIpt_honeycombData" type="hidden" value="${honeycombData}" />
      <input id="iIpt_goldUnitData" type="hidden" value="${goldUnitData}" />
    </div>
    <div class="center-container">
      <div class="center-block">
        <div id="iDiv_introduce_c">
          <div>
            <h1>
              <img class="cImg_beeLogo" src="/img/bee.png" />
              蜂巢迷宫
            </h1>
            <p class="cP_remarkText">
              蜂巢迷宫的灵感，来自最强大脑节目第二季中美PK赛的收官战<br/>规则上稍作修改，由简入难，循序渐进，有空儿没空儿练练自己的记忆力吧
            </p>
          </div>
          <br>
          <p>
            <a class="cA_startGame_b" href="javascript:;" onclick="showHoneycombViewToUser()">Start</a>
          </p>
          <p>
            <span class="cSpan_difficultyText">当前难度：</span>
            <select id="iSel_difficultySelect" onchange="updateDifficultyDegree(this)">
              <option value="1">1星</option>
              <option value="2">2星</option>
              <option value="3">3星</option>
              <option value="4">4星</option>
              <option value="5">5星</option>
              <option value="6">6星</option>
              <option value="7">7星</option>
            </select>
          </p>
          <p>
            <a class="cA_remarkText" href="javascript:;">仅以拙作，献给@丽君，普通大脑同样需要锻炼！</a>
          </p>
          <p>
            <a class="cA_gameRuleLink" href="#showGameRule">游戏咋玩</a>
            &nbsp;&nbsp;
            <a class="cA_top10RecordsLink" href="#" onclick="showTop10RecordsModal()"><span id="iSpan_difficultyDegree">1</span>星排行榜</a>
          </p>
        </div>
        <div id="iDiv_overlook_c">
          <div>
            <span class="cSpan_titleText"></span>
            <div class="cDiv_rememberTimer"></div>
          </div>
          <div id="iDiv_honeycombView"></div>
          <div id="iDiv_userOperatePanel_c">
            <table>
              <tr>
                <td style="text-align: right; vertical-align: bottom;">
                  <a class="cA_position_b" href="javascript:;" onclick="goToNextUnit('front_left')">左前</a>
                </td>
                <td style="text-align: center; vertical-align: top;">
                  <a class="cA_position_b" href="javascript:;" onclick="goToNextUnit('front')">正前</a>
                </td>
                <td style="text-align: left; vertical-align: bottom;">
                  <a class="cA_position_b" href="javascript:;" onclick="goToNextUnit('front_right')">右前</a>
                </td>
              </tr>
              <tr class="cTr_playerRow">
                <td></td>
                <td class="cTd_user">
                  <img class="cImg_beeLogo" src="/img/bee.png" />
                </td>
                <td></td>
              </tr>
              <tr>
                <td style="text-align: right; vertical-align: top;">
                  <a class="cA_position_b" href="javascript:;" onclick="goToNextUnit('back_left')">左后</a>
                </td>
                <td style="text-align: center; vertical-align: bottom;">
                  <a class="cA_position_b" href="javascript:;" onclick="goToNextUnit('back')">正后</a>
                </td>
                <td style="text-align: left; vertical-align: top;">
                  <a class="cA_position_b" href="javascript:;" onclick="goToNextUnit('back_right')">右后</a>
                </td>
              </tr>
              <tr>
                <td class="cTd_userMoveInfo" colspan="3">
                  <p>
                    <span>已经收集蜂蜜：</span><span id="iSpan_getGoldUnitNum">0</span>
                    <span id="iSpan_goldUnitTotalNum"></span>
                    <br/><br/>
                    <span>已经移动步骤：</span><span id="iSpan_moveStepNum">0</span>
                    <br/><br/>
                    <span>途中偷看次数：</span><span id="iSpan_glanceTime">0</span>
                  </p>
                </td>
              </tr>
            </table>
          </div>
          <div id="iDiv_operateMenus_c">
            <a id="iA_restart" href="javascript:;" class="blueButton firstButton" onclick="reloadCurrentPage()">重开一局</a>
            <a id="iA_start" href="javascript:;" class="blueButton" onclick="startGame()">开始挑战</a>
            <a id="iA_hint" href="javascript:;" class="blueButton" onclick="showHoneycomb4AMoment()">偷看一眼</a>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="remodal-bg"></div>
  <div class="remodal" data-remodal-id="showGameRule" data-remodal-options="closeOnAnyClick:false">
    <h1>蜂巢迷宫 - 玩法说明</h1>
    <p class="cP_left">
      1. 每局游戏，会随时生成起点(&nbsp;<img class="cImg_small" src="/img/honeycombunit_start.png" />&nbsp;)和终点(&nbsp;<img class="cImg_small" src="/img/honeycombunit_finish.png" />&nbsp;)，并依难度系数随机生成1至N个蜂蜜点(&nbsp;<img class="cImg_small" src="/img/honeycombunit_gold.png" />&nbsp;)
    </p>
    <p class="cP_left">
      2. 游戏开始，先记忆起始点及蜂蜜位置信息，就绪后点击<b>“开始挑战”</b>，通过方向指示按钮移动位置(每点击一次，移动一个格子)，收集所有蜂蜜且达到终点，挑战成功(耗时越少，排名越靠前)。
    </p>
    <p class="cP_left">
      3. <b>“偷看一眼”</b>，真的只能偷看一眼哈
    </p>
    <p class="cP_left">
      4. 玩家在起点时，<b>默认右侧为正前方</b>，切记！
    </p>
    <br>
    <a class="remodal-confirm" href="#">明白了</a>
  </div>
  <div class="remodal" data-remodal-id="showGameResult" data-remodal-options="closeOnAnyClick:false">
    <h1 id="iH_gameResultTitle"></h1>
    <p>
      <span id="iSpan_resultDescription"></span>
    </p>
    <p id="iP_uploadAchievement_c">
      <input id="iIpt_nikeName" type="text" placeholder="请亲输入昵称" onkeyup="checkNikeName(this)" />
      <a id="iA_uploadAchievement" href="javascript:;" class="blueButton" onclick="uploadCurrentAchievement()">上传成绩</a>
    </p>
    <p id="iP_uploadResult_c"></p>
    <br>
    <a id="iA_playAgain" class="remodal-confirm" href="javascript:;" onclick="playThisDifficultyGameAgain()">再玩一次</a>
    <a id="iA_addDifficulty" class="remodal-confirm" href="javascript:;" onclick="increaseDifficulty()">增加难度</a>
    <a id="iA_reduceDifficulty" class="remodal-confirm" href="javascript:;" onclick="reduceDifficulty()">降低难度</a>
    <a class="remodal-confirm" href="javascript:;">复一下盘</a>
  </div>
  <div class="remodal" data-remodal-id="showTop10Records" data-remodal-options="closeOnAnyClick:false">
    <h1>排行榜</h1>
    <p>
      <span id="iSpan_difficulty"></span>
    </p>
    <p>
      <table id="iTbl_top10Records">
        <tr class="cTr_title">
          <td>名次</td>
          <td>玩家昵称</td>
          <td>耗时(秒)</td>
          <td>偷看(次)</td>
          <td>移动(步)</td>
        </tr>
        <tr>
          <td class='cTd_noRecords' colspan='5'>读取数据中...</td>
        </tr>
      </table>
    </p>
    <br>
    <a class="remodal-confirm" href="#">我要挑战</a>
  </div>
</body>
</html>
