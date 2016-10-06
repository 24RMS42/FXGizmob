package com.fxgizmob;

import java.util.Timer;
import java.util.TimerTask;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.util.PrefManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

public class LoginActivity extends Activity implements OnClickListener {

	private Button btnLogin;
	private Button btnSignup;

	private EditText txtEmail;
	private EditText txtPass;

	private String preUsername;
	private String prePassword;

	private VideoView video_background;

	public static ProgressDialog progressBar;
	Timer progressTimer;
	TimerTask progressTask;

	public void dismissProgressDialog() {
		if (progressBar != null) {
			progressBar.dismiss();
			progressBar = null;
		}
	}

	public void showProgressDialog() {

		progressBar = new ProgressDialog(this);
		progressBar
				.setMessage(getResources().getString(R.string.progress_wait));
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.setCancelable(false);
		progressBar.show();

		if (progressTimer != null)
			initProgressTimerTask();

		progressTimer = new Timer();
		progressTask = new TimerTask() {
			@Override
			public void run() {
				if (progressBar != null && progressBar.isShowing()) {
					LoginActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(
									LoginActivity.this,
									getResources().getString(
											R.string.internet_speed_low),
									Toast.LENGTH_LONG).show();
						}
					});
				}

				dismissProgressDialog();
				initProgressTimerTask();
			}
		};

		progressTimer.schedule(progressTask, 15000);

	}

	private void initProgressTimerTask() {

		if (progressTask != null)
			progressTask.cancel();

		if (progressTimer != null)
			progressTimer.cancel();

		progressTask = null;
		progressTimer = null;

	}

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.fxgizmob_login_layout);
		video_background = (VideoView)this.findViewById(R.id.video_background);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnSignup = (Button) findViewById(R.id.btnSignUp);
		btnSignup.setOnClickListener(this);

		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPass = (EditText) findViewById(R.id.txtPass);

		preUsername = PrefManager.readPrefString(getApplicationContext(),
				"username");
		prePassword = PrefManager.readPrefString(getApplicationContext(),
				"password");
		if (!"".equals(preUsername) && !"".equals(prePassword)) {
			txtEmail.setText(preUsername);
			txtPass.setText(prePassword);
			login(preUsername, prePassword);
		}

	}

	@Override
	public void onResume(){
		super.onResume();
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_background);
		video_background.setVideoURI(uri);
		video_background.start();
		video_background.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.setLooping(true);
			}
	    });

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int i = v.getId();
		switch (i) {
		case R.id.btnLogin:
			Login();
			break;
		case R.id.btnSignUp:
			// Toast.makeText(LoginActivity.this, "Select SignUp",
			// Toast.LENGTH_SHORT).show();
			Register();

			break;
		default:
			break;
		}
	}

	public void Login() {
		showProgressDialog();
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("email", txtEmail.getText().toString());
		query.getFirstInBackground(new GetCallback<ParseUser>() {
			@Override
			public void done(ParseUser object, ParseException e) {
				// TODO Auto-generated method stub
				if (object == null) {
					dismissProgressDialog();

					Toast.makeText(
							LoginActivity.this,
							getResources().getString(R.string.failed_login)
									+ e.getMessage(), Toast.LENGTH_LONG).show();
				} else {
					login(object.getString("username"), txtPass.getText()
							.toString());
				}
			}
		});
	}

	private void login(String userName, String password) {
		ParseUser.logInInBackground(userName, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {

				dismissProgressDialog();

				if (user != null) {
					Toast.makeText(LoginActivity.this,
							getResources().getString(R.string.success_login),
							Toast.LENGTH_LONG).show();

					ParseInstallation installation = ParseInstallation
							.getCurrentInstallation();
					ParseUser me = ParseUser.getCurrentUser();
					me.add("loginState", true);
					installation.put("user", me);
					installation.saveInBackground();
					moveToMainActivity();
				} else {
					Toast.makeText(
							LoginActivity.this,
							getResources().getString(R.string.failed_login)
									+ e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	protected void moveToMainActivity() {
		// TODO Auto-generated method stub
		PrefManager.savePrefString(getApplicationContext(), "username",
				txtEmail.getText().toString());
		PrefManager.savePrefString(getApplicationContext(), "password", txtPass
				.getText().toString());
		Intent intent = new Intent(LoginActivity.this,
				FXGizmoDashboardActivity.class);
		startActivity(intent);
		finish();
	}

	public void Register() {
		Intent i_register = new Intent(LoginActivity.this,
				FxGizmoRegisterActivity.class);
		startActivity(i_register);
	}
}
