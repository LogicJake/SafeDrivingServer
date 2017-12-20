<?php

require_once './include/login.function.php';

if(isset($_POST['user_name'])&&isset($_POST['user_passwd']))
{
    if($_POST['user_name']&&$_POST['user_passwd'])
    {
        $result['result'] = signup(urldecode($_POST['user_name']),$_POST['user_passwd']);
    }
    else
    {
        $status['status'] = 0;
        $result['result'] = $status['status'];
    }
}
    Result::success($result);