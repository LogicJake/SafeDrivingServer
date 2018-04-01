<?php
require_once './include/login.function.php';

if(isset($_GET['user_name']))
{
    if($_GET['user_name'])
    {
        $result['email'] = get_email($_GET['user_name']);
    }
}
Result::success($result);