package com.fxgizmob;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class FXGizmoMainActivity extends Activity{// implements OnMenuItemClickListener {
	
	private final int LOC_TORONTO = 0;
	private final int LOC_NEWYORK = 1;
	private final int LOC_LONDON = 2;
	private final int LOC_FRANKFURT = 3;
	private final int LOC_JOHANNESBURG = 4;
	private final int LOC_MOSCOW = 5;
	private final int LOC_DUBAI = 6;
	private final int LOC_HONGKONG = 7;
	private final int LOC_SHANGHAI = 8;
	private final int LOC_TOKYO = 9;
	private final int LOC_SYDNEY = 10;
	private final int LOC_WELLINGTON = 11;
	
	private int unit;
	
	private boolean flag_city[] = new boolean[12];
	
	
	private int nStartPointX = 0;
	private int nStartPointY = 0;
	
	private int nTimelineX = 0;
	
	private int map_width;
	private int map_height;
	Handler timerhandler = new Handler();
	//Current Time and Week Day
	TextView current_time;
	TextView display_ampm;
	TextView day_of_week;
	TextView market_state;
	//City Time View
	TextView time_toronto;
	TextView time_newyork;
	TextView time_london;
	TextView time_frankfurt;
	TextView time_johannesburg;
	TextView time_moscow;
	TextView time_dubai;
	TextView time_hongkong;
	TextView time_shanghai;
	TextView time_tokyo;
	TextView time_sydney;
	TextView time_wellington;
	//City Day of Week View
	TextView day_toronto;
	TextView day_newyork;
	TextView day_london;
	TextView day_frankfurt;
	TextView day_johannesburg;
	TextView day_moscow;
	TextView day_dubai;
	TextView day_hongkong;
	TextView day_shanghai;
	TextView day_tokyo;
	TextView day_sydney;
	TextView day_wellington;
	
	
	//City Name
	TextView name_toronto;
	TextView name_newyork;
	TextView name_london;
	TextView name_frankfurt;
	TextView name_johannesburg;
	TextView name_moscow;
	TextView name_dubai;
	TextView name_hongkong;
	TextView name_shanghai;
	TextView name_tokyo;
	TextView name_sydney;
	TextView name_wellington;
	
	//City Real Time View and Name
	TextView time_toronto_info;
	TextView time_newyork_info;
	TextView time_london_info;
	TextView time_frankfurt_info;
	TextView time_johannesburg_info;
	TextView time_moscow_info;
	TextView time_dubai_info;
	TextView time_hongkong_info;
	TextView time_shanghai_info;
	TextView time_tokyo_info;
	TextView time_sydney_info;
	TextView time_wellington_info;
	
	//City Info Layout
	LinearLayout layout_toronto;
	LinearLayout layout_newyork;
	LinearLayout layout_london;
	LinearLayout layout_frankfurt;
	LinearLayout layout_johannesburg;
	LinearLayout layout_moscow;
	LinearLayout layout_dubai;
	LinearLayout layout_hongkong;
	LinearLayout layout_shanghai;
	LinearLayout layout_tokyo;
	LinearLayout layout_sydney;
	LinearLayout layout_wellington;
	
	//Button City
	ImageButton btn_toronto;
	ImageButton btn_newyork;
	ImageButton btn_london;
	ImageButton btn_frankfurt;
	ImageButton btn_johannesburg;
	ImageButton btn_moscow;
	ImageButton btn_dubai;
	ImageButton btn_hongkong;
	ImageButton btn_shanghai;
	ImageButton btn_tokyo;
	ImageButton btn_sydney;
	ImageButton btn_wellington;
	
	
	//Number View
	ImageView num_m;
	ImageView num_12_pm;
	ImageView num_11_pm;
	ImageView num_10_pm;
	ImageView num_09_pm;
	ImageView num_08_pm;
	ImageView num_06_pm;
	ImageView num_07_pm;
	ImageView num_05_pm;
	ImageView num_04_pm;
	ImageView num_03_pm;
	ImageView num_02_pm;
	ImageView num_01_pm;
	ImageView num_12_am;
	ImageView num_11_am;
	ImageView num_10_am;
	ImageView num_09_am;
	ImageView num_08_am;
	ImageView num_07_am;
	ImageView num_06_am;
	ImageView num_05_am;
	ImageView num_04_am;
	ImageView num_03_am;
	ImageView num_02_am;
	ImageView num_01_am;
	ImageView img_timeline;
	
	
//	LayoutInflater layoutInflater;
//	View popupView;
//	PopupWindow popupWindow;
//	TextView city_name;
//	TextView time_info;
//	ImageView flag_country;
	
	//TimeBar
	LinearLayout time_bar;
	//MAP View
	AbsoluteLayout map_layout;
	AbsoluteLayout.LayoutParams timelineParams;
	
	//Current Time Variable
	int cur_seconds = 0;
	int cur_minutes = 0;
	int cur_hours = 0;
	int count_sec = 0;
	
	int day = 0;
	
	Timer timer;
	TimerTask timerTask;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.fxgizmob_main_layout);
		
		for (int i = 0; i < 12; i++){
			flag_city[i] = false;
		}		
		
		current_time 	= (TextView) findViewById(R.id.current_time);
		display_ampm 	= (TextView) findViewById(R.id.display_ampm);
		day_of_week 	= (TextView) findViewById(R.id.day_of_week); 
		market_state 	= (TextView) findViewById(R.id.txt_marketstate);
		
		time_toronto 		= (TextView) findViewById(R.id.time_toronto);
		time_newyork 		= (TextView) findViewById(R.id.time_newyork);
		time_london 		= (TextView) findViewById(R.id.time_london);
		time_frankfurt 		= (TextView) findViewById(R.id.time_frankfurt);
		time_johannesburg 	= (TextView) findViewById(R.id.time_johannesburg);
		time_moscow 		= (TextView) findViewById(R.id.time_moscow);
		time_dubai 			= (TextView) findViewById(R.id.time_dubai);
		time_hongkong 		= (TextView) findViewById(R.id.time_hongkong);
		time_shanghai 		= (TextView) findViewById(R.id.time_shanghai);
		time_tokyo 			= (TextView) findViewById(R.id.time_tokyo);
		time_sydney 		= (TextView) findViewById(R.id.time_sydney);
		time_wellington 	= (TextView) findViewById(R.id.time_wellington);
				
		day_toronto 		= (TextView) findViewById(R.id.day_toronto);
		day_newyork 		= (TextView) findViewById(R.id.day_newyork);
		day_london 			= (TextView) findViewById(R.id.day_london);
		day_frankfurt 		= (TextView) findViewById(R.id.day_frankfurt);
		day_johannesburg 	= (TextView) findViewById(R.id.day_johannesburg);
		day_moscow 			= (TextView) findViewById(R.id.day_moscow);
		day_dubai 			= (TextView) findViewById(R.id.day_dubai);
		day_hongkong 		= (TextView) findViewById(R.id.day_hongkong);
		day_shanghai 		= (TextView) findViewById(R.id.day_shanghai);
		day_tokyo 			= (TextView) findViewById(R.id.day_tokyo);
		day_sydney 			= (TextView) findViewById(R.id.day_sydney);
		day_wellington 		= (TextView) findViewById(R.id.day_wellington);
		
		name_toronto 		= (TextView) findViewById(R.id.name_toronto);
		name_newyork 		= (TextView) findViewById(R.id.name_newyork);
		name_london 		= (TextView) findViewById(R.id.name_london);
		name_frankfurt 		= (TextView) findViewById(R.id.name_frankfurt);
		name_johannesburg 	= (TextView) findViewById(R.id.name_johannesburg);
		name_moscow 		= (TextView) findViewById(R.id.name_moscow);
		name_dubai 			= (TextView) findViewById(R.id.name_dubai);
		name_hongkong 		= (TextView) findViewById(R.id.name_hongkong);
		name_shanghai 		= (TextView) findViewById(R.id.name_shanghai);
		name_tokyo 			= (TextView) findViewById(R.id.name_tokyo);
		name_sydney 		= (TextView) findViewById(R.id.name_sydney);
		name_wellington 	= (TextView) findViewById(R.id.name_wellington);		
			
		layout_toronto 	= (LinearLayout) findViewById(R.id.layout_toronto);
		layout_newyork 	= (LinearLayout) findViewById(R.id.layout_newyork);
		layout_london 	= (LinearLayout) findViewById(R.id.layout_london);
		layout_frankfurt 	= (LinearLayout) findViewById(R.id.layout_frankfurt);
		layout_johannesburg 	= (LinearLayout) findViewById(R.id.layout_johannesburg);
		layout_moscow 	= (LinearLayout) findViewById(R.id.layout_moscow);
		layout_dubai 	= (LinearLayout) findViewById(R.id.layout_dubai);
		layout_hongkong 	= (LinearLayout) findViewById(R.id.layout_hongkong);
		layout_shanghai 	= (LinearLayout) findViewById(R.id.layout_shanghai);
		layout_tokyo 	= (LinearLayout) findViewById(R.id.layout_tokyo);
		layout_sydney 	= (LinearLayout) findViewById(R.id.layout_sydney);
		layout_wellington 	= (LinearLayout) findViewById(R.id.layout_wellington);
		
		
		
		time_toronto_info 		= (TextView) findViewById(R.id.time_toronto_info);
		time_newyork_info 		= (TextView) findViewById(R.id.time_newyork_info);
		time_london_info 		= (TextView) findViewById(R.id.time_london_info);
		time_frankfurt_info 	= (TextView) findViewById(R.id.time_frankfurt_info);
		time_johannesburg_info 	= (TextView) findViewById(R.id.time_johannesburg_info);
		time_moscow_info 		= (TextView) findViewById(R.id.time_moscow_info);
		time_dubai_info 		= (TextView) findViewById(R.id.time_dubai_info);
		time_hongkong_info	 	= (TextView) findViewById(R.id.time_hongkong_info);
		time_shanghai_info		= (TextView) findViewById(R.id.time_shanghai_info);
		time_tokyo_info 		= (TextView) findViewById(R.id.time_tokyo_info);
		time_sydney_info 		= (TextView) findViewById(R.id.time_sydney_info);
		time_wellington_info 	= (TextView) findViewById(R.id.time_wellington_info);
		
		time_bar = (LinearLayout) findViewById(R.id.time_bar);
		
		//Number View
		num_m 	  = (ImageView) findViewById(R.id.num_m);		//toronto, newyork
		num_12_pm = (ImageView) findViewById(R.id.num_12_pm);
		num_11_pm = (ImageView) findViewById(R.id.num_11_pm);
		num_10_pm = (ImageView) findViewById(R.id.num_10_pm);
		num_09_pm = (ImageView) findViewById(R.id.num_09_pm);	//london
		num_08_pm = (ImageView) findViewById(R.id.num_08_pm);	//frankfurt
		num_07_pm = (ImageView) findViewById(R.id.num_07_pm);
		num_06_pm = (ImageView) findViewById(R.id.num_06_pm);	//moscow, johannesburg
		num_05_pm = (ImageView) findViewById(R.id.num_05_pm);	//dubai
		num_04_pm = (ImageView) findViewById(R.id.num_04_pm);
		num_03_pm = (ImageView) findViewById(R.id.num_03_pm);
		num_02_pm = (ImageView) findViewById(R.id.num_02_pm);
		num_01_pm = (ImageView) findViewById(R.id.num_01_pm);	//hongkong, shanghai
		num_12_am = (ImageView) findViewById(R.id.num_12_am);	//tokyo
		num_11_am = (ImageView) findViewById(R.id.num_11_am);
		num_10_am = (ImageView) findViewById(R.id.num_10_am);	//sydney, wellington
		num_09_am = (ImageView) findViewById(R.id.num_09_am);
		num_08_am = (ImageView) findViewById(R.id.num_08_am);
		num_07_am = (ImageView) findViewById(R.id.num_07_am);
		num_06_am = (ImageView) findViewById(R.id.num_06_am);
		num_05_am = (ImageView) findViewById(R.id.num_05_am);
		num_04_am = (ImageView) findViewById(R.id.num_04_am);
		num_03_am = (ImageView) findViewById(R.id.num_03_am);
		num_02_am = (ImageView) findViewById(R.id.num_02_am);
		num_01_am = (ImageView) findViewById(R.id.num_01_am);
		
		
		img_timeline = (ImageView) findViewById(R.id.img_timeline);
		
		unit = convertDPtoPixel(18);
		//Popup Window
