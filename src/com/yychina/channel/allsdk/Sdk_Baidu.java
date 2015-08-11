//package com.yychina.channel.allsdk;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.location.GpsStatus.Listener;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.yychina.channel.SdkBaseFactory;
//import com.yychina.channel.SdkPayInfo;
//import com.baidu.gamesdk.ActivityAdPage;
//import com.baidu.gamesdk.ActivityAdPage.Listener;
//import com.baidu.gamesdk.ActivityAnalytics;
//import com.baidu.gamesdk.BDGameSDK;
//import com.baidu.gamesdk.BDGameSDKSetting;
//import com.baidu.gamesdk.BDGameSDKSetting.Domain;
//import com.baidu.gamesdk.IResponse;
//import com.baidu.gamesdk.ResultCode;
//import com.baidu.platformsdk.PayOrderInfo;
//
//public class Sdk_Baidu implements SdkBaseFactory {
//	private static String tag;
//	private static Context context;
//
//	private static ActivityAdPage mActivityAdPage;
//	private static ActivityAnalytics mActivityAnalytics;
//

//	private static String token = "";
//	@Override
//	public void setContext(Context setContext) {
//		context = setContext;
//		tag = this.getClass().getSimpleName();
//	}
//
//	@Override
//	public void init() {
//		BDGameSDKSetting mBDGameSDKSetting = new BDGameSDKSetting();
//		mBDGameSDKSetting.setAppID(4358272);//APPID设置
//		mBDGameSDKSetting.setAppKey("qEDCXjbCGrWukHcsNqlQqQ0P");//APPKEY设置
//		mBDGameSDKSetting.setDomain(Domain.RELEASE);//设置为正式模式
//		mBDGameSDKSetting.setOrientation(Utils.getOrientation(context));
//		BDGameSDK.init((Activity) context, mBDGameSDKSetting, new IResponse<Void>(){
//			@Override
//			public void onResponse(int resultCode, String resultDesc,
//					Void extraData) {
//				switch(resultCode){
//				case ResultCode.INIT_SUCCESS:
//					//初始化成功
//					Intent intent = new Intent(context, MainActivity.class);
//					context.startActivity(intent);
//					((Activity) context).finish();
//					break;
//
//				case ResultCode.INIT_FAIL:
//				default:
//					Toast.makeText(context, "启动失败", Toast.LENGTH_LONG).show();
//					((Activity) context).finish();
//					//初始化失败
//				}
//			}
//		});
//	}
//
//	@Override
//	public void login(final int luaFunc) {
//		if(!BDGameSDK.isLogined()){
//			BDGameSDK.login(new IResponse<Void>() {
//				@Override
//				public void onResponse(int resultCode, String resultDesc, Void extraData) {
//					loginResult(luaFunc, resultCode);
//				}
//			});
//		}else{
//			JSONObject dataJsonObj = new JSONObject();
//			try {
//				dataJsonObj.put("msg", "ok");
//				dataJsonObj.put("access_token", BDGameSDK.getLoginAccessToken());
//				dataJsonObj.put("userId", BDGameSDK.getLoginUid());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			Log.i(tag, dataJsonObj.toString());
//			if(luaFunc >0){
//				Cocos2dxLuaJavaBridge.callLuaFunctionWithString(luaFunc, dataJsonObj.toString());
//				Cocos2dxLuaJavaBridge.releaseLuaFunction(luaFunc);
//			}else{
////				SdkFactory.reLogin();
//				Cocos2dxLuaJavaBridge.callLuaGlobalFunctionWithString("SdkReLogin", dataJsonObj.toString());
//			}
//		}
//	}
//
//	private void loginResult(final int luaFunc, int resultCode) {
//		try {
//			String hint = "";
//			JSONObject dataJsonObj = new JSONObject();
//			switch(resultCode){
//			case ResultCode.LOGIN_SUCCESS:
//				hint = "登录成功";
//				dataJsonObj.put("msg", "ok");
//				dataJsonObj.put("access_token", BDGameSDK.getLoginAccessToken());
//				dataJsonObj.put("userId", BDGameSDK.getLoginUid());
//				break;
//			case ResultCode.LOGIN_CANCEL:
//				dataJsonObj.put("msg", "cancel");
//				hint = "取消登录";
//				break;
//			case ResultCode.LOGIN_FAIL:
//			default:
//				dataJsonObj.put("msg", "fail");
//				hint = "登录失败";
//			}
//			Log.i(tag, dataJsonObj.toString());
//			if(luaFunc >0){
//				Cocos2dxLuaJavaBridge.callLuaFunctionWithString(luaFunc, dataJsonObj.toString());
//				Cocos2dxLuaJavaBridge.releaseLuaFunction(luaFunc);
//			}else{
////				SdkFactory.reLogin();
//				Cocos2dxLuaJavaBridge.callLuaGlobalFunctionWithString("SdkReLogin", dataJsonObj.toString());
//			}
//			Toast.makeText(MainActivity.context, hint, Toast.LENGTH_LONG).show();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void pay(final int luaFunc, String jsonData) {
//		SdkPayInfo info = new SdkPayInfo().converInfo(jsonData);
//		PayOrderInfo payOrderInfo = buildOrderInfo(info);
//		if (payOrderInfo == null) {
//			return;
//		}
//		BDGameSDK.pay(payOrderInfo, info.notify_uri, new IResponse<PayOrderInfo>() {
//			@Override
//			public void onResponse(int resultCode, String resultDesc,
//					PayOrderInfo extraData) {
//				String resultStr = "";
//				String resultLua = "11111111";
//				switch (resultCode) {
//				case ResultCode.PAY_SUCCESS:// 支付成功
//					resultStr = "支付成功:" + resultDesc;
//					resultLua = "0";
//					break;
//				case ResultCode.PAY_CANCEL:// 订单支付取消
//					resultStr = "取消支付";
//					resultLua = "1";
//					break;
//				case ResultCode.PAY_FAIL:// 订单支付失败
//					resultStr = "支付失败：" + resultDesc;
//					resultLua = "2";
//					break;
//				case ResultCode.PAY_SUBMIT_ORDER:// 订单已经提交，支付结果未知（比如：已经请求了，但是查询超时）
//					resultStr = "订单已经提交，支付结果未知";
//					resultLua = "3";
//					break;
//				}
//
//				Cocos2dxLuaJavaBridge.callLuaFunctionWithString(luaFunc, resultLua);
//				Cocos2dxLuaJavaBridge.releaseLuaFunction(luaFunc);
//
//				Toast.makeText(MainActivity.context, resultStr, Toast.LENGTH_LONG).show();
//			}
//		});
//	}
//
//	private PayOrderInfo buildOrderInfo(SdkPayInfo info){
//		String totalAmount = info.money;//支付总金额 （以分为单位）
//		int ratio = 1;//该参数为非定额支付时生效 (支付金额为0时为非定额支付,具体参见使用手册)
//		JSONObject dataJsonObj = new JSONObject();
//        try {
//			dataJsonObj.put("productId", info.productId);
//			dataJsonObj.put("majorId", info.majorId);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//        String extInfo = dataJsonObj.toString();//扩展字段，该信息在支付成功后原样返回给CP // ExtInfo   productId, majorId
//
//		if(TextUtils.isEmpty(totalAmount)){
//			totalAmount = "0";
//		}
//
//		PayOrderInfo payOrderInfo = new PayOrderInfo();
//		payOrderInfo.setCooperatorOrderSerial(info.order_id);
//		payOrderInfo.setProductName(info.productName);
//		long p = Long.parseLong(totalAmount);
//		payOrderInfo.setTotalPriceCent(p);//以分为单位
//		payOrderInfo.setRatio(ratio);
//		payOrderInfo.setExtInfo(extInfo);//该字段将会在支付成功后原样返回给CP(不超过500个字符)
//
//		return payOrderInfo;
//	}
//
//	@Override
//	public void switchAccount(final int luaFunc) {
//		Log.i(tag, "-----------SwitchAccount-----------");
////		Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
////		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////		context.startActivity(i);
//		BDGameSDK.logout();
//	}
//
//	@Override
//	public void destroy() {
//		context = null;
//		BDGameSDK.destroy();
//	}
//
//	@Override
//	public void mainInit(final Activity activity) {
//		mActivityAnalytics = new ActivityAnalytics(activity);
//
//		mActivityAdPage = new ActivityAdPage(activity, new Listener(){
//			@Override
//			public void onClose() {
//				// TODO 关闭暂停页, CP可以让玩家继续游戏
//				Toast.makeText(activity, "继续游戏", Toast.LENGTH_LONG).show();
//				Log.i(tag, "-----------ActivityAdPage-----------");
//
//			}
//		});
//
//		Log.i(tag, "-----------SwitchAccount-----------");
//		BDGameSDK.setSuspendWindowChangeAccountListener(new IResponse<Void>(){
//			@Override
//			public void onResponse(int resultCode, String resultDesc,
//					Void extraData) {
//				Log.i(tag, "-----------resultCode-----------"+resultCode);
//				loginResult(-1, resultCode);
//			}
//		});
//
//		BDGameSDK.setSessionInvalidListener(new IResponse<Void>(){
//
//			@Override
//			public void onResponse(int resultCode, String resultDesc,
//					Void extraData) {
//				if(resultCode == ResultCode.SESSION_INVALID){
////					Toast.makeText(getApplicationContext(), "会话失效，请重新登录", Toast.LENGTH_LONG).show();
//					//TODO 此处CP可调用登录接口
//					Cocos2dxLuaJavaBridge.callLuaGlobalFunctionWithString("SdkReLogin", "");
//				}
//			}
//		});
//	}
//
//	@Override
//	public void onResume() {
//		if(mActivityAnalytics != null)
//			mActivityAnalytics.onResume();
//		if(mActivityAdPage != null)
//			mActivityAdPage.onResume();
//	}
//
//	@Override
//	public void onStop() {
//		if(mActivityAdPage != null)
//			mActivityAdPage.onStop();
//	}
//
//	@Override
//	public void onPause() {
//		if(mActivityAnalytics != null)
//			mActivityAnalytics.onPause();
//	}
//
//	@Override
//	public void logout() {
//		BDGameSDK.logout();
//	}
//}