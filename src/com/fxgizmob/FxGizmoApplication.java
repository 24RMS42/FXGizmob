package com.fxgizmob;

import com.parse.Parse;
import com.parse.ParseInstallation;
//import com.parse.PushService;

import android.app.Application;
//import android.os.Bundle;

public class FxGizmoApplication extends Application {
	
//	@Override
	public void onCreate() {
		  Parse.initialize(this, getResources().getString(R.string.parse_appid), getResources().getString(R.string.parse_clientkey));
		  
		  ParseInstallation.getCurrentInstallation().saveInBackground();
//		  PushService.setDefaultPushCallback(this, FXGizmobSplashActivity.class);
	}
}
