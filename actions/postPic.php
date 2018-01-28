<?php
if(isset($_POST['image'])) {
    global $db;
    $res = $db->has('avator',
        [
            'user_id' => $GLOBALS['uid']
        ]);
    if($res){
        $db->update('avator',
        [
            'avator' => $_POST['image'],
            'time' => time()
        ],[
            'user_id' => $GLOBALS['uid']
        ]);
    }
    else{
        echo time();
        $db->insert('avator',
        [
            'avator' => $_POST['image'],
            'user_id' => $GLOBALS['uid'],
            'time' => time()
        ]);
    }
}