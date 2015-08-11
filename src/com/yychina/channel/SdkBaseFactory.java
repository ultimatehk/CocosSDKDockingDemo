package com.yychina.channel;

import android.content.Context;
import android.content.Intent;

public interface SdkBaseFactory {
	void mainInit(Context c);
	
	void init();

	void login();

	void logout();

	void pay(String jsonData);

	void switchAccount(int luaFunc);

	public void destroy();

	void resume();

	void stop();

	void pause();
	
	void restart();
	
	void onNewIntent(Context context);

	void loginPlatform(String platform);
	
	String getJsonResult(String type);
}