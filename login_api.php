<?php



if(isset($_POST['email']) && isset($_POST['password'])  && isset($_POST['category'])){
	$password = $_POST['password'];
	$email = $_POST['email'];
	$category=$_POST['category'];
	$imei = $_POST['IMEI'];
	$serial = $_POST['simnr'];


		$response = array();

	require_once 'core/db_connect.php';

	$db = new DB_CONNECT();
	$sql = mysql_query("UPDATE users SET imei='$imei', sim_serial ='$serial' WHERE user_email='$email'");

	$result = mysql_query("SELECT COUNT(*) FROM users WHERE user_email = '$email' AND category='$category'");
	if($result){
		 if(mysql_result($result, 0) > 0){
		 		$result = mysql_query("SELECT password FROM users WHERE user_email = '$email'");


				if (!$result) {
			   		$response["success"] = 0;
					$response["message"] = "Cannot Login. Please try again";

					echo json_encode($response);
			    	exit;
				}else{
					$row = mysql_fetch_row($result);

					if($row[0]==$password){
						$response["success"] = 1;
						$response["message"] = "Login is successfull";


						$sql="SELECT * FROM users WHERE user_email='$email'";
						$result = mysql_query($sql) or die(mysql_error());
	
						if (mysql_num_rows($result)>0) {

						$response["info"] = array();
						while ($row=mysql_fetch_array($result)) {
							$files=array();
							$files["id"]=$row["user_id"];
							$files["name"]=$row["user_name"];
							$files["number"]=$row["user_num"];

							array_push($response["info"], $files);

						}
					}
						echo json_encode($response);
					}else{
						$response["success"] = 0;
						$response["message"] = "Login email and Password do not match. Please try again.";
						echo json_encode($response);
					}

				}
		 }else{
		 	$response["success"] = 0;
			$response["message"] = "Cannot Login. Email not found";
			echo json_encode($response);
			exit();
		 }
	}
}else{

	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing";
	// echoing JSON response
	echo json_encode($response);

}

?>
