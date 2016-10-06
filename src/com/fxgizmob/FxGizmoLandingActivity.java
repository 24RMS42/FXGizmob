package com.fxgizmob;

import java.math.BigDecimal;

import org.json.JSONException;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.util.GlobalVariables;

public class FxGizmoLandingActivity extends Activity implements OnClickListener{
	
	AbsoluteLayout landing_layout;
	
	ImageView 	gear_background;
	ImageButton btn_liverating;
	ImageButton btn_calendar;
	ImageButton btn_forextimes;
	ImageButton btn_pretradinginfo;
	Button btn_out;
	
	private int landing_width;
	private int landing_height;
	
	
//	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
//	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
//	// note that these credentials will differ between live & sandbox
//	// environments.

	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
	
//	EditText editText_friend_name;
//	EditText editText_amount;
//	Button btn_paywithpaypal;
	Button btn_order;
	Button btn_notification;
	
	//private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	
	private static PayPalConfiguration config = new PayPalConfiguration()
												.environment(CONFIG_ENVIRONMENT)
												.clientId(Util.paypal_sdk_id)
												// the following are only used in PayPalFuturePaymentActivity.
												.merchantName("Hipster Store")
//												.merchantPrivacyPolicyUri(Uri.parse("https://www.sandbox.paypal.com/webapps/mpp/ua/privacy-full"))
//												.merchantUserAgreementUri(Uri.parse("https://www.sandbox.paypal.com/webapps/mpp/ua/useragreement-full"));
												.merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
												.merchantUserAgreementUri(Uri.parse("https://www.example.com/useragreement"));
	PayPalPayment thingToBuy;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.fxgizmo_landingpage);
		
		
		initPaypalSDK();
		
		landing_layout 	= (AbsoluteLayout) findViewById(R.id.landing_layout);
		
		gear_background = (ImageView) findViewById(R.id.gear_background);
		btn_liverating 	= (ImageButton) findViewById(R.id.btn_liveforexrating);
		btn_calendar	= (ImageButton) findViewById(R.id.btn_economic_calendar);
		btn_forextimes 	= (ImageButton) findViewById(R.id.btn_markettiming);
		btn_pretradinginfo 	= (ImageButton) findViewById(R.id.btn_pretradinginfo);
		btn_out 		= (Button) findViewById(R.id.btn_out);
		
		final AbsoluteLayout.LayoutParams param_gear = (AbsoluteLayout.LayoutParams) gear_background.getLayoutParams();
		final AbsoluteLayout.LayoutParams param_live = (AbsoluteLayout.LayoutParams) btn_liverating.getLayoutParams();
		final AbsoluteLayout.LayoutParams param_calendar = (AbsoluteLayout.LayoutParams) btn_calendar.getLayoutParams();
		final AbsoluteLayout.LayoutParams param_forextimes = (AbsoluteLayout.LayoutParams) btn_forextimes.getLayoutParams();
		final AbsoluteLayout.LayoutParams param_pretradinginfo = (AbsoluteLayout.LayoutParams) btn_pretradinginfo.getLayoutParams();
//		final AbsoluteLayout.LayoutParams param_logout = (AbsoluteLayout.LayoutParams) btn_out.getLayoutParams();
		
		
		
		ViewTreeObserver vto = landing_layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				landing_width = landing_layout.getWidth();
				landing_height = landing_layout.getHeight();
				
				param_gear.x = (int) (landing_width * 0.014);
				param_gear.y = (int) (landing_height * 0.058);
				gear_background.setLayoutParams(param_gear);
				
//				param_forextimes.x = (int) (landing_width * 0.271);
//				param_forextimes.y = (int) (landing_height * 0.103);
				param_forextimes.x = (int) (landing_width * 0.1);
				param_forextimes.y = (int) (landing_height * 0.152);

				btn_forextimes.setLayoutParams(param_forextimes);
				
//				param_pretradinginfo.x = (int) (landing_width * 0.133);
//				param_pretradinginfo.y = (int) (landing_height * 0.287);
				param_pretradinginfo.x = (int) (landing_width * 0.514);
				param_pretradinginfo.y = (int) (landing_height * 0.076);
				btn_pretradinginfo.setLayoutParams(param_pretradinginfo);	

//				param_calendar.x = (int) (landing_width * 0.537);
//				param_calendar.y = (int) (landing_height * 0.261);
				param_calendar.x = (int) (landing_width * 0.589);
				param_calendar.y = (int) (landing_height * 0.453);
				btn_calendar.setLayoutParams(param_calendar);

