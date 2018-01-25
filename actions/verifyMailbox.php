<?php
require_once './include/mail.function.php';
if (!isset($_GET['action_type'])) {
	Result::error('missing action_type');
}
else
{
	$action_type = $_GET['action_type'];
	switch($action_type){
    case 'sendCode':
    	if (!isset($_GET['mail']))
    		Result::error('missing mail');
        SendCode();
        break;
    case 'verifyCode':
        if (!isset($_GET['code']))
    		Result::error('missing code');
    	verifyCode();
        break;
	}
	
}

function SendCode(){
	global $db;
	$mail = $_GET['mail'];
	$code = GetRandStr(4);
	$uid = $GLOBALS['uid'];
	$expireTime = 10*60;		//过期时间10min
	$re =$db->insert("verify", [
            "userid" => $uid,
            'verifyCode' => $code,
            'add_time' => time(),
            'expire' => time() + $expireTime
        	]
        );

	$res = sendMail($mail,'邮箱验证',"您的此次验证码为：{$code},如果不是本人操作请忽略。",true);
	if ($res == 1)
		Result::success('send success');
	else
		Result::error('send failed');
}

function verifyCode(){
	$code = $_GET['code'];
	$uid = $GLOBALS['uid'];
	global $db;
	$res = $db->has("verify",[
		 "AND" => [
		 	'userid' => $uid,
		 	'verifyCode' => $code,
		 	'expire[>]' => time()		//没有过期 
		 ]
		]);
	if($res)
		Result::success('success');
	else
		Result::error('fail');
}
/*
	获取位长为len的数字字母验证码
*/

function GetRandStr($len) 
{ 
    $chars = array( 
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",  
        "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",  
        "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",  
        "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",  
        "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2",  
        "3", "4", "5", "6", "7", "8", "9" 
    ); 
    $charsLen = count($chars) - 1; 
    shuffle($chars);   
    $output = ""; 
    for ($i=0; $i<$len; $i++) 
    { 
        $output .= $chars[mt_rand(0, $charsLen)]; 
    }  
    return $output;  
} 