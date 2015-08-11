package com.yychina.channel.allsdk;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.msdk.api.LoginRet;
import com.tencent.msdk.api.MsdkBaseInfo;
import com.tencent.msdk.api.TokenRet;
import com.tencent.msdk.api.WGPlatform;
import com.tencent.msdk.api.WGPlatformObserver;
import com.tencent.msdk.api.WGQZonePermissions;
import com.tencent.msdk.api.WakeupRet;
import com.tencent.msdk.consts.CallbackFlag;
import com.tencent.msdk.consts.EPlatform;
import com.tencent.msdk.consts.TokenType;
import com.tencent.msdk.remote.api.RelationRet;
import com.tencent.msdk.tools.Logger;
import com.yychina.channel.SdkBaseFactory;

public class Sdk_TencentMSdk implements SdkBaseFactory {
	private static String tag;
	private static Context context;
	private String token;
	private String weixinToken;
	private String userId;
	private MsdkBaseInfo baseInfo;
	private long pauseTime = 0;
	protected static int platform = EPlatform.ePlatform_None.val();
	JSONObject dataJsonObjInit = new JSONObject();
	String jsonResultInit;

	@Override
	public void mainInit(Context c) {
		context = c;
		Log.i("HuangKe----->", context.getPackageName());

		baseInfo = new MsdkBaseInfo();
		baseInfo.qqAppId = "100703379";
		baseInfo.qqAppKey = "4578e54fb3a1bd18e0681bc1c734514e";
		baseInfo.wxAppId = "wxcde873f99466f74a";
		baseInfo.wxAppKey = "bc0994f30c0a12a9908e353cf05d4dea";
		// 订阅型测试用offerId
		baseInfo.offerId = "100703379";

		// 自2.7.1a开始游戏可在初始化msdk时动态设置版本号，灯塔和bugly的版本号由msdk统一设置
		// 2.7.1a之前的版本不要设置
		// 1、版本号组成 = versionName + versionCode
		// 2、游戏如果不赋值给appVersionName（或者填为""）和appVersionCode(或者填为-1)，
		// msdk默认读取AndroidManifest.xml中android:versionCode="51"及android:versionName="2.7.1"
		// 3、游戏如果在此传入了appVersionName（非空）和appVersionCode（正整数）如下，则灯塔和bugly上获取的版本号为2.7.1.271
		// baseInfo.appVersionName = "2.7.1";
		// baseInfo.appVersionCode = 271;
		WGPlatform.Initialized((Activity) context, baseInfo);
		// 设置拉起QQ时候需要用户授权的项
		WGPlatform.WGSetPermission(WGQZonePermissions.eOPEN_ALL);

		// 必须保证handleCallback在Initialized之后
		// launchActivity的onCreat()和onNewIntent()中必须调用
		// WGPlatform.handleCallback()。否则会造成微信登录无回调
		
		//读取本地票据，如果成功直接登录
		if(getPlatform() == EPlatform.ePlatform_QQ||getPlatform() == EPlatform.ePlatform_Weixin){
			WGPlatform.WGLoginWithLocalInfo();
			token = getOpenId();
			
			try {
				dataJsonObjInit.put("status", "logined");
				dataJsonObjInit.put("user_id", "");
				dataJsonObjInit.put("token", getOpenId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonResultInit = dataJsonObjInit.toString();
			Log.i("HuangKe----->", jsonResultInit);
		}else{
			try {
				dataJsonObjInit.put("status", "unlogined");
				dataJsonObjInit.put("user_id", "");
				dataJsonObjInit.put("token", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			 jsonResultInit = dataJsonObjInit.toString();
			 Log.i("HuangKe----->", jsonResultInit);
		}
		WGPlatform.handleCallback(((Activity) context).getIntent());

		WGPlatform.WGSetObserver(new WGPlatformObserver() {

			@Override
			public void OnWakeupNotify(WakeupRet arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void OnRelationNotify(RelationRet arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void OnLoginNotify(LoginRet ret) {
				Logger.d("called");
				Logger.d("ret.flag" + ret.flag);
				switch (ret.flag) {
				case CallbackFlag.eFlag_Succ:
					// 登陆成功, 读取各种票据
					String openId = ret.open_id;
					//为token赋值
					token = openId;
					Log.i("HuangKe------>", openId);
					String pf = ret.pf;
					String pfKey = ret.pf_key;
					platform = ret.platform;
					String wxAccessToken = "";
					long wxAccessTokenExpire = 0;
					String wxRefreshToken = "";
					long wxRefreshTokenExpire = 0;
					for (TokenRet tr : ret.token) {
						switch (tr.type) {
						case TokenType.eToken_WX_Access:
							wxAccessToken = tr.value;
							wxAccessTokenExpire = tr.expiration;
							break;
						case TokenType.eToken_WX_Refresh:
							wxRefreshToken = tr.value;
							wxRefreshTokenExpire = tr.expiration;
							break;
						default:
							break;
						}
					}
					// 调进入游戏方法
					// mainActivity.letUserLogin();
					break;

				// 游戏逻辑，对登陆失败情况分别进行处理
				case CallbackFlag.eFlag_WX_UserCancel:
				case CallbackFlag.eFlag_WX_NotInstall:
				case CallbackFlag.eFlag_WX_NotSupportApi:
				case CallbackFlag.eFlag_WX_LoginFail:
				case CallbackFlag.eFlag_Local_Invalid:
					Logger.d(ret.desc);
				default:
					// 显示登陆界面
					// mainActivity.letUserLogout();
					break;
				}
			}
		});
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void login() {
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		Log.i("HuangKe----->", "执行注销");
		WGPlatform.WGLogout();
	}

	@Override
	public void pay(String jsonData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void switchAccount(int luaFunc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		WGPlatform.onDestory((Activity) context);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		WGPlatform.onResume();
		if (pauseTime != 0
				&& System.currentTimeMillis() / 1000 - pauseTime > 1800) {
			Logger.d("MsdkStart", "start auto login");
			// 模拟游戏自动登录，这里需要游戏添加加载动画
			// WGLoginWithLocalInfo是一个异步接口, 会到后台验证上次登录的票据是否有效
			WGPlatform.WGLoginWithLocalInfo();
			// 模拟游戏自动登录 END
		} else {
			Logger.d("MsdkStart", "do not start auto login");
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		WGPlatform.onStop();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		WGPlatform.onPause();
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		WGPlatform.onRestart();
	}

	@Override
	public void onNewIntent(Context context) {
		// TODO Auto-generated method stub
		WGPlatform.handleCallback(((Activity) context).getIntent());
	}

	@Override
	public void loginPlatform(String platform) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// 腾讯分平台登录，其值可为ePlatform_QQ, ePlatform_Weixin
		if (platform == "QQ" && getPlatform() != EPlatform.ePlatform_None) {
			// 如已登录直接进入游戏
			// startModule();
			Log.i("HuangKe----->", "已经登录!");
			Log.i("HuangKe----->", "当前登录平台:" + getPlatform());
		} else if (getPlatform() == EPlatform.ePlatform_None) {
			WGPlatform.WGLogin(EPlatform.ePlatform_QQ);
		} else {

		}

		if (platform == "WEIXIN" && getPlatform() != EPlatform.ePlatform_None) {
			// 如已登录直接进入游戏
			// startModule();
			Log.i("HuangKe----->", "已经登录!");
			Log.i("HuangKe----->", "当前登录平台:" + getPlatform());
		} else if (getPlatform() == EPlatform.ePlatform_None) {
			WGPlatform.WGLogin(EPlatform.ePlatform_Weixin);
		} else {

		}

	}

	// 获取本地票据中的当前登录平台
	public EPlatform getPlatform() {
		LoginRet ret = new LoginRet();
		WGPlatform.WGGetLoginRecord(ret);
		if (ret.flag == CallbackFlag.eFlag_Succ) {
			Log.i("HuangKe----->", "当前平台是：" + EPlatform.getEnum(ret.platform));
			return EPlatform.getEnum(ret.platform);
		}
		return EPlatform.ePlatform_None;

	}
	
	//获取本地票据中的openId
	public String getOpenId(){
		LoginRet ret = new LoginRet();
		String openId = null ;
		WGPlatform.WGGetLoginRecord(ret);
		if(ret.flag == CallbackFlag.eFlag_Succ){
			openId = ret.open_id;
		}
		return openId;
	}

	@Override
	public String getInitResult() {
		// TODO Auto-generated method stub
		return jsonResultInit;
	}
}
