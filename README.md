# MCrypt-for-Android
Encrypt decrypt using Android and PHP


MCrypt mcrypt = new MCrypt(); // 	new MCrypt(myAppContext);

****** Encrypt *****
String encrypted = MCrypt.bytesToHex( mcrypt.encrypt("Text to Encrypt") );
 ***** Decrypt *****
String decrypted = new String( mcrypt.decrypt( encrypted ) );

