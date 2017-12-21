<?php

require_once './include/collect.function.php';

if(isset($_POST['name'],$_POST['id'],$_POST['data'])) {
    if($_POST['name']&&$_POST['id']&&$_POST['data']) {
        $result['result'] = collect(urldecode($_POST['name']),$_POST['id'],$_POST['data']);
        Result::success($result);
    }

    Result::error('error');
}