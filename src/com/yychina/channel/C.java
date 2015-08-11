package com.yychina.channel;


import android.content.Context;

/**
 *
 * @author vic
 *
 */
public class C {

	/**
	 * 当前activity 设置 为了外部包调用实例
	 */
	public static final class ActivityCurr{
		public static Context context; // 设置主游戏Activity实例
//		public static Context context() throws Exception {
//			if(mContext == null ){
//				throw new Exception("current Context not null");
//			}
//			return mContext;
//		}

		public static void setContext(Context c) {
			context = c;
		}
	}
	
	/**
	 * 显示屏信息
	 *
	 * @author chen
	 * @date 2012-10-25 下午3:15:23
	 */
	public static final class Channel {

		public static String getChannel() {
			return channelInfo()[0];
		}

		public static String getChannelSdkClassName() {
			return channelInfo()[1];
		}

		public static final String[] c_baidu = { "110000", "Sdk_Baidu" };
		public static final String[] c_qh360 = { "000023", "Sdk_QiHu360" };
		public static final String[] c_mi = { "000111", "Sdk_Mi"};
		public static final String[] c_msdk = {"000112","Sdk_TencentMSdk"};

		private static String[] channelInfo() {
			return c_msdk;
		}
	}
	
	public static final class UnityMethod {
		/** 总回调控制类 */
		public static final String controller = "SDKController";
		/** test */
		public static final String message = "messgae";

		/** 无参 */public static final String init = "InitCallBack";
		public static final String login = "LoginCallBack";
		public static final String logout = "LogoutCallBack";
		public static final String pay = "PayCallBack";
		public static final String switchUser = "SwitchCallBack";
	}
}