//		layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);					
//		popupView = layoutInflater.inflate(R.layout.popup_layout, null);
		
//		city_name = (TextView) popupView.findViewById(R.id.city_name);
//		time_info = (TextView) popupView.findViewById(R.id.time_info);
//		flag_country = (ImageView) popupView.findViewById(R.id.flag_country);
		
//		popupWindow = new PopupWindow(popupView, unit * 9, 100);

		map_layout = (AbsoluteLayout) findViewById(R.id.map_layout);
		
		btn_toronto =(ImageButton) findViewById(R.id.btn_toronto);
		final AbsoluteLayout.LayoutParams torontoParams = (AbsoluteLayout.LayoutParams) btn_toronto.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_torontoParam = (AbsoluteLayout.LayoutParams) layout_toronto.getLayoutParams();
//		btn_toronto.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_TORONTO);
//			}
//		});
		
		btn_newyork =(ImageButton) findViewById(R.id.btn_newyork);
		final AbsoluteLayout.LayoutParams newyorkParams = (AbsoluteLayout.LayoutParams) btn_newyork.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_newyorkParam = (AbsoluteLayout.LayoutParams) layout_newyork.getLayoutParams();
		
//		btn_newyork.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_NEWYORK);
//			}
//		});
		
		btn_london =(ImageButton) findViewById(R.id.btn_london);
		final AbsoluteLayout.LayoutParams londonParams = (AbsoluteLayout.LayoutParams) btn_london.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_londonParam = (AbsoluteLayout.LayoutParams) layout_london.getLayoutParams();

