<?php

require_once './include/login.function.php';

if(isset($_POST['user_name'],$_POST['user_passwd'])) {
    if($_POST['user_name'] && $_POST['user_passwd']) {
        $result['result'] = check_login(urldecode($_POST['user_name']),$_POST['user_passwd']);
        Result::success($result);
    }

    Result::error('error');
}