package com.example.cocossdkdockingdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.msdk.api.WGPlatform;
import com.tencent.msdk.tools.Logger;
import com.yychina.channel.SdkFactory;

public class MainActivity extends Activity{
	private TextView tvToken,tvUserId;
	private Button btnLoginQQ,btnLoginWEIXIN,btnLogout,btnCharge,btnShare;
	public static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 if (WGPlatform.IsDifferentActivity(this)) {
	            Logger.d("Warning!Reduplicate game activity was detected.Activity will finish immediately.");
	            this.finish();
	            return;
	        }
		context = MainActivity.this;
		setContentView(R.layout.activity_main);
		tvToken = (TextView) findViewById(R.id.tv_token);
		tvUserId = (TextView) findViewById(R.id.tv_uid);
		btnLoginQQ = (Button) findViewById(R.id.btn_login_QQ);
		btnLoginWEIXIN = (Button) findViewById(R.id.btn_login_WEIXIN);
		btnLogout = (Button) findViewById(R.id.btn_logout);
		btnCharge = (Button) findViewById(R.id.btn_charge);
		btnShare = (Button) findViewById(R.id.btn_share);
		
		SdkFactory.mainInit(context);
		
		btnLoginQQ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SdkFactory.loginPlatform("QQ");
			}
		});
		
		btnLoginWEIXIN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SdkFactory.loginPlatform("WEIXIN");
			}
		});
		
		btnLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SdkFactory.logout();
			}
		});
		
	}
	
	// Quit Unity
		@Override protected void onDestroy ()
		{
			SdkFactory.distory();
			super.onDestroy();
		}

		// Pause Unity
		@Override protected void onPause()
		{
			super.onPause();
			SdkFactory.pause();
		}

		// Resume Unity
		@Override protected void onResume()
		{
			super.onResume();
			SdkFactory.resume();
		}
		
		@Override
	    protected void onStop() {
			SdkFactory.stop();
	    	super.onStop();
	    }
	
	
	
}