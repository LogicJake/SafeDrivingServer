<?php
require 'PHPMailer/class.phpmailer.php';
require 'PHPMailer/class.smtp.php'; 
function sendMail($to,$title,$content,$single){                       
    $username = '835410808@qq.com';
    $password = 'tupxlujbcnqrbfad';
    $mail = new PHPMailer(true);   
    try {
        //Server settings
        $mail->SMTPDebug = 0;                                 // disable verbose debug output
        $mail->isSMTP();                                      // Set mailer to use SMTP
        $mail->Host = 'smtp.qq.com';  // Specify main and backup SMTP servers
        $mail->SMTPAuth = true;                               // Enable SMTP authentication
        $mail->Username = $username;                 // SMTP username
        $mail->Password = $password;                           // SMTP password
        $mail->SMTPSecure = 'ssl';                            // Enable TLS encryption, `ssl` also accepted
        $mail->Port = 465;                                    // TCP port to connect to
        $mail->CharSet = 'UTF-8';
        //Recipients
        $mail->setFrom($username, '作业提交平台');
        if ($single) {                  //单个收件人
            $mail->addAddress($to);     // Add a recipient
        }
        else
        {
            foreach ($to as $tosingle){ 
                $mail->addAddress($tosingle);     // 添加多个收件人
            } 
        }
        // //Attachments
        // $mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
        // $mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name

        //Content
        $mail->isHTML(true);                                  // Set email format to HTML
        $mail->Subject = $title;
        $mail->Body    = $content;
        $mail->send();
        return 1;
    } catch (Exception $e) {
        return 0;
    }
}
