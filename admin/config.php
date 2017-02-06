<?php
/*
 * @author Trajilovic Goran
 */

#Database
$basetyp = 'mysql';
$host = 'localhost';
$db_name="database_name"; // Database Name
$db_username="username"; // Database User Name
$db_password="password"; // Database Password

error_reporting( E_ALL & ~E_DEPRECATED & ~E_NOTICE );
ob_start();
session_start();

define('DB_DRIVER', "$basetyp");
define('DB_SERVER', "$host");
define('DB_SERVER_USERNAME', "$db_username");
define('DB_SERVER_PASSWORD', "$db_password");
define('DB_DATABASE', "$db_name");

try {
  $DB = new PDO(DB_DRIVER.':host='.DB_SERVER.';dbname='.DB_DATABASE, DB_SERVER_USERNAME, DB_SERVER_PASSWORD , $dboptions);  
} catch (Exception $ex) {
  echo $ex->getMessage();
  die;
}

require_once 'functions.php';

//get error/success messages
if ($_SESSION["errorType"] != "" && $_SESSION["errorMsg"] != "" ) {
    $ERROR_TYPE = $_SESSION["errorType"];
    $ERROR_MSG = $_SESSION["errorMsg"];
    $_SESSION["errorType"] = "";
    $_SESSION["errorMsg"] = "";
}
?>
