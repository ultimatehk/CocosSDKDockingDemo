package com.yychina.channel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * sdk 简单工厂类
 *
 * @author vic
 *
 */
public class SdkFactory{
	/**
	 * 获取sdk实例
	 *
	 * @return
	 */
	public static SdkBaseFactory getSdkInstance() {
		SdkBaseFactory baseSdkFactory = null;
		try {
			String className = SdkFactory.class.getPackage().getName() + ".allsdk." + C.Channel.getChannelSdkClassName();
			baseSdkFactory = (SdkBaseFactory) Class.forName(className).newInstance();// 利用反射得到sdk实例
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseSdkFactory;
	}

	/**
	 * 统一初始化
	 */
	public static void init() {
		((Activity) C.ActivityCurr.context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
		getSdkInstance().init();
			}
		});
	}

	/**
	 * 统一登录
	 * @param luaFunc
	 * @throws Exception 
	 */
	public static void login() {
		((Activity) C.ActivityCurr.context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSdkInstance().login();
			}
		});
	}
	
	/**
	 * 分平台登录
	 * @param jsonData
	 */
	public static void loginPlatform(final String platform){
		((Activity) C.ActivityCurr.context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSdkInstance().loginPlatform(platform);
			}
		});
	}
	
	public static void logout(){
		((Activity) C.ActivityCurr.context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSdkInstance().logout();
			}
		});
	}

	/**
	 * 统一支付
	 * @param luaFunc
	 * @throws Exception 
	 */
	public static void pay(final String jsonData) throws Exception {
		((Activity) C.ActivityCurr.context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSdkInstance().pay(jsonData);
			}
		});
	}

	/**
	 * 统一切换账号
	 * @param luaFunc
	 */
	public static void switchAccount(final int luaFunc){
		((Activity) C.ActivityCurr.context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSdkInstance().switchAccount(luaFunc);
			}
		});
	}

	/**
	 * 销毁
	 */
	public static void distory(){
		getSdkInstance().destroy();
	}

	/**
	 * 初始化main
	 * @param activity
	 */
	public static void mainInit(Context c){
		C.ActivityCurr.setContext(c);
		getSdkInstance().mainInit(c);
	}

	public static void resume(){
		getSdkInstance().resume();
	}

	public static void stop(){
		getSdkInstance().stop();
	}

	public static void pause(){
		getSdkInstance().pause();
	}
	
	public static void reStart(){
		getSdkInstance().restart();
	}
	
	public static void onNewIntent(Context context){
		getSdkInstance().onNewIntent(context);
	}
}
