package com.fxgizmob;

import java.util.Timer;
import java.util.TimerTask;

import com.fxgizmob.R.string;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FxGizmoRegisterActivity extends Activity {
	
	TextView regEmail;
	TextView regPass;
	TextView regConfirmPass;
	Button btn_register;
	Button btn_cancel;
	
	String strRegEmail;
	String strRegPass;
	String strRegConfirm;
	
	
	
	public static ProgressDialog progressBar;
	Timer progressTimer ;      
	TimerTask progressTask ;
	
	
	public void dismissProgressDialog() {
		if (progressBar != null) {
			progressBar.dismiss();
			progressBar = null;			
		}
	}
	
	public void showProgressDialog() {
		
		progressBar = new ProgressDialog(this) ;
		progressBar.setMessage(getResources().getString(R.string.progress_wait)) ;
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER) ;
		progressBar.setCancelable(false) ;
		progressBar.show();
		
		if( progressTimer != null )			
			initProgressTimerTask() ;
		
		progressTimer = new Timer() ;
		progressTask = new TimerTask() {
		    @Override
		    public void run() {
		    	if ( progressBar != null && progressBar.isShowing() )
		    	{
		    		FxGizmoRegisterActivity.this.runOnUiThread(new Runnable() {
		    			public void run() {
		    				Toast.makeText(FxGizmoRegisterActivity.this, getResources().getString(R.string.internet_speed_low), Toast.LENGTH_LONG).show() ;
			    		}
			    	});
		    	}
		    	
		    	dismissProgressDialog() ;
		    	initProgressTimerTask() ;	    	
		    }
		} ;
		
		progressTimer.schedule(progressTask, 15000) ;
		
	}
	

	
	private void initProgressTimerTask() {
		
		if ( progressTask != null )
    		progressTask.cancel() ;
    	
    	if ( progressTimer != null )
    		progressTimer.cancel() ;
    	
    	progressTask = null ;
    	progressTimer = null ;
		
	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.fxgizmo_register_layout);
		
		regEmail 	= (TextView) findViewById(R.id.register_email);
		regPass 	= (TextView) findViewById(R.id.register_password);
		regConfirmPass = (TextView) findViewById(R.id.register_confirmpassword);
		
		
		btn_register 	= (Button) findViewById(R.id.btn_register);
		btn_cancel 		= (Button) findViewById(R.id.btn_cancel);
		
		btn_register.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strRegEmail = regEmail.getText().toString();
				strRegPass 	= regPass.getText().toString();
				strRegConfirm = regConfirmPass.getText().toString();
				
				if (!strRegPass.equals(strRegConfirm)){
					Toast.makeText(FxGizmoRegisterActivity.this, getResources().getString(R.string.password_nomatch), Toast.LENGTH_LONG).show();
				} else {
					if (strRegPass.length() < 8){
						Toast.makeText(FxGizmoRegisterActivity.this, getResources().getString(R.string.password_cannot_be_empty), Toast.LENGTH_LONG).show();
					} else {
						showProgressDialog();
						ParseUser user = new ParseUser();
						user.setUsername(strRegEmail);
						user.setEmail(strRegEmail);
						user.setPassword(strRegPass);
						
						user.put("post_nums", 1) ;
						
						user.signUpInBackground(new SignUpCallback() {
							@Override
							public void done(ParseException e) {
								// TODO Auto-generated method stub
								dismissProgressDialog();
								if (e == null){
									Toast.makeText(FxGizmoRegisterActivity.this, getResources().getString(R.string.success_register), Toast.LENGTH_LONG).show();
									moveToLoginActivity();
								} else {
									e.printStackTrace();
									Toast.makeText(FxGizmoRegisterActivity.this, getResources().getString(R.string.failed_register) + e.getMessage(), Toast.LENGTH_LONG).show();
								}							
							}
						});	
					}
				}
					
			}
		});
		
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	private void moveToLoginActivity() {		
        finish() ; 
	}
	@Override
	public void onBackPressed(){
	}

}
