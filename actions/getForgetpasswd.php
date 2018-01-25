<?php
require_once './include/login.function.php';

if(isset($_GET['user_name']))
{
    if($_GET['user_name'])
    {
        $result['user_id'] = get_user_id($_GET['user_name']);
        $result['token'] = Token::addToken($result['user_id']);
    }
}
Result::success($result);