package com.projekt.xyz;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.globcast.vaz.util.JSONParser;
import com.globcast.vaz.util.UserInfo;
import com.globcast.vaz.util.Util;


public class LoginActivity extends Activity implements OnClickListener {

	Button submitBtn;
	EditText emailEt,passwordEt;
	SharedPreferences sh;

	JSONParser jparser=new JSONParser();

	public static final String loginURL = "http://url.com/login.php";
	Context con;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		con=LoginActivity.this;
		
		sh=getSharedPreferences("CRUZER_PREF", MODE_PRIVATE);
		
		init();

		String email=sh.getString("loginemail", null);
		String pass=sh.getString("loginpass", null);
		boolean driver=sh.getBoolean("type", false);
		
		if(email!=null && pass!=null && driver){
			emailEt.setText(email);
			passwordEt.setText(pass);
			submitBtn.performClick();

		}

	}

	private void init() {

		submitBtn=(Button) findViewById(R.id.loginSubmitBtn);

		submitBtn.setOnClickListener(this);

		emailEt=(EditText) findViewById(R.id.loginEmailEt);
		passwordEt=(EditText) findViewById(R.id.loginPassEt);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		if(!Util.isConnectingToInternet(this)){
			Util.showNoInternetDialog(this);
			return;
		}

		switch (v.getId()) {
		case R.id.loginSubmitBtn:
			if(!TextUtils.isEmpty(emailEt.getText().toString())){
				if(!TextUtils.isEmpty(passwordEt.getText().toString())){
					new Login().execute("driver");
				}else Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
			}

		}
	}

	class Login extends AsyncTask<String, String, String>{
		ProgressDialog pDialog;
		String s="";
		int success=-1;
		int error=0;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog=new ProgressDialog(con);
			pDialog.setMessage("Login is processing......");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		boolean driver=false;
		String email="";
		String password="";

		@SuppressWarnings("WrongThread")
		@Override
		protected String doInBackground(String... st) {


			if(st!=null && st[0].equals("driver")){

				email=emailEt.getText().toString();
				password=passwordEt.getText().toString();
				driver=true;
			}
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			String deviceid = telephonyManager.getSimSerialNumber();
			String serial = deviceid.substring(Math.max(0, deviceid.length() - 16));
			String code = telephonyManager.getDeviceId();


			List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("simnr", "" +serial));
			params.add(new BasicNameValuePair("IMEI", "A" +code));

			if(driver)params.add(new BasicNameValuePair("category", "driver"));
			else params.add(new BasicNameValuePair("category", "client"));

			UserInfo.setEmail(email);
			UserInfo.setPassword(password);

			try {
				JSONObject jobj=jparser.makeHttpRequest(loginURL, "POST", params);
				success=jobj.getInt("success");
				s=jobj.getString("message");
				
				if(success==1){
					JSONObject job=jobj.getJSONArray("info").getJSONObject(0);
					UserInfo.setName(job.getString("name"));
					UserInfo.setPhonenumber(job.getString("number"));
					UserInfo.setId(job.getString("id"));
				}

			} catch (JSONException e) {
				error=1;
			}catch(Exception e){
				error=1;
			}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();

			if(error==1){
				if(Util.isConnectingToInternet(con)){
					Toast.makeText(con, "Server is down, Please try again later", Toast.LENGTH_SHORT).show();
				}else
					Util.showNoInternetDialog(con);
				return;
			}

			if(success==0){
				Toast.makeText(con, s, Toast.LENGTH_LONG).show();
			}else if(success==1){
				SharedPreferences.Editor edit=sh.edit();
				edit.putString("loginemail", email);
				edit.putString("loginpass", password);
				edit.putBoolean("type", driver);
				edit.commit();


				Intent i=new Intent(con, DriverPositionActivity.class);
				if(driver)
					i=new Intent(con, DriverActivity.class);

				startActivity(i);
				Toast.makeText(con, s, Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
}
