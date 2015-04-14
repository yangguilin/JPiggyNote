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
          <br>
          <p>
            <a class="cA_remarkText" href="javascript:;">仅以拙作，献给@丽君，普通大脑同样需要锻炼！</a>
          </p>
          <p>
            <a class="cA_gameRuleLink" href="#showGameRule">游戏咋玩</a>
          </p>
        </div>
        <div id="iDiv_overlook_c">
          <div>
            <span class="cSpan_titleText">时间在飞快地奔跑着</span>
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
            <a id="iA_restart" href="javascript:;" class="grayButton firstButton" onclick="reloadCurrentPage()">重新开始</a>
            <a id="iA_start" href="javascript:;" class="grayButton" onclick="startGame()">开始挑战</a>
            <a id="iA_hint" href="javascript:;" class="grayButton" onclick="showHoneycomb4AMoment()">偷看一眼</a>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="remodal-bg"></div>
  <div class="remodal" data-remodal-id="showGameRule" data-remodal-options="closeOnAnyClick:false">
    <h1>蜂巢迷宫 - 玩法说明</h1>
    <p class="cP_left">
      1. 每局游戏开始时，系统随时生成玩家的起始点（起点为粉色，终点为绿色），同时根据难度系数来随机生成1至N个蜂蜜标记房间（橙色）
    </p>
    <p class="cP_left">
      2. 游戏分两步：首先，记忆起始点和蜂蜜位置，然后点击“<b>开始挑战</b>”，通过留个方向移动，收集到所有的蜂蜜，且顺利达到终点，即挑战成功
    </p>
    <p class="cP_left">
      3. 游戏过程中，玩家可随时通过“<b>偷看一眼</b>”，快速查看自己的位置，不过只有1秒哦
    </p>
    <p>
      <b>小提示：</b>玩家在起点时，默认右侧为正前方，切记哦
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
    <a id="iA_playAgain" class="remodal-confirm" href="javascript:;" onclick="reloadCurrentPage()">再玩一次</a>
    <a id="iA_addDifficulty" class="remodal-confirm" href="javascript:;" onclick="increaseDifficulty()">增加难度</a>
    <a id="iA_reduceDifficulty" class="remodal-confirm" href="javascript:;" onclick="reduceDifficulty()">降低难度</a>
  </div>
</body>
</html>