//		btn_london.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_LONDON);
//			}
//		});
		
		btn_frankfurt =(ImageButton) findViewById(R.id.btn_frankfurt);
		final AbsoluteLayout.LayoutParams frankfurtParams = (AbsoluteLayout.LayoutParams) btn_frankfurt.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_frankfurtParam = (AbsoluteLayout.LayoutParams) layout_frankfurt.getLayoutParams();
//		btn_frankfurt.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_FRANKFURT);
//			}
//		});
		
		btn_johannesburg =(ImageButton) findViewById(R.id.btn_johannesburg);
		final AbsoluteLayout.LayoutParams johannesburgParams = (AbsoluteLayout.LayoutParams) btn_johannesburg.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_johannesburgParam = (AbsoluteLayout.LayoutParams) layout_johannesburg.getLayoutParams();
//		btn_johannesburg.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_JOHANNESBURG);
//			}
//		});
		
		btn_moscow =(ImageButton) findViewById(R.id.btn_moscow);
		final AbsoluteLayout.LayoutParams moscowParams = (AbsoluteLayout.LayoutParams) btn_moscow.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_moscowParam = (AbsoluteLayout.LayoutParams) layout_moscow.getLayoutParams();
//		btn_moscow.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_MOSCOW);
//			}
//		});
		
		btn_dubai =(ImageButton) findViewById(R.id.btn_dubai);
		final AbsoluteLayout.LayoutParams dubaiParams = (AbsoluteLayout.LayoutParams) btn_dubai.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_dubaiParam = (AbsoluteLayout.LayoutParams) layout_dubai.getLayoutParams();
//		btn_dubai.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_DUBAI);
//			}
//		});
		
		btn_hongkong =(ImageButton) findViewById(R.id.btn_hongkong);
		final AbsoluteLayout.LayoutParams hongknogParams = (AbsoluteLayout.LayoutParams) btn_hongkong.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_hongknogParam = (AbsoluteLayout.LayoutParams) layout_hongkong.getLayoutParams();
//		btn_hongkong.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_HONGKONG);
//			}
//		});
		
		btn_shanghai =(ImageButton) findViewById(R.id.btn_shanghai);
		final AbsoluteLayout.LayoutParams shanghaiParams = (AbsoluteLayout.LayoutParams) btn_shanghai.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_shanghaiParam = (AbsoluteLayout.LayoutParams) layout_shanghai.getLayoutParams();
//		btn_shanghai.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_SHANGHAI);
//			}
//		});
		
		btn_tokyo =(ImageButton) findViewById(R.id.btn_tokyo);
		final AbsoluteLayout.LayoutParams tokyoParams = (AbsoluteLayout.LayoutParams) btn_tokyo.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_tokyoParam = (AbsoluteLayout.LayoutParams) layout_tokyo.getLayoutParams();
//		btn_tokyo.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_TOKYO);
//			}
//		});
		
		btn_sydney =(ImageButton) findViewById(R.id.btn_sydney);
		final AbsoluteLayout.LayoutParams sydneyParams = (AbsoluteLayout.LayoutParams) btn_sydney.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_sydneyParam = (AbsoluteLayout.LayoutParams) layout_sydney.getLayoutParams();
//		btn_sydney.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_SYDNEY);
//			}
//		});		
		
		btn_wellington =(ImageButton) findViewById(R.id.btn_wellington);
		final AbsoluteLayout.LayoutParams wellingtionParams = (AbsoluteLayout.LayoutParams) btn_wellington.getLayoutParams();
		final AbsoluteLayout.LayoutParams layout_wellingtionParam = (AbsoluteLayout.LayoutParams) layout_wellington.getLayoutParams();
