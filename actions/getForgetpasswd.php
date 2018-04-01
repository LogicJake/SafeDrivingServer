<?php
require_once './include/login.function.php';

if(isset($_GET['user_name']))
{
    if($_GET['user_name'])
    {
        $result = get_email($_GET['user_name']);
        if($result['status']==1)
            $result['token'] = Token::addToken($result['id']);
    }
}
Result::success($result);