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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.util.GlobalVariables;

public class FxGizmoPaypalActivity extends Activity {
//	 // private static final String TAG = "paymentdemoblog";
//	/**
//	* - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
//	*
//	* - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
//	* from https://developer.paypal.com
//	*
//	* - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
//	* without communicating to PayPal's servers.
//	*/
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fxgizmo_paypal_layout);
		
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
		
		btn_order = (Button) findViewById(R.id.order);
		btn_notification = (Button) findViewById(R.id.notification);
		
		if (GlobalVariables.f_payStatement){
			btn_notification.setVisibility(View.VISIBLE);
			btn_order.setVisibility(View.GONE);
		} else {
			btn_notification.setVisibility(View.GONE);
			btn_order.setVisibility(View.VISIBLE);
		}
		
		btn_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				thingToBuy = new PayPalPayment(new BigDecimal("0.1"), "USD", "HeadSet", PayPalPayment.PAYMENT_INTENT_SALE);
				Intent intent = new Intent(FxGizmoPaypalActivity.this, PaymentActivity.class);
				intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
				startActivityForResult(intent, REQUEST_CODE_PAYMENT);
			}
		});
		btn_notification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i_notification = new Intent(FxGizmoPaypalActivity.this, FxGizmooNotificatinActivity.class);
				startActivity(i_notification);
				finish();
			}
		});
	}
	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(FxGizmoPaypalActivity.this,
		PayPalFuturePaymentActivity.class);
		startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
	}
	@Override
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
				btn_order.setVisibility(View.GONE);
				btn_notification.setVisibility(View.VISIBLE);
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