//		btn_wellington.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showTimeInfo(LOC_WELLINGTON);
//			}
//		});
				
		timelineParams = (AbsoluteLayout.LayoutParams) img_timeline.getLayoutParams();
		
		
		final ImageView img_map = (ImageView) findViewById(R.id.image_map);
		
		
		ViewTreeObserver vto = img_map.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		@Override
		  public void onGlobalLayout() {		    
		    map_width = img_map.getWidth();
		    map_height = img_map.getHeight();
		    
			wellingtionParams.x = (int) (map_width * 0.96);
			wellingtionParams.y = (int) (map_height * 0.82);
			btn_wellington.setLayoutParams(wellingtionParams);
		    
			sydneyParams.x = (int) (map_width * 0.91);
			sydneyParams.y = (int) (map_height * 0.765);
			btn_sydney.setLayoutParams(sydneyParams);		
			
			tokyoParams.x = (int) (map_width * 0.859);
			tokyoParams.y = (int) (map_height * 0.391);
			btn_tokyo.setLayoutParams(tokyoParams);
			
			hongknogParams.x = (int) (map_width * 0.792);
			hongknogParams.y = (int) (map_height * 0.497);
			btn_hongkong.setLayoutParams(hongknogParams);
			
			shanghaiParams.x = (int) (map_width * 0.82);
			shanghaiParams.y = (int) (map_height * 0.45);
			btn_shanghai.setLayoutParams(shanghaiParams);
			
			dubaiParams.x = (int) (map_width * 0.61);
			dubaiParams.y = (int) (map_height * 0.467);
			btn_dubai.setLayoutParams(dubaiParams);
			
			moscowParams.x = (int) (map_width * 0.569);
			moscowParams.y = (int) (map_height * 0.315);
			btn_moscow.setLayoutParams(moscowParams);
		
			johannesburgParams.x = (int) (map_width * 0.539);
			johannesburgParams.y = (int) (map_height * 0.681);
			btn_johannesburg.setLayoutParams(johannesburgParams);
			
			frankfurtParams.x = (int) (map_width * 0.49);
			frankfurtParams.y = (int) (map_height * 0.32);
			btn_frankfurt.setLayoutParams(frankfurtParams);
			
			londonParams.x = (int) (map_width * 0.451);
			londonParams.y = (int) (map_height * 0.289);
			btn_london.setLayoutParams(londonParams);
			
			newyorkParams.x = (int) (map_width * 0.265);
			newyorkParams.y = (int) (map_height * 0.355);
			btn_newyork.setLayoutParams(newyorkParams);
			
			torontoParams.x = (int) (map_width * 0.255);
			torontoParams.y = (int) (map_height * 0.3);
			btn_toronto.setLayoutParams(torontoParams);
			
			layout_torontoParam.x = (int) (map_width * 0.02);
			layout_torontoParam.y = (int) (map_height * 0.01);
			layout_toronto.setLayoutParams(layout_torontoParam);

			layout_newyorkParam.x = (int) (map_width * 0.02);
			layout_newyorkParam.y = (int) (map_height * 0.11);
			layout_newyork.setLayoutParams(layout_newyorkParam);
			
			layout_londonParam.x = (int) (map_width * 0.14);
			layout_londonParam.y = (int) (map_height * 0.01);
			layout_london.setLayoutParams(layout_londonParam);

			layout_frankfurtParam.x = (int) (map_width * 0.26);
			layout_frankfurtParam.y = (int) (map_height * 0.01);
			layout_frankfurt.setLayoutParams(layout_frankfurtParam);

			layout_johannesburgParam.x = (int) (map_width * 0.38);
			layout_johannesburgParam.y = (int) (map_height * 0.01);
			layout_johannesburg.setLayoutParams(layout_johannesburgParam);

			layout_moscowParam.x = (int) (map_width * 0.38);
			layout_moscowParam.y = (int) (map_height * 0.11);
			layout_moscow.setLayoutParams(layout_moscowParam);

			layout_dubaiParam.x = (int) (map_width * 0.5);
			layout_dubaiParam.y = (int) (map_height * 0.01);
			layout_dubai.setLayoutParams(layout_dubaiParam);

			layout_hongknogParam.x = (int) (map_width * 0.62);
			layout_hongknogParam.y = (int) (map_height * 0.01);
			layout_hongkong.setLayoutParams(layout_hongknogParam);

			layout_shanghaiParam.x = (int) (map_width * 0.62);
			layout_shanghaiParam.y = (int) (map_height * 0.11);
			layout_shanghai.setLayoutParams(layout_shanghaiParam);

			layout_tokyoParam.x = (int) (map_width * 0.74);
			layout_tokyoParam.y = (int) (map_height * 0.01);
			layout_tokyo.setLayoutParams(layout_tokyoParam);

			layout_sydneyParam.x = (int) (map_width * 0.86);
			layout_sydneyParam.y = (int) (map_height * 0.01);
			layout_sydney.setLayoutParams(layout_sydneyParam);

			layout_wellingtionParam.x = (int) (map_width * 0.86);
			layout_wellingtionParam.y = (int) (map_height * 0.11);
			layout_wellington.setLayoutParams(layout_wellingtionParam);
			
			nStartPointX = torontoParams.x;
			nStartPointY = torontoParams.y;
			
			 
			time_bar.setPadding(nStartPointX - 150, 0, 0, 0);	
			
//		    ViewTreeObserver obs = img_map.getViewTreeObserver();
//		    obs.removeGlobalOnLayoutListener(this);
		  }
		});

	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);
	    // Call here getWidth() and getHeight()
	 }
	@Override
	protected void onResume(){
		super.onResume();
		startTimer();
	}
	
	public void initFlag(){
		for (int i = 0; i < 12; i ++){
			flag_city[i] = false;
		}
	}
	
	public void initTimebar(){
		
		num_01_am.setBackgroundResource(R.drawable.num_01);
		num_02_am.setBackgroundResource(R.drawable.num_02);
		num_03_am.setBackgroundResource(R.drawable.num_03);
		num_04_am.setBackgroundResource(R.drawable.num_04);
		num_05_am.setBackgroundResource(R.drawable.num_05);
		num_06_am.setBackgroundResource(R.drawable.num_06);
		num_07_am.setBackgroundResource(R.drawable.num_07);
		num_08_am.setBackgroundResource(R.drawable.num_08);
		num_09_am.setBackgroundResource(R.drawable.num_09);
		num_10_am.setBackgroundResource(R.drawable.num_10);
		num_11_am.setBackgroundResource(R.drawable.num_11);
		num_12_am.setBackgroundResource(R.drawable.num_12);
		
		num_01_pm.setBackgroundResource(R.drawable.num_01);
		num_02_pm.setBackgroundResource(R.drawable.num_02);
		num_03_pm.setBackgroundResource(R.drawable.num_03);
		num_04_pm.setBackgroundResource(R.drawable.num_04);
		num_05_pm.setBackgroundResource(R.drawable.num_05);
		num_06_pm.setBackgroundResource(R.drawable.num_06);
		num_07_pm.setBackgroundResource(R.drawable.num_07);
		num_08_pm.setBackgroundResource(R.drawable.num_08);
		num_09_pm.setBackgroundResource(R.drawable.num_09);
		num_10_pm.setBackgroundResource(R.drawable.num_10);
		num_11_pm.setBackgroundResource(R.drawable.num_11);
		num_12_pm.setBackgroundResource(R.drawable.num_12);
	}
	public void initCityTime(){
		name_toronto.setTextColor(getResources().getColor(R.color.text_color));
		time_toronto.setTextColor(getResources().getColor(R.color.text_color));
		
		name_newyork.setTextColor(getResources().getColor(R.color.text_color));
		time_newyork.setTextColor(getResources().getColor(R.color.text_color));

		name_london.setTextColor(getResources().getColor(R.color.text_color));
		time_london.setTextColor(getResources().getColor(R.color.text_color));

		name_frankfurt.setTextColor(getResources().getColor(R.color.text_color));
		time_frankfurt.setTextColor(getResources().getColor(R.color.text_color));

		name_johannesburg.setTextColor(getResources().getColor(R.color.text_color));
		time_johannesburg.setTextColor(getResources().getColor(R.color.text_color));

		name_moscow.setTextColor(getResources().getColor(R.color.text_color));
		time_moscow.setTextColor(getResources().getColor(R.color.text_color));

		name_dubai.setTextColor(getResources().getColor(R.color.text_color));
		time_dubai.setTextColor(getResources().getColor(R.color.text_color));

		name_hongkong.setTextColor(getResources().getColor(R.color.text_color));
		time_hongkong.setTextColor(getResources().getColor(R.color.text_color));

		name_shanghai.setTextColor(getResources().getColor(R.color.text_color));
		time_shanghai.setTextColor(getResources().getColor(R.color.text_color));

		name_tokyo.setTextColor(getResources().getColor(R.color.text_color));
		time_tokyo.setTextColor(getResources().getColor(R.color.text_color));

		name_sydney.setTextColor(getResources().getColor(R.color.text_color));
		time_sydney.setTextColor(getResources().getColor(R.color.text_color));

		name_wellington.setTextColor(getResources().getColor(R.color.text_color));
		time_wellington.setTextColor(getResources().getColor(R.color.text_color));


		
	}

//	
//	public void showTimeInfo(int city){
//		popupWindow.setOutsideTouchable(true);
//		switch (city) {
//		case LOC_TORONTO:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_toronto);
//			initFlag();
//			flag_city[LOC_TORONTO] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_canada);
//			
//			popupWindow.showAsDropDown(num_m, -convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_NEWYORK:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_newyork);
//			initFlag();
//			flag_city[LOC_NEWYORK] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_us);
//			popupWindow.showAsDropDown(num_m, -convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_LONDON:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_london);
//			initFlag();
//			flag_city[LOC_LONDON] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_england);
//			popupWindow.showAsDropDown(num_09_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_FRANKFURT:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_frankfurt);
//			initFlag();
//			flag_city[LOC_FRANKFURT] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_germany);
//			popupWindow.showAsDropDown(num_08_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_JOHANNESBURG:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_johannesburg);
//			initFlag();
//			flag_city[LOC_JOHANNESBURG] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_southafrica);
//			popupWindow.showAsDropDown(num_06_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_MOSCOW:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_moscow);
//			initFlag();
//			flag_city[LOC_MOSCOW] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_russia);
//			popupWindow.showAsDropDown(num_06_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_DUBAI:
//			popupWindow.dismiss();			
//			city_name.setText(R.string.city_dubai);
//			initFlag();
//			flag_city[LOC_DUBAI] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_arab);			
//			popupWindow.showAsDropDown(num_05_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_HONGKONG:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_hongkong);
//			initFlag();
//			flag_city[LOC_HONGKONG] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_hongkong);
//			popupWindow.showAsDropDown(num_01_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_SHANGHAI:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_shanghai);
//			initFlag();
//			flag_city[LOC_SHANGHAI] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_china);
//			popupWindow.showAsDropDown(num_01_pm, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_TOKYO:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_tokyo);
//			initFlag();
//			flag_city[LOC_TOKYO] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_japan);
//			popupWindow.showAsDropDown(num_12_am, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_SYDNEY:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_sydney);
//			initFlag();
//			flag_city[LOC_SYDNEY] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_australia);
//			popupWindow.showAsDropDown(num_10_am, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;
//		case LOC_WELLINGTON:
//			popupWindow.dismiss();
//			city_name.setText(R.string.city_wellington);
//			initFlag();
//			flag_city[LOC_WELLINGTON] = true;
//			flag_country.setBackgroundResource(R.drawable.flag_newzealand);
//			popupWindow.showAsDropDown(num_10_am, convertDPtoPixel(9), convertDPtoPixel(20));
//			break;			
//		default:
//			break;
//		}		
//	}	
	
	public void startTimer() {
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 0, 1000);
	}
	public void initializeTimerTask() {
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				timerhandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						setCurrentLocalTime();
						try {
							setCityTime();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						setTimeLine();
						try {
							displayTimeInfo();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		};	
	}

	public void setCurrentLocalTime(){
		Calendar c = Calendar.getInstance();		
		cur_seconds = c.get(Calendar.SECOND);
		cur_minutes = c.get(Calendar.MINUTE);
		cur_hours 	= c.get(Calendar.HOUR);		
		
		day = c.get(Calendar.DAY_OF_WEEK);		
		
		
		
		int temp_ampm = c.get(Calendar.AM_PM);
		
		setTimeBar(cur_hours, temp_ampm);
		setTimeLine(cur_hours, cur_minutes, temp_ampm);
		
		if (cur_hours == 0)
			cur_hours = 12;					
		String time = temp_ampm == Calendar.AM ? "AM" : "PM";	
		
		current_time.setText(String.format("%d : %02d", cur_hours, cur_minutes));
		
		
		display_ampm.setText(time);		
		day_of_week.setText(getDayofWeek(day));
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY){
			market_state.setTextColor(getResources().getColor(R.color.text_close));
			day_of_week.setTextColor(getResources().getColor(R.color.text_close));
		} else {
			market_state.setTextColor(getResources().getColor(R.color.text_color));
			day_of_week.setTextColor(getResources().getColor(R.color.text_color));
		}
	}
	
	public void setTimeBar(int hour, int time_ampm){
		
		initTimebar();	
		
		if (time_ampm == Calendar.AM){
			if (hour >= 1 && hour < 2){
				num_01_am.setBackgroundResource(R.drawable.am_01);
			} else if ( hour >= 2 && hour < 3){
				num_02_am.setBackgroundResource(R.drawable.am_02);
			} else if ( hour >= 3 && hour < 4){
				num_03_am.setBackgroundResource(R.drawable.am_03);
			} else if ( hour >= 4 && hour < 5){
				num_04_am.setBackgroundResource(R.drawable.am_04);
			} else if ( hour >= 5 && hour < 6){
				num_05_am.setBackgroundResource(R.drawable.am_05);
			} else if ( hour >= 6 && hour < 7){
				num_06_am.setBackgroundResource(R.drawable.am_06);
			} else if ( hour >= 7 && hour < 8){
				num_07_am.setBackgroundResource(R.drawable.am_07);
			} else if ( hour >= 8 && hour < 9){
				num_08_am.setBackgroundResource(R.drawable.am_08);
			} else if ( hour >= 9 && hour < 10){
				num_09_am.setBackgroundResource(R.drawable.am_09);
			} else if ( hour >= 10 && hour < 11){
				num_10_am.setBackgroundResource(R.drawable.am_10);
			} else if ( hour >= 11 && hour < 12){
				num_11_am.setBackgroundResource(R.drawable.am_11);
			} else if ( hour >= 0 && hour < 1){
				num_12_pm.setBackgroundResource(R.drawable.am_12);
			} 
		} else if (time_ampm == Calendar.PM){
			if (hour >= 1 && hour < 2){
				num_01_pm.setBackgroundResource(R.drawable.pm_01);
			} else if ( hour >= 2 && hour < 3){
				num_02_pm.setBackgroundResource(R.drawable.pm_02);
			} else if ( hour >= 3 && hour < 4){
				num_03_pm.setBackgroundResource(R.drawable.pm_03);
			} else if ( hour >= 4 && hour < 5){
				num_04_pm.setBackgroundResource(R.drawable.pm_04);
			} else if ( hour >= 5 && hour < 6){
				num_05_pm.setBackgroundResource(R.drawable.pm_05);
			} else if ( hour >= 6 && hour < 7){
				num_06_pm.setBackgroundResource(R.drawable.pm_06);
			} else if ( hour >= 7 && hour < 8){
				num_07_pm.setBackgroundResource(R.drawable.pm_07);
			} else if ( hour >= 8 && hour < 9){
				num_08_pm.setBackgroundResource(R.drawable.pm_08);
			} else if ( hour >= 9 && hour < 10){
				num_09_pm.setBackgroundResource(R.drawable.pm_09);
			} else if ( hour >= 10 && hour < 11){
				num_10_pm.setBackgroundResource(R.drawable.pm_10);
			} else if ( hour >= 11 && hour < 12){
				num_11_pm.setBackgroundResource(R.drawable.pm_11);
			} else if ( hour >= 0 && hour < 1){
				num_12_am.setBackgroundResource(R.drawable.am_12);
			} 		
		}
	}
	public void displayTimeInfo() throws ParseException{
//		if (flag_city[LOC_TORONTO]){
			time_toronto_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT-05")));
//		} else if (flag_city[LOC_NEWYORK]){
			time_newyork_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT-05")));
//		} else if (flag_city[LOC_LONDON]){
			time_london_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+00")));
//		} else if (flag_city[LOC_FRANKFURT]){
			time_frankfurt_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+01")));
//		} else if (flag_city[LOC_JOHANNESBURG]){
			time_johannesburg_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+03")));
//		} else if (flag_city[LOC_MOSCOW]){
			time_moscow_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+03")));
//		} else if (flag_city[LOC_DUBAI]){
			time_dubai_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+04")));
//		} else if (flag_city[LOC_HONGKONG]){
			time_hongkong_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+08")));
//		} else if (flag_city[LOC_SHANGHAI]){
			time_shanghai_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+08")));
//		} else if (flag_city[LOC_TOKYO]){
			time_tokyo_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+09")));
//		} else if (flag_city[LOC_SYDNEY]){
			time_sydney_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+11")));
//		} else if (flag_city[LOC_WELLINGTON]){
			time_wellington_info.setText(setTimeInfo(TimeZone.getTimeZone("GMT+11")));
//		}
	}
	
	public void setTimeLine(int hour, int minute, int ampm ) {
		int entire_minute;
		int position = 0;
		if (ampm == Calendar.AM){
			if (hour == 0)
				hour += 24;
			entire_minute = hour * 60 + minute;
		} else {
			entire_minute = (hour + 12) * 60 + minute;
		}
		position = (int)(num_01_am.getX() - ((num_01_am.getX() - num_m.getX()) * entire_minute / 1440))  + convertDPtoPixel(32); //+ btn_menu.getWidth()
		
		timelineParams.x = position;
		
		img_timeline.setLayoutParams(timelineParams);		
	}
	
	
	public int convertDPtoPixel(int nDp){
		int nPixel;
	    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
	    nPixel = Math.round(nDp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
		return nPixel;
	}
	
	public int convertPixelToDp(int nPixel) {
		int nDp;
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
	    nDp = Math.round(nPixel / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return nDp;
	}
	
	public String convertTimeZone(TimeZone tz){
		Calendar c = Calendar.getInstance(tz);
		String strConvertTime;
		
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		
		if (temp_hour == 0)
			temp_hour = 12;					
		String time = temp_ampm == Calendar.AM ? "am" : "pm";
		
		strConvertTime = String.format("%d : %02d %s", temp_hour, temp_minute, time);
		
		return strConvertTime;
	}
	public String setDayofWeek(TimeZone tz){
		Calendar c = Calendar.getInstance(tz);
		String strDay = "";
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		switch (temp_day) {
		case Calendar.SUNDAY:
			strDay = "SUN";
			break;
		case Calendar.MONDAY:
			strDay = "MON";
			break;
		case Calendar.TUESDAY:
			strDay = "TUE";
			break;
		case Calendar.WEDNESDAY:
			strDay = "WED";
			break;
		case Calendar.THURSDAY:
			strDay = "THU";
			break;
		case Calendar.FRIDAY:
			strDay = "FRI";
			break;
		case Calendar.SATURDAY:
			strDay = "SAT";
			break;
		default:
			break;
		}		
		return strDay;
	}
	public String setTimeInfo(TimeZone tz) throws ParseException{
		int totalTime = 32400;
		String openTime = "08:00 am";
		SimpleDateFormat sDF = new SimpleDateFormat("hh:mm a");
		sDF.setTimeZone(tz);
		Date openDate = sDF.parse(openTime);
		sDF.setTimeZone(TimeZone.getDefault());
		
		String txtOpen = sDF.format(openDate);
		
		Calendar c = Calendar.getInstance(tz);

		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
						
		String time = temp_ampm == Calendar.AM ? "am" : "pm";

		if (temp_day == Calendar.SUNDAY || temp_day == Calendar.SATURDAY){
			String opentime;
			opentime = String.format("Open at %s", txtOpen);
			
			return opentime;
		} else if ( temp_hour < 8 && temp_ampm == Calendar.AM){
			String opentime;
			opentime = String.format("Open at %s",  txtOpen);
			return opentime;
		} else if ( temp_hour >= 5 && temp_ampm == Calendar.PM){
			String opentime;
			opentime = String.format("Open at %s", txtOpen);
			return opentime;
		} else {
			if (temp_ampm == Calendar.PM){
				temp_hour += 12;
			}
			
			int remainingTime;
			remainingTime = totalTime - (temp_hour - 8) * 3600 - temp_minute * 60 - temp_second;

			String strRemainingT;
			int remainingSecond = remainingTime % 60;
			int remainingMinute = ((remainingTime - remainingSecond) / 60) % 60;
			int remainingHour = (remainingTime -remainingSecond - remainingMinute * 60) / 3600;
			
			
			strRemainingT = String.format("%sh %sm %ss : to close", remainingHour, remainingMinute, remainingSecond);
			return strRemainingT;
		}		
	}
	
	public boolean isOpen(TimeZone tz) throws ParseException{
		String openTime = "08:00 am";
		SimpleDateFormat sDF = new SimpleDateFormat("hh:mm a");
		sDF.setTimeZone(tz);
//		Date openDate = sDF.parse(openTime);
		sDF.setTimeZone(TimeZone.getDefault());
		
		Calendar c = Calendar.getInstance(tz);

		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
				
		if (temp_day == Calendar.SUNDAY || temp_day == Calendar.SATURDAY){
			return false;
		} else if ( temp_hour < 8 && temp_ampm == Calendar.AM){
			return false;
		} else if ( temp_hour >= 5 && temp_ampm == Calendar.PM){
			return false;
		} else {
			return true;
		}		
	}
	public void alertToronto(){
		TimeZone tz = TimeZone.getTimeZone("GMT-05");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_toronto, "Toronto", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 0 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_toronto, "Toronto", "Close");
			} 
		}
	}
	public void alertNewyork(){
		TimeZone tz = TimeZone.getTimeZone("GMT-05");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 4 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_newyork, "New York", "Open");
			} else if (temp_hour == 4 && temp_second == 53 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_newyork, "New York", "Close");
			} 
		}
	}
	public void alertLondon(){
		TimeZone tz = TimeZone.getTimeZone("GMT+00");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_london, "London", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_london, "London", "Close");
			} 
		}
	}
	public void alertFrankFurt(){
		TimeZone tz = TimeZone.getTimeZone("GMT+01");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_frankfurt, "Frankfurt", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_frankfurt, "Frankfurt", "Close");
			} 
		}
	}
	public void alertJohannesburg(){
		TimeZone tz = TimeZone.getTimeZone("GMT+03");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_johannesburg, "Johannesburg", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_johannesburg, "Johannesburg", "Close");
			} 
		}
	}
	public void alertMoscow(){
		TimeZone tz = TimeZone.getTimeZone("GMT+03");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 4 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_moscow, "Moscow", "Open");
			} else if (temp_hour == 4 && temp_second == 53 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_moscow, "Moscow", "Close");
			} 
		}
	}
	public void alertDubai(){
		TimeZone tz = TimeZone.getTimeZone("GMT+04");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_dubai, "Dubai", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_dubai, "Dubai", "Close");
			} 
		}
	}
	public void alertHongkong(){
		TimeZone tz = TimeZone.getTimeZone("GMT+08");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_hongkong, "Hong Kong", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_hongkong, "Hong Kong", "Close");
			} 
		}
	}
	public void alertShanghai(){
		TimeZone tz = TimeZone.getTimeZone("GMT+08");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 4 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_shanghai, "ShangHai", "Open");
			} else if (temp_hour == 4 && temp_second == 53 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_shanghai, "ShangHai", "Close");
			} 
		}
	}
	public void alertTokyo(){
		TimeZone tz = TimeZone.getTimeZone("GMT+09");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_tokyo, "Tokyo", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_tokyo, "Tokyo", "Close");
			} 
		}
	}
	public void alertSydney(){
		TimeZone tz = TimeZone.getTimeZone("GMT+11");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 0 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_sydney, "Sydney", "Open");
			} else if (temp_hour == 4 && temp_second == 57 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_sydney, "Sydney", "Close");
			} 
		}
	}
	public void alertWellington(){
		TimeZone tz = TimeZone.getTimeZone("GMT+11");
		Calendar c = Calendar.getInstance(tz);
		int temp_second = c.get(Calendar.SECOND);
		int temp_minute = c.get(Calendar.MINUTE);
		int temp_hour = c.get(Calendar.HOUR);
		int temp_ampm = c.get(Calendar.AM_PM);
		int temp_day = c.get(Calendar.DAY_OF_WEEK);
		if (temp_day != Calendar.SUNDAY && temp_day != Calendar.SATURDAY) {
			if (temp_hour == 8 && temp_second == 4 && temp_minute == 0 && temp_ampm == Calendar.AM) {
				playSound(R.raw.alarm_wellington, "Wellington", "Open");
			} else if (temp_hour == 4 && temp_second == 53 && temp_minute == 59 && temp_ampm == Calendar.PM){
				playSound(R.raw.alarm_wellington, "Wellington", "Close");
			} 
		}
	}

	public void playSound(int id, String cityName, String strState){
		Uri path = Uri.parse("android.resource://com.fxgizmob/" + id);
		
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(getApplicationContext())
        .setSmallIcon(R.drawable.app_logo)
        .setContentText(String.format("%s Market at %s", strState, cityName))
        .setSound(path)
        .setVibrate(new long[] { 1000, 1000})
        .setContentTitle("Alert");
       
	      Notification notification = builder.build();
	      NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
	      nm.notify(1410 + id, notification);
	}
	public void setCityTime() throws ParseException{
		
		initCityTime();
		
		TimeZone tz_toronto = TimeZone.getTimeZone("GMT-05");		
		time_toronto.setText(convertTimeZone(tz_toronto));
		day_toronto.setText(setDayofWeek(tz_toronto));
		alertToronto();
		
		if (isOpen(tz_toronto)){
			name_toronto.setTextColor(getResources().getColor(R.color.text_open));
			time_toronto.setTextColor(getResources().getColor(R.color.text_open));
//			btn_toronto.setBackgroundResource(R.drawable.flag_open_toronto);
		}else{
			name_toronto.setTextColor(getResources().getColor(R.color.text_color));
			time_toronto.setTextColor(getResources().getColor(R.color.text_color));
//			btn_toronto.setBackgroundResource(R.drawable.flag_close_toronto);
		}
		TimeZone tz_newyork = TimeZone.getTimeZone("GMT-05");
		time_newyork.setText(convertTimeZone(tz_newyork));
		day_newyork.setText(setDayofWeek(tz_newyork));
		alertNewyork();
		
		if (isOpen(tz_newyork)){
			name_newyork.setTextColor(getResources().getColor(R.color.text_open));
			time_newyork.setTextColor(getResources().getColor(R.color.text_open));
//			btn_newyork.setBackgroundResource(R.drawable.flag_open_newyork);
		}else{
			name_newyork.setTextColor(getResources().getColor(R.color.text_color));
			time_newyork.setTextColor(getResources().getColor(R.color.text_color));
//			btn_newyork.setBackgroundResource(R.drawable.flag_close_newyork);
		}

		TimeZone tz_london = TimeZone.getTimeZone("GMT+00");
		time_london.setText(convertTimeZone(tz_london));
		day_london.setText(setDayofWeek(tz_london));
		alertLondon();
		
		if (isOpen(tz_london)){
			name_london.setTextColor(getResources().getColor(R.color.text_open));
			time_london.setTextColor(getResources().getColor(R.color.text_open));
//			btn_london.setBackgroundResource(R.drawable.flag_open_london);
		}else{
			name_london.setTextColor(getResources().getColor(R.color.text_color));
			time_london.setTextColor(getResources().getColor(R.color.text_color));
//			btn_london.setBackgroundResource(R.drawable.flag_close_london);
		}

		TimeZone tz_frankfurt = TimeZone.getTimeZone("GMT+01");
		time_frankfurt.setText(convertTimeZone(tz_frankfurt));
		day_frankfurt.setText(setDayofWeek(tz_frankfurt));
		alertFrankFurt();
		
		if (isOpen(tz_frankfurt)){
			name_frankfurt.setTextColor(getResources().getColor(R.color.text_open));
			time_frankfurt.setTextColor(getResources().getColor(R.color.text_open));
//			btn_frankfurt.setBackgroundResource(R.drawable.flag_open_frankfurt);
		}else{
			name_frankfurt.setTextColor(getResources().getColor(R.color.text_color));
			time_frankfurt.setTextColor(getResources().getColor(R.color.text_color));
//			btn_frankfurt.setBackgroundResource(R.drawable.flag_close_frankfurt);
		}

		TimeZone tz_johannesburg = TimeZone.getTimeZone("GMT+03");
		time_johannesburg.setText(convertTimeZone(tz_johannesburg));
		day_johannesburg.setText(setDayofWeek(tz_johannesburg));
		alertJohannesburg();
		if (isOpen(tz_johannesburg)){
			name_johannesburg.setTextColor(getResources().getColor(R.color.text_open));
			time_johannesburg.setTextColor(getResources().getColor(R.color.text_open));
//			btn_johannesburg.setBackgroundResource(R.drawable.flag_open_johannesburg);
		}else{
			name_johannesburg.setTextColor(getResources().getColor(R.color.text_color));
			time_johannesburg.setTextColor(getResources().getColor(R.color.text_color));
//			btn_johannesburg.setBackgroundResource(R.drawable.flag_close_johannesburg);
		}

		TimeZone tz_moscow = TimeZone.getTimeZone("GMT+03");
		time_moscow.setText(convertTimeZone(tz_moscow));
		day_moscow.setText(setDayofWeek(tz_moscow));
		alertMoscow();
		
		if (isOpen(tz_moscow)){
			name_moscow.setTextColor(getResources().getColor(R.color.text_open));
			time_moscow.setTextColor(getResources().getColor(R.color.text_open));
//			btn_moscow.setBackgroundResource(R.drawable.flag_open_moscow);
		}else{
			name_moscow.setTextColor(getResources().getColor(R.color.text_color));
			time_moscow.setTextColor(getResources().getColor(R.color.text_color));
//			btn_moscow.setBackgroundResource(R.drawable.flag_close_moscow);
		}

		TimeZone tz_dubai = TimeZone.getTimeZone("GMT+04");
		time_dubai.setText(convertTimeZone(tz_dubai));
		day_dubai.setText(setDayofWeek(tz_dubai));
		alertDubai();
		
		if (isOpen(tz_dubai)){
			name_dubai.setTextColor(getResources().getColor(R.color.text_open));
			time_dubai.setTextColor(getResources().getColor(R.color.text_open));
//			btn_dubai.setBackgroundResource(R.drawable.flag_open_dubai);
		}else{
			name_dubai.setTextColor(getResources().getColor(R.color.text_color));
			time_dubai.setTextColor(getResources().getColor(R.color.text_color));
//			btn_dubai.setBackgroundResource(R.drawable.flag_close_dubai);
		}

		TimeZone tz_hongkong = TimeZone.getTimeZone("GMT+08");
		time_hongkong.setText(convertTimeZone(tz_hongkong));
		day_hongkong.setText(setDayofWeek(tz_hongkong));
		alertHongkong();
		
		if (isOpen(tz_hongkong)){
			name_hongkong.setTextColor(getResources().getColor(R.color.text_open));
			time_hongkong.setTextColor(getResources().getColor(R.color.text_open));
//			btn_hongkong.setBackgroundResource(R.drawable.flag_open_hongkong);
		}else{
			name_hongkong.setTextColor(getResources().getColor(R.color.text_color));
			time_hongkong.setTextColor(getResources().getColor(R.color.text_color));
//			btn_hongkong.setBackgroundResource(R.drawable.flag_close_hongkong);
		}

		TimeZone tz_shanghai = TimeZone.getTimeZone("GMT+08");
		time_shanghai.setText(convertTimeZone(tz_shanghai));
		day_shanghai.setText(setDayofWeek(tz_shanghai));
		alertShanghai();
		
		if (isOpen(tz_shanghai)){
			name_shanghai.setTextColor(getResources().getColor(R.color.text_open));
			time_shanghai.setTextColor(getResources().getColor(R.color.text_open));
//			btn_shanghai.setBackgroundResource(R.drawable.flag_open_shanghai);
		}else{
			name_shanghai.setTextColor(getResources().getColor(R.color.text_color));
			time_shanghai.setTextColor(getResources().getColor(R.color.text_color));
//			btn_shanghai.setBackgroundResource(R.drawable.flag_close_shanghai);
		}

		TimeZone tz_tokyo = TimeZone.getTimeZone("GMT+09");
		time_tokyo.setText(convertTimeZone(tz_tokyo));
		day_tokyo.setText(setDayofWeek(tz_tokyo));
		alertTokyo();
		
		if (isOpen(tz_tokyo)){
			name_tokyo.setTextColor(getResources().getColor(R.color.text_open));
			time_tokyo.setTextColor(getResources().getColor(R.color.text_open));
//			btn_tokyo.setBackgroundResource(R.drawable.flag_open_tokyo);
		}else{
			name_tokyo.setTextColor(getResources().getColor(R.color.text_color));
			time_tokyo.setTextColor(getResources().getColor(R.color.text_color));
//			btn_tokyo.setBackgroundResource(R.drawable.flag_close_tokyo);
		}

		TimeZone tz_sydney = TimeZone.getTimeZone("GMT+11");
		time_sydney.setText(convertTimeZone(tz_sydney));
		day_sydney.setText(setDayofWeek(tz_sydney));
		alertSydney();
		
		if (isOpen(tz_sydney)){
			name_sydney.setTextColor(getResources().getColor(R.color.text_open));
			time_sydney.setTextColor(getResources().getColor(R.color.text_open));
			btn_sydney.setBackgroundResource(R.drawable.flag_open_sydney);
//		}else{
			name_sydney.setTextColor(getResources().getColor(R.color.text_color));
			time_sydney.setTextColor(getResources().getColor(R.color.text_color));
//			btn_sydney.setBackgroundResource(R.drawable.flag_close_sydney);
		}

		TimeZone tz_wellington = TimeZone.getTimeZone("GMT+11");
		time_wellington.setText(convertTimeZone(tz_wellington));
		day_wellington.setText(setDayofWeek(tz_wellington));
		alertWellington();
		
		if (isOpen(tz_wellington)){
			name_wellington.setTextColor(getResources().getColor(R.color.text_open));
			time_wellington.setTextColor(getResources().getColor(R.color.text_open));
//			btn_wellington.setBackgroundResource(R.drawable.flag_open_wellington);
		}else{
			name_wellington.setTextColor(getResources().getColor(R.color.text_color));
			time_wellington.setTextColor(getResources().getColor(R.color.text_color));
//			btn_wellington.setBackgroundResource(R.drawable.flag_close_wellington);
		}
	}
	
	public String getDayofWeek(int day) {
		String dayName = "";
		switch (day){
		case Calendar.SUNDAY:
			dayName = "SUN";
			break;
		case Calendar.MONDAY:
			dayName = "MON";
			break;
		case Calendar.TUESDAY:
			dayName = "TUE";
			break;
		case Calendar.WEDNESDAY:
			dayName = "WED";
			break;
		case Calendar.THURSDAY:
			dayName = "THU";
			break;
		case Calendar.FRIDAY:
			dayName = "FRI";
			break;
		case Calendar.SATURDAY:
			dayName = "SAT";
			break;
		default:
			break;
		}
		return dayName;
		
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
	
	public void alertime(){
		
	}
	
}
