package com.fxgizmob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.util.GifView;

public class FXGizmoSplashActivity extends Activity{
	
	GifView gifView;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.fxgizmo_splashscreen);
				
		gifView = (GifView) findViewById(R.id.gif_view);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(FXGizmoSplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();	
			}
		}, 7000);
	}
}
