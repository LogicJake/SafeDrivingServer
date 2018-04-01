<?php

require_once './include/login.function.php';

if(isset($_POST['new_passwd'])) {
    if($_POST['new_passwd'])
    {
        $result['result'] = change_passwd($GLOBALS['uid'],$_POST['new_passwd']);
    }
}

Result::success($result);