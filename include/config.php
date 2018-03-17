<?php
use Medoo\Medoo;

$db = new Medoo([
    'database_type' => 'mysql',
    'database_name' => 'driving_info',
    'server' => 'localhost',
    'username' => 'root',
    'password' => '',
    'charset' => 'utf8'
]);