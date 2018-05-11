<?php

define("DATABASE" , "c571_db");
define("USERS" , "toyota_users");
define("EVENTS" , "toyota_events");
define("ACCESS_PASSWORD" , "H3w3SNjl5VcYX");
define("ACCESS_USER" , "c571_user");


$poST_user  = $_POST['poST_user'];
$poST_cardNumber = $_POST['poST_cardNumber'];
$poST_amount = $_POST['poST_amount'];
$poST_civ = $_POST['poST_civ'];
// todo: remeber to add bank name

$date_ = date();
 ///     username  ,card_number   ,    amount  ,    	civ    ,     usernumber
$con = mysqli_connect("localhost"  ,ACCESS_USER , ACCESS_PASSWORD , DATABASE);
if(mysqli_num_rows(mysqli_query($con , "SELECT * FROM powletbank_card WHERE username ='$poST_user' AND card_number='$poST_cardNumber'  "))>0){
	// exixts
	if(mysqli_query($con , "UPDATE powletbank_card SET amount=amount + '$poST_amount' WHERE  username ='$poST_user' AND card_number='$poST_cardNumber'  ")){
		$arr['message'] = "done";
		echo json_encode($arr);
	exit();
	}
}

if(mysqli_query($con  , "INSERT INTO powletbank_card ( username  ,card_number , amount  ,civ ) VALUES
 ( '$poST_user' ,  '$poST_cardNumber' ,  '$poST_amount' , '$poST_civ'    )" )){
	 $arr['message'] = "done";
	}else{
		 $arr['message'] = "failed";
	}
	

	echo json_encode($arr);
	exit();


?>