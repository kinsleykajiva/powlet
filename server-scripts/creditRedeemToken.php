<?php

define("DATABASE" , "c571_db");
define("USERS" , "toyota_users");
define("EVENTS" , "toyota_events");
define("ACCESS_PASSWORD" , "H3w3SNjl5VcYX");
define("ACCESS_USER" , "c571_user");


$poST_user = $_POST['poST_user'];

$poST_pin  = $_POST['poST_pin'];

$poST_redeem  = $_POST['poST_redeem'];

$poST_number = $_POST['poST_number'];


$date_ = date();

$con = mysqli_connect("localhost"  ,ACCESS_USER , ACCESS_PASSWORD , DATABASE);

// userNumber      ,      balance        ,     date_
if(mysqli_num_rows(mysqli_query($con, "SELECT * FROM powlet_users WHERE user_number='$poST_number'  AND password = '$poST_pin'  "))<1){
    mysqli_query($con , "INSERT INTO powlet_credit_tokens (userNumber , balance, date_) VALUES ( '$poST_number', '500', NOW()) ");
   /* $arr['message'] = "SELECT * FROM powlet_users WHERE user_number='$poST_number'  AND password = '$poST_pin' ";
    echo json_encode($arr);
    exit();
    $arr['message'] = "user-unfound";
    echo json_encode($arr);
	exit();*/
}

/*if(mysqli_num_rows(mysqli_query($con, "SELECT * FROM powlet_credit_tokens WHERE userNumber='$poST_number' "))<1){
    $arr['message'] = "unfound";
    echo json_encode($arr);
	exit();
}else{*/
    $bal = mysqli_fetch_assoc(mysqli_query($con , "SELECT * FROM powlet_credit_tokens WHERE userNumber='$poST_number'"))['balance'] ;
    if( (float) $bal <  1){
        $arr['message'] = "broke";
        echo json_encode($arr);
        exit();
    }
/*}
*/
$sql = "UPDATE powlet_credit_tokens SET balance= balance -'$poST_redeem' WHERE  userNumber = '$poST_number' ";


if(mysqli_query($con  ,$sql  )){
     $arr['message'] = "done";
     $bal =mysqli_fetch_assoc(mysqli_query($con , "SELECT * FROM powlet_credit_tokens WHERE userNumber='$poST_number'"))['balance'];
     $arr['balance'] = $bal;
	}else{
		 $arr['message'] = "failed";
	}


echo json_encode($arr);
	exit();

?>