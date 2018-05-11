<?php

define("DATABASE" , "c571_db");
define("USERS" , "toyota_users");
define("EVENTS" , "toyota_events");
define("ACCESS_PASSWORD" , "H3w3SNjl5VcYX");
define("ACCESS_USER" , "c571_user");


$pos_name  = $_POST['poST_number'];
$poST_user  = $_POST['poST_user'];
$report = $_POST['poST_report_messsge'];
$date_ = date();

$con = mysqli_connect("localhost"  ,ACCESS_USER , ACCESS_PASSWORD , DATABASE);

if(mysqli_query($con  , "INSERT INTO powlet_fault_reports (userNumber ,username , report , date_) VALUES ( '$poST_user' ,'$pos_name',  '$report' ,  '$date_'   )" )){
	 $arr['message'] = "done";
	}else{
		 $arr['message'] = "found";
	}

	echo json_encode($arr);
	exit();


?>