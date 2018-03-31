<?php

require_once 'token.class.php';
// require_once 'info.function.php';

function check_login($user_name,$user_passwd)
{
        global $db;
        $re =$db->has("user", [
                "user_name" => $user_name,
            ]
            );
        if(!$re)
        {
            $return['status'] = 2;
        }
        else
        {
            $re = $db->get("user",[
                "id",
                "user_name",
                "email",
            ],[
                "user_name" => $user_name,
                "user_passwd" => $user_passwd,
            ]
            );
            if($re)
            {
                $return['email'] = $re['email'];
                $token = Token::addToken($re['id']);
                $return['status'] = 1;
                $return['id'] = $re['id'];
                $return['user_name'] = $re['user_name'];
                $return['token'] = $token;
            }
            else
            {
                $return['status'] = 0;
            }
        }
        return $return;

}


function signup($user_name,$user_passwd)
{
    global $db;
    $re =$db->has("user", [
             "user_name" => $user_name,
        ]
        );
    if($re)
    {
        $return['status']=2;
    }
    else
    {
        $re = $db->insert("user", [
            "user_name" => $user_name,
            "user_passwd" => $user_passwd,
        ]
        );
        $re = $db->get('user',['id'],['user_name'=> $user_name]);
        $re = $re['id'];
        $token = Token::addToken($re);
        $return['token'] = $token;
        $return['status']=1;
        $return['id']=$re;
    }
    return $return;
}

function get_user_id($user_name)
{
    global $db;
    $user = $db->get("user",['id'],['user_name'=>$user_name]);
    return $user?$user['id']:"0";
}

function logout($user_id)
{
    global $db;
    $logout = $db->delete('token',['userid'=>$user_id]);
    if($logout)
    {
        return 1;
    }
    else
    {
        return 0;
    }
}
