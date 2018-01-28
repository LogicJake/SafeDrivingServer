<?php

require_once './include/login.function.php';
$result = logout($GLOBALS['uid']);
if($result)
    Result::success($result);
else
    Result::error($result);