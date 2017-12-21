<?php
function collect($name,$user_id,$data)
{
    global $db;
    if ($name == 'GYROSCOPE') {
        $re = $db->insert("gyroscope", [
            "user_id" => $user_id,
            "data" => $data,
        ]
        );
        if ($re->rowCount()>0) {
            $return['status']=1;
            return $return;
        }
        
    }
    elseif ($name == 'ACCELEROMETER') {
        $re = $db->insert("accelerometer", [
            "user_id" => $user_id,
            "data" => $data,
        ]
        );
        if ($re->rowCount()>0) {
            $return['status']=1;
            return $return;
        }
    }

}