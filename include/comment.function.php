<?php
function addcomment($rate,$suggestion,$tag){
    print_r($suggestion);
    $uid = $GLOBALS['uid'];
    global $db;
    $res = $db->insert('comment',[
        'uid'=>$uid,
        'rate' => $rate,
        'suggestion' =>$suggestion,
        'tag' => $tag 
    ]);
    if($res)
        return TRUE;
    else
        return FALSE;
}