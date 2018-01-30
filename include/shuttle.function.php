<?php

function getInfo($destination,$date){
    $dbname = "shuttle_info_".$destination;
    global $db;
    $res=$db->has($dbname,[         //检查该日期是不是特殊情况
        "AND" =>[        
            "type" => 2,
            "start_date[<=]" => $date,
            "end_date[>=]" => $date
        ]
    ]);
    if($res)        //如果有说明是特殊情况
    {
        $res = $db->select($dbname,[
            'time',
            'car_num',
            'east',
            'lancui'
        ],[
                'type' => 2,
                'start_date[<=]' => $date,
                'end_date[>=]' => $date
            ]);
    }
    else{           //一般情况，判断周中周末
        date_default_timezone_set("PRC");
        $week_day = date("w",$date);
        if($week_day == 0 || $week_day == 6){       //周末
            $res = $db->select($dbname,[
                'time',
                'car_num',
                'east',
                'lancui'
            ],[
                    'type' => 1,
            ]);
        }
        else
        {       
            $res = $db->select($dbname,[
                'time',
                'car_num',
                'east',
                'lancui'
            ],[
                'type' => 0,
            ]);
        }
    }
    return $res;
}