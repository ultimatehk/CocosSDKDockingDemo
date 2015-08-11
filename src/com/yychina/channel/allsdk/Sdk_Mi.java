//package com.yychina.channel.allsdk;
//
//import java.util.UUID;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.unity3d.player.UnityPlayer;
//import com.xiaomi.gamecenter.sdk.GameInfoField;
//import com.xiaomi.gamecenter.sdk.MiCommplatform;
//import com.xiaomi.gamecenter.sdk.MiErrorCode;
//import com.xiaomi.gamecenter.sdk.OnLoginProcessListener;
//import com.xiaomi.gamecenter.sdk.OnPayProcessListener;
//import com.xiaomi.gamecenter.sdk.entry.MiAccountInfo;
//import com.xiaomi.gamecenter.sdk.entry.MiAppInfo;
//import com.xiaomi.gamecenter.sdk.entry.MiBuyInfo;
//import com.yychina.channel.C;
//import com.yychina.channel.SdkBaseFactory;
//import com.yychina.channel.SdkPayInfo;
//
//public class Sdk_Mi implements SdkBaseFactory {
//	private static String tag;
//	private static Context context;
//	private MiAccountInfo accountInfo;
//	private String token;
//	private long userId;
//
//	private MiAppInfo appInfo;
//
//	@Override
//	public void mainInit(Context c) {
//		tag = Sdk_Mi.class.getName();
//		context = c;
//		if (context == null) {
//			Log.e("HuangKe----->", "传入的context为空");
//		}
//		// TODO Auto-generated method stub
//		/** SDK初始化 */
//		appInfo = new MiAppInfo();
//		appInfo.setAppId("2882303761517368331");
//		appInfo.setAppKey("5371736810331");
//		MiCommplatform.Init((Activity) context, appInfo);
//	}
//
//	@Override
//	public void init() {
//		UnityPlayer.UnitySendMessage(C.UnityMethod.controller,C.UnityMethod.init,"");
//	}
//
//	@Override
//	public void login() {
//		MiCommplatform.getInstance().miLogin((Activity) context,
//				new OnLoginProcessListener() {
//					@Override
//					public void finishLoginProcess(int arg0, MiAccountInfo arg1) {
//						JSONObject dataJsonObj = new JSONObject();
//						if (MiErrorCode.MI_XIAOMI_PAYMENT_SUCCESS == arg0) {
//							accountInfo = arg1;
//							userId = arg1.getUid();
//							token = arg1.getSessionId();
//							Log.i("HuangKe----->", "登录成功，user_id是：  " + userId
//									+ "");
//							Log.i("HuangKe----->", "登录成功，token(session_id)是：  "
//									+ token);
//							
//							//向服务器上传token
//							
//
//							try {
//								dataJsonObj.put("status", "success");
//								dataJsonObj.put("user_id", userId);
//								dataJsonObj.put("token", token);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//
//							handler.sendEmptyMessage(30000);
//						}
//						if (MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_LOGIN_FAIL == arg0) {
//							Log.i("HuangKe----->", "登录失败。。。");
//							try {
//								dataJsonObj.put("status", "fail");
//								dataJsonObj.put("user_id", "");
//								dataJsonObj.put("token", "");
//							} catch (Exception e) {
//								// TODO: handle exception
//								e.printStackTrace();
//							}
//							handler.sendEmptyMessage(40000);
//						}
//						if (MiErrorCode.MI_XIAOMI_GAMECENTER_ERROR_CANCEL == arg0) {
//							Log.i("HuangKe----->", "取消登录。。。");
//							try{
//								dataJsonObj.put("status", "cancel");
//								dataJsonObj.put("user_id", "");
//								dataJsonObj.put("token", "");
//								}catch (Exception e) {
//									// TODO: handle exception
//									e.printStackTrace();
//								}
//						
//							handler.sendEmptyMessage(70000);
//						}
//						String jsonResult = dataJsonObj.toString();
//						UnityPlayer.UnitySendMessage(C.UnityMethod.controller,C.UnityMethod.login,jsonResult);
//					}
//				});
//
//		
//	}
//
//	private Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 10000:
//				// Intent i2 = new Intent( MainActivity.this,
//				// MiappPayActivity.class );
//				// i2.putExtra( "from", "repeatpay" );
//				// i2.putExtra( "screen", demoScreenOrientation );
//				// startActivity( i2 );
//				break;
//			case 20000:
//				// Intent i1 = new Intent( MainActivity.this,
//				// MiappPayActivity.class );
//				// i1.putExtra( "from", "unrepeatpay" );
//				// i1.putExtra( "screen", demoScreenOrientation );
//				// startActivity( i1 );
//				break;
//			case 30000:
//				Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
//				break;
//			case 40000:
//				Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
//				break;
//			case 70000:
//				Toast.makeText(context, "取消登录", Toast.LENGTH_SHORT)
//						.show();
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	@Override
//	public void logout() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void pay(String jsonData) {
//		final JSONObject dataJsonObj = new JSONObject();
//		try {
//			JSONObject json = new JSONObject(jsonData);
//			json.put("order_id", UUID.randomUUID().toString());
//			json.put("token", token);
//			json.put("money", "5");
//			json.put("rmb", "0.01");
//			json.put("rate", "0.01");
//			json.put("productName", "充值1");
//			json.put("count", "3");
//			json.put("productId", "cc1");
//			json.put("notify_uri", "http://www.ddd.com");
//			json.put("roleName", "小诗子");
//			json.put("roleId", "1212382");
//			json.put("roleLevel", "12");
//			json.put("roleVIPLevel", "1");
//			json.put("rolePartyName", "说的");
//			json.put("channelId", "xiaomi");
//			json.put("serverId", "sdssdss237283sdj");
//			json.put("serverName", "战斗服");
//			json.put("app_ext", "dsds");
//			
//			final SdkPayInfo sdkPayinfo = new SdkPayInfo().converInfo(json.toString());
//			
//			
//			MiBuyInfo miBuyInfo= new MiBuyInfo();
//			miBuyInfo.setCpOrderId(UUID.randomUUID().toString());//订单号唯一（不为空）
//			miBuyInfo.setCpUserInfo("cpUserInfo"); //此参数在用户支付成功后会透传给CP的服务器
//			final int amount = Integer.parseInt(sdkPayinfo.count) * Integer.parseInt(sdkPayinfo.money);
//			miBuyInfo.setAmount( amount ); //必须是大于1的整数，10代表10米币，即10元人民币（不为空）
//			//用户信息，网游必须设置、单机游戏或应用可选**
//			
//			Bundle mBundle = new Bundle();
//			mBundle.putString( GameInfoField.GAME_USER_BALANCE, sdkPayinfo.userBalance );   //用户余额
//			mBundle.putString( GameInfoField.GAME_USER_GAMER_VIP, sdkPayinfo.roleVIPLevel );  //vip等级
//			mBundle.putString( GameInfoField.GAME_USER_LV, sdkPayinfo.roleLevel );  //角色等级
//			mBundle.putString( GameInfoField.GAME_USER_PARTY_NAME, sdkPayinfo.rolePartyName);  //工会，帮派
//			mBundle.putString( GameInfoField.GAME_USER_ROLE_NAME, sdkPayinfo.roleName ); //角色名称
//			mBundle.putString( GameInfoField.GAME_USER_ROLEID, sdkPayinfo.roleId);    //角色id
//			mBundle.putString( GameInfoField.GAME_USER_SERVER_NAME, sdkPayinfo.serverName );  //所在服务器
//			miBuyInfo.setExtraInfo( mBundle ); //设置用户信息
//			MiCommplatform.getInstance().miUniPay((Activity)context, miBuyInfo, 
//			new OnPayProcessListener()
//			{
//			@Override
//			    public void finishPayProcess( int code ) {
//			        switch( code ) {
//			        case MiErrorCode.MI_XIAOMI_PAYMENT_SUCCESS:
//			       //购买成功
//			        	Log.i("HuangKe----->", "支付成功");
//			        	Log.i("HuangKe----->", "金额是:"+amount);
//			        	try {
//							dataJsonObj.put("status", "success");
//							dataJsonObj.put("user_id", userId);
//							dataJsonObj.put("productName", sdkPayinfo.productName);
//							dataJsonObj.put("totalmoney", amount);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//			            break;
//			        case MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_PAY_CANCEL:
//			            //取消购买
//			        	Log.i("HuangKe----->", "取消购买");
//			        	try {
//							dataJsonObj.put("status", "cancel");
//							dataJsonObj.put("user_id", userId);
//							dataJsonObj.put("productName", "");
//							dataJsonObj.put("totalmoney", "");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//			               break;
//			        case MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_PAY_FAILURE:
//			            //购买失败
//			        	Log.i("HuangKe----->", "购买失败");
//			        	try {
//							dataJsonObj.put("status", "failed");
//							dataJsonObj.put("user_id", userId);
//							dataJsonObj.put("productName", "");
//							dataJsonObj.put("totalmoney", "");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//			               break;       
//			case  MiErrorCode.MI_XIAOMI_PAYMENT_ERROR_ACTION_EXECUTED:  
//			   //操作正在进行中
//			   break;       
//			        default:
//			              //购买失败
//			            break;
//			        }
//			    }
//			});
//			
//			
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		String jsonResult = dataJsonObj.toString();
//		UnityPlayer.UnitySendMessage(C.UnityMethod.controller,C.UnityMethod.login,jsonResult);
//	}
//
//	@Override
//	public void switchAccount(int luaFunc) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void resume() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void pause() {
//		// TODO Auto-generated method stub
//
//	}
//
//}