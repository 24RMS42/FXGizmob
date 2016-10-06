package com.fxgizmob;

import com.util.PrefManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class FxGizmoSettingActivity extends Activity implements OnClickListener{
	
	ImageButton btn_logout_setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.fxgizmo_setting_layout);
		
		btn_logout_setting = (ImageButton) findViewById(R.id.btn_logout_setting);
		btn_logout_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_logout_setting:
			PrefManager.savePrefString(getApplicationContext(), "username", "");
			PrefManager.savePrefString(getApplicationContext(), "password", "");
			Intent i_login = new Intent(FxGizmoSettingActivity.this, LoginActivity.class);
			startActivity(i_login);
			finish();
			break;

		default:
			break;
		}
	}
}