//				param_live.x = (int) (landing_width * 0.38);
//				param_live.y = (int) (landing_height * 0.4);
				param_live.x = (int) (landing_width * 0.381);
				param_live.y = (int) (landing_height * 0.55);
				btn_liverating.setLayoutParams(param_live);

//				param_logout.x = (int) (landing_width * 0);
//				param_logout.y = (int) (landing_height -param_logout.height);
//				btn_out.setLayoutParams(param_logout);
				
			}
		});
		btn_liverating.setOnClickListener(this);
		btn_calendar.setOnClickListener(this);
		btn_forextimes.setOnClickListener(this);
		btn_pretradinginfo.setOnClickListener(this);
		btn_out.setOnClickListener(this);
	}
	public void onBackPressed(){

	}

	private void initPaypalSDK(){
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btn_liveforexrating:
			Intent i_liverating = new Intent(FxGizmoLandingActivity.this, FxGizmoLiveRatingActivity.class);
			startActivity(i_liverating);					
			break;
		case R.id.btn_economic_calendar:
			Intent i_calendar = new Intent(FxGizmoLandingActivity.this, FxGizmoCalendarActivity.class);
			startActivity(i_calendar);			
			break;

		case R.id.btn_pretradinginfo:
			
			if (GlobalVariables.f_payStatement){
//				btn_notification.setVisibility(View.VISIBLE);
//				btn_order.setVisibility(View.GONE);
				Intent i_notification = new Intent(FxGizmoLandingActivity.this, FxGizmooNotificatinActivity.class);
				startActivity(i_notification);
			} else {
//				btn_notification.setVisibility(View.GONE);
//				btn_order.setVisibility(View.VISIBLE);
				thingToBuy = new PayPalPayment(new BigDecimal("0.1"), "USD", "HeadSet", PayPalPayment.PAYMENT_INTENT_SALE);
				Intent intent = new Intent(FxGizmoLandingActivity.this, PaymentActivity.class);
				intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
				startActivityForResult(intent, REQUEST_CODE_PAYMENT);
			}
			break;
		case R.id.btn_markettiming:
			Intent i_main = new Intent(FxGizmoLandingActivity.this, FXGizmoMainActivity.class);
			startActivity(i_main);			
			break;
		case R.id.btn_out:
//			Intent i_login = new Intent(FxGizmoLandingActivity.this, LoginActivity.class);
//			startActivity(i_login); 
			finish();	
			break;

		default:
			break;
		}
	}
	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(FxGizmoLandingActivity.this,
		PayPalFuturePaymentActivity.class);
		startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						System.out.println(confirm.toJSONObject().toString(4));
						System.out.println(confirm.getPayment().toJSONObject().toString(4));
						Toast.makeText(getApplicationContext(), "Order placed", Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				GlobalVariables.f_payStatement = true;
				Intent i_notification = new Intent(FxGizmoLandingActivity.this, FxGizmooNotificatinActivity.class);
				startActivity(i_notification);
				Toast.makeText(getApplicationContext(), "Payment done successfully", Toast.LENGTH_LONG).show();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				System.out.println("The user canceled.");
				Toast.makeText(getApplicationContext(), "Payment Canceled, Try again", Toast.LENGTH_LONG).show();
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				System.out.println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
				Toast.makeText(getApplicationContext(), "Payment failed, Try again", Toast.LENGTH_LONG).show();
			}
		} 
		else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
				if (auth != null) {
					try {
						Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));
						String authorization_code = auth.getAuthorizationCode();
						Log.i("FuturePaymentExample", authorization_code);
						sendAuthorizationToServer(auth);
						Toast.makeText(getApplicationContext(), "Future Payment code received from PayPal", Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("FuturePaymentExample", "The user canceled.");
			} else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i("FuturePaymentExample", "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
			}
		}
	}
	private void sendAuthorizationToServer(PayPalAuthorization authorization) {
	}
	public void onFuturePaymentPurchasePressed(View pressed) {
		// Get the Application Correlation ID from the SDK
		String correlationId = PayPalConfiguration
		.getApplicationCorrelationId(this);
		Log.i("FuturePaymentExample", "Application Correlation ID: "
		+ correlationId);
		// TODO: Send correlationId and transaction details to your server for
		// processing with
		// PayPal...
		Toast.makeText(getApplicationContext(), "App Correlation ID received from SDK", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
}
