package com.yychina.channel;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

public class SdkPayInfo implements Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = -7508280930197401474L;
	
	public String order_id; //订单号
	public String token;  //token
	public String userId; // 渠道平台用户id
	public String money;  //单价
	public String rmb;    //配好商品编码的总价（方式二）
	public String rate;   //兑换比率
	public String productName;  //商品名称
	public String count;//商品数量
	public String productId; //商品编码（方式二）
	public String notify_uri; // 回调Url 有的平台是传这个告知服务器的
	public String roleName;// 角色名称 
	public String roleId;//角色ID
	public String roleLevel;//角色等级
	public String roleVIPLevel;//角色VIP等级
	public String userBalance;//用户余额
	public String rolePartyName;//角色公会名
	public String channelId; // 渠道平台id
	public String serverId; // 区服id
	public String serverName;//区服名称
	public String app_ext;  // 保留字段 如果客户端传值，支付成功之后 会原样返回

	public SdkPayInfo converInfo(String data){
		String tempItem = null;
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			JSONObject jsonObject = new JSONObject(data);
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				try {
					tempItem = fields[i].getName();
					if(tempItem.equals("serialVersionUID") || jsonObject.isNull(tempItem) || jsonObject.get(tempItem) == JSONObject.NULL) continue;
					field.set(this, jsonObject.get(tempItem).toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
}
