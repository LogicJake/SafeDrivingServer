<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: Content-Type');
session_start();

require_once './include/Medoo.php';
require_once './include/config.php';
require_once './include/result.class.php';
//require_once './include/token.class.php';

// white list
$actionList = ['collect','postSignup','postLogin'];          //所有action列表

$noTokenList = ['collect','postSignup','postLogin'];         //不需要token的action

if (!isset($_GET['_action'])) {
    Result::error('missing _action');
}
if (!in_array($_GET['_action'], $actionList)) {
    Result::error('_action error');
}

if (in_array($_GET['_action'], $noTokenList)){//如果是不需要token的 action 直接进入
    require_once "actions/{$_GET['_action']}.php";

} else {//token验证
    if (!isset($_GET['token'])){//无token错误
        Result::error('no token');
    }
    if(Token::userid($_GET['token']) < 1){//token不存在终止
        Result::error('token wrong');
    }
    //其余为正确情况 全局并进入action
    $GLOBALS['uid'] = Token::userid($_GET['token']);
    require_once "actions/{$_GET['_action']}.php";
}