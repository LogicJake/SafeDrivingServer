<?php
require_once "./include/shuttle.function.php";
if(isset($_GET['destination'],$_GET['date'])){
    $destination = $_GET['destination'];
    $date = $_GET['date'];
    $result = getInfo($destination,$date);
}
Result::success($result);