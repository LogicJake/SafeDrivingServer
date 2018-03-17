<?php
require_once './include/comment.function.php';

if(isset($_POST['rate'],$_POST['suggestion'],$_POST['tag']))
{
    $result = addcomment($_POST['rate'],$_POST['suggestion'],$_POST['tag']);
    if($result)
        Result::success();
    else
        Result::error();
}
else
    Result::error();