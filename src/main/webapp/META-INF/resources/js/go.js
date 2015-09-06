// class defined
function GoWebEngine(interval, userMsgRecordNum, userMsgDivId){
    this.Interval = interval;
    this.UserMsgRecordNum = userMsgRecordNum;
    this.UserMsgDivId = userMsgDivId;
}
GoWebEngine.prototype.start = function(){
    var monster = createMonster();
    var player = new Player("西瓜", 500, 20, 5, 20);
    startFight(player, monster);
};

function Player(name, life, attack, defend, speed){
    this.Name = name;
    this.Life = life;
    this.Attack = attack;
    this.Defend = defend;
    this.Speed = speed;
}
Player.prototype.attackMonster = function(){

}

function Monster(level, name, life, attack, defend, speed, article){
    this.Level = level;
    this.Name = name;
    this.Life = life;
    this.Attack = attack;
    this.Defend = defend;
    this.Speed = speed;
    this.Article = article;
}
Monster.prototype.attackPlayer = function(){

}

function Boss(name, life, attack, defend){
    Monster(name, life, attack, defend);
}
Boss.prototype = new Monster();
Boss.prototype.constructor = Boss;


// global init.
var g_GoWebEngine = new GoWebEngine(5000, 5, "iDiv_userMessageWindow");


$(function(){
    setInterval(g_GoWebEngine.start, g_GoWebEngine.Interval);
});


function showUserMessage(msg){
    var $UlObj = $("#" + g_GoWebEngine.UserMsgDivId + ">ul");
    var $newItem = $("<li>" + msg + "</li>");
    var recordNum = $UlObj.children("li").length;
    if (recordNum >= g_GoWebEngine.UserMsgRecordNum){
        $UlObj.children("li:first-child").remove();
    }
    $UlObj.append($newItem);
}

function startFight(player, monster){
    if (player == null || monster == null) return;
    // 你一拳我一脚
    var round = 0;
    do{
        round++;
        monster.Life -= player.Attack - monster.Defend;
        if (monster.Life > 0) {
            player.Life -= monster.Attack - player.Defend;
        }
    } while(player.Life > 0 && monster.Life > 0)
    // 战斗结果
    if (player.Life > 0){
        showUserMessage("干掉怪物，获得(" + round + ")点经验");
    } else if (monster.Life > 0){
        showUserMessage("真遗憾，被怪物干掉了，获得(1)点经验");
    }
}

function createMonster(){
    var name = "野狼";
    var life = parseInt(Math.random() * 500);
    var attack = parseInt(Math.random() * 50);
    var defend = parseInt(Math.random() * 10);
    var speed = 10;
    return new Monster("野狼", life, attack, defend, speed);
}