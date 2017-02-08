#Android-PHP-Encrypt-Decrypt using Device IMEI and Simcardserialnumber

Encrypt / Decrypt Between Android and PHP and vice-versa HOW TO USE IT (JAVA)

MCrypt mcrypt = new MCrypt(); 

new MCrypt(con);

/* Encrypt */

String encrypted = MCrypt.bytesToHex( mcrypt.encrypt("Text to Encrypt") );

/* Decrypt */

String decrypted = new String( mcrypt.decrypt( encrypted ) ); HOW TO USE IT (PHP)

$kodiranje = new Decoder();

/* Encrypt */

$encrypted = $kodiranje->encrypt("Text to encrypt");

/* Decrypt */

$decrypted = $kodiranje->decrypt($encrypted);
