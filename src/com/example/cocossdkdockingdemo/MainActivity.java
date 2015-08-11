package com.example.cocossdkdockingdemo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.msdk.api.WGPlatform;
import com.tencent.msdk.tools.Logger;
import com.yychina.channel.SdkFactory;

public class MainActivity extends Activity {
	private TextView tvToken, tvUserId;
	private Button btnLoginQQ, btnLoginWEIXIN, btnLogout, btnCharge, btnShare;
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

		String initResult = SdkFactory.getJsonResult("init");

		// String logoutResult = SdkFactory.getJsonResult("logout");
		// String paymentResult = SdkFactory.getJsonResult("payment");

		try {
			JSONObject initResultJsonObj = new JSONObject(initResult);
			if (initResultJsonObj.getString("status").equals("logined")) {
				Toast.makeText(MainActivity.this, "已经登录，本地票据有效，将自动登录并显示token",
						Toast.LENGTH_SHORT).show();
				tvToken.setText(initResultJsonObj.getString("token"));
				btnLoginQQ.setVisibility(View.INVISIBLE);
				btnLoginWEIXIN.setVisibility(View.INVISIBLE);
			} else {
				Toast.makeText(MainActivity.this, "尚未登录，请点击登录按钮",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btnLoginQQ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SdkFactory.loginPlatform("QQ");

					new Thread(new Runnable() {

						@Override
						public void run() {
							String loginResult = SdkFactory
									.getJsonResult("login");
							Message msg = handler.obtainMessage();
							msg.obj = loginResult;
							handler.sendMessage(msg);
						}
					}).start();

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
				Toast.makeText(MainActivity.this, "已注销", Toast.LENGTH_SHORT)
						.show();
				btnLoginQQ.setVisibility(View.VISIBLE);
				btnLoginWEIXIN.setVisibility(View.VISIBLE);
				tvToken.setText("");
			}
		});

	}

	// Quit Unity
	@Override
	protected void onDestroy() {
		SdkFactory.distory();
		super.onDestroy();
	}

	// Pause Unity
	@Override
	protected void onPause() {
		super.onPause();
		SdkFactory.pause();
	}

	// Resume Unity
	@Override
	protected void onResume() {
		super.onResume();
		SdkFactory.resume();
	}

	@Override
	protected void onStop() {
		SdkFactory.stop();
		super.onStop();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String loginResult = (String) msg.obj;
			try {
				Log.i("HuangKe----->", "主UI线程能拿到的登录结果串是：" + loginResult);
				JSONObject loginResultJsonObj = new JSONObject(loginResult);
				if (loginResultJsonObj.getString("status").equals("success")) {
					Toast.makeText(MainActivity.this, "登录成功！",
							Toast.LENGTH_SHORT).show();
					tvToken.setText(loginResultJsonObj.getString("token"));
					btnLoginQQ.setVisibility(View.INVISIBLE);
					btnLoginWEIXIN.setVisibility(View.INVISIBLE);
				}
				if (loginResultJsonObj.getString("status").equals("cancel")) {
					Toast.makeText(MainActivity.this, "登录取消！",
							Toast.LENGTH_SHORT).show();
				}
				if (loginResultJsonObj.getString("status").equals("fail")) {
					Toast.makeText(MainActivity.this, "登录失败！",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

}