package com.fxgizmob;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class FxGizmoLiveRatingActivity extends Activity {
	
	
	private final String URL = "file:///android_asset/liverating.html"; 
	private WebView webView;
	private ProgressBar progress;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.fxgizmo_liverating_layout);

		webView = (WebView) findViewById(R.id.webview_liverating);
		webView.setWebViewClient(new MyWebViewClient());

		progress = (ProgressBar) findViewById(R.id.progressBar_liverating);
		progress.setVisibility(View.GONE);	
		
		if (validateUrl(URL)) {
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadUrl(URL);
		}
	}
	private boolean validateUrl(String url) {
		return true;
	}
	private class MyWebViewClient extends WebViewClient {	
		 @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;
		    }

		@Override
		public void onPageFinished(WebView view, String url) {
			 progress.setVisibility(View.GONE);
				FxGizmoLiveRatingActivity.this.progress.setProgress(100);
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			 progress.setVisibility(View.VISIBLE);
			 FxGizmoLiveRatingActivity.this.progress.setProgress(0);
			super.onPageStarted(view, url, favicon);
		}
	}
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
}
