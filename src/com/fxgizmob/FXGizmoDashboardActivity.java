package com.fxgizmob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class FXGizmoDashboardActivity extends Activity implements OnClickListener{

	ImageButton btn_dashboard;
	ImageButton btn_setting;
	ImageButton btn_share;
	private final int REQUEST_SETTING = 1;
	public final int RESULT_CODE = 11;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.fxgizmo_dashboard_layout);

		btn_dashboard = (ImageButton) findViewById(R.id.btn_dashboard);
		btn_setting = (ImageButton) findViewById(R.id.btn_setting);
		btn_share = (ImageButton) findViewById(R.id.btn_share);
		
		btn_dashboard.setOnClickListener(this);
		btn_setting.setOnClickListener(this);
		btn_share.setOnClickListener(this);
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_dashboard:
			Intent intent = new Intent(FXGizmoDashboardActivity.this, FxGizmoLandingActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_setting:
			Toast.makeText(this, "Open Setting", Toast.LENGTH_SHORT).show();
			Intent i_setting = new Intent(FXGizmoDashboardActivity.this, FxGizmoSettingActivity.class);
			startActivityForResult(i_setting, REQUEST_SETTING);
			break;
		case R.id.btn_share:
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			startActivity(sharingIntent);
			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_SETTING) {
			
		}
	}
}
