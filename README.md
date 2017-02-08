#Android-PHP-Encrypt-Decrypt using Device IMEI and Simcardserialnumber

Encrypt / Decrypt Between Android and PHP and vice-versa HOW TO USE IT (JAVA)

MCrypt mcrypt = new MCrypt(); 

new MCrypt(con);

/* Encrypt */

String encrypted = MCrypt.bytesToHex( mcrypt.encrypt("Text to Encrypt") );

/* Decrypt */

String decrypted = new String( mcrypt.decrypt( encrypted ) ); 

exemple for Android:
##############################################################################################
				(--> new MCrypt(myAPPcontext); //using with Context)
            MCrypt mcrypt = new MCrypt();
						String id = new String( MCrypt.decrypt(job.getString("id") ));
						String name = new String( MCrypt.decrypt(job.getString("name") ));

HOW TO USE IT (PHP)

$kodiranje = new Decoder();

/* Encrypt */

$encrypted = $kodiranje->encrypt("Text to encrypt");

/* Decrypt */

$decrypted = $kodiranje->decrypt($encrypted);
