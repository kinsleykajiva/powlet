<?php

define("DATABASE" , "c571_db");
define("USERS" , "toyota_users");
define("EVENTS" , "toyota_events");
define("ACCESS_PASSWORD" , "H3w3SNjl5VcYX");
define("ACCESS_USER" , "c571_user");

$con = mysqli_connect("localhost"  ,ACCESS_USER , ACCESS_PASSWORD , DATABASE);

$poST_name  = $_POST['poST_name'];
$poST_Surname = $_POST['poST_Surname'];
$poST_password  = $_POST['poST_password'];
$poST_metterNuymber = $_POST['poST_metterNuymber'];
$poST_PhoneNumber  = $_POST['poST_PhoneNumber'];

 $arr=array();
 
if(mysqli_num_rows(mysqli_query($con , "SELECT *  FROM powlet_users WHERE user_number = '$poST_PhoneNumber' AND meter_number = '$poST_metterNuymber' "))<1){
  //
   
   $arr['message'] = mysqli_query($con , "INSERT INTO powlet_users (name , surname , password,   meter_number ,user_number  )  VALUES ('$poST_name' , '$poST_Surname' , '$poST_password' , '$poST_metterNuymber' , '$poST_PhoneNumber')") 
   ? "done" : "failed";
}else{
    
    $arr['message'] = "found";
}
echo json_encode($arr);
	exit();

?>