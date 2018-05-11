<?php

define("DATABASE" , "c571_db");
define("USERS" , "toyota_users");
define("EVENTS" , "toyota_events");
define("ACCESS_PASSWORD" , "H3w3SNjl5VcYX");
define("ACCESS_USER" , "c571_user");

$con = mysqli_connect("localhost"  ,ACCESS_USER , ACCESS_PASSWORD , DATABASE);

$poST_user  = $_POST['poST_user'];
$poST_TransNumber = $_POST['poST_TransNumber'];
$poST_amount_  = $_POST['poST_amount_'];
$poST_pin  = $_POST['poST_pin'];
$date_ = date();

if(mysqli_num_rows(mysqli_query($con , "SELECT * FROM powlet_users WHERE password  = '$poST_pin' AND user_number = '$poST_user'  "))<1){

	/* $arr['message'] ="SELECT * FROM powlet_users WHERE password  = '$poST_pin' AND user_number = '$poST_user' ";
	 echo json_encode($arr);
	exit();*/
	 $arr['message'] = "user-unfound";
	 echo json_encode($arr);
	exit();
}


$TABLENAME = "powlet_tokenPuchases";
 $arr=array();
 function crypto_rand_secure($min, $max)
{
    $range = $max - $min;
    if ($range < 1) return $min; // not so random...
    $log = ceil(log($range, 2));
    $bytes = (int) ($log / 8) + 1; // length in bytes
    $bits = (int) $log + 1; // length in bits
    $filter = (int) (1 << $bits) - 1; // set all lower bits to 1
    do {
        $rnd = hexdec(bin2hex(openssl_random_pseudo_bytes($bytes)));
        $rnd = $rnd & $filter; // discard irrelevant bits
    } while ($rnd > $range);
    return $min + $rnd;
}
function getToken($length)
{
    $token = "";
    $codeAlphabet = "0123456789";
    
    $max = strlen($codeAlphabet); // edited

    for ($i=0; $i < $length; $i++) {
        $token .= $codeAlphabet[crypto_rand_secure(0, $max-1)];
    }

    return $token;
}
$token =  getToken(4) . getToken(6) ;
 if(mysqli_query($con  , "INSERT INTO $TABLENAME  (user_numnber , trancNumebr , amount , token , date_ ) VALUES ('$poST_user','$poST_TransNumber','$poST_amount_','$token','$date_') ")){
       $arr['message'] = "done";
       $arr['token']  = $token;
 	}else{
 		 $arr['message'] = "found";
 	}
 

echo json_encode($arr);
	exit();

?>