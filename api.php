<?php
if(isset($_POST['user_email'])){
$response = array();
require_once 'core/db_connect.php';
include 'core/encoder_class.php';
require_once('admin/config.php');
require_once('includes/database.class.php');

$db= new database($pdo);
$db = new DB_CONNECT();
//Pass your driver number here


$driver_email = $_POST['user_email'];
$config['Decoder'] = array();
$sql = "SELECT * FROM users WHERE user_email = '$driver_email'";

$stmt = $pdo->prepare($sql);
$stmt->execute();
$users = $stmt->fetchAll();
foreach($users as $citai);
$config['Decoder']['kljuc'] = $citai["kljuc"];
$config['Decoder']['key'] = $citai["superkljuc"];

/// ENDE CITANJE KLJUCA IZ BAZE PODATAKA ///

#Encrypt
$kodiranje = new Decoder();


$result = mysql_query("select * from transport where driver_email = '$driver_email' ORDER BY termin "); 


	if($result){
		$response["ridelist"] = array();
		while ($rowBooking=mysql_fetch_array($result)) {
					$infos=array();
					$infos["id"]=$kodiranje->encrypt($rowBooking["id"]);
					$infos["sender_id"]=$kodiranje->encrypt($rowBooking["sender_id"]);
					$infos["name"]=$kodiranje->encrypt($rowBooking["name"]);
          $infos["accept"]=$rowBooking["accept"];


					array_push($response["ridelist"], $infos);
				}
			$response["success"] = 1;
			$response["message"] = "Loading Data successful.";
			echo json_encode($response);
		}else{
			$response["success"] = 0;
			$response["message"] = "Could not load data".mysql_error();
			echo json_encode($response);
		}
}else{

	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing";
	// echoing JSON response
	echo json_encode($response);

}
?>
