package com.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.fxgizmob.LoginActivity;
import com.fxgizmob.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;
import com.parse.ParseUser;










import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
//import library.MyLifecycleHandler;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationReceiver extends ParsePushBroadcastReceiver{

	public static final String PARSE_EXTRA_DATA_KEY         =   "com.parse.Data";
	public static final String PARSE_JSON_CHANNEL_KEY       =   "com.parse.Channel";
	
	Context context;
	
	
	@Override
	public void onPushReceive(final Context context, final Intent intent) {
        super.onPushReceive(context, intent);
        if (intent == null)
            return ;
        this.context = context;
    //  Get Push Notification
		String pushData = intent.getExtras().getString(PARSE_EXTRA_DATA_KEY);
        String message = getData(pushData);
        
        GlobalVariables.message_list.add(message);
        Log.d("Push Notification", message);
        
     // Add custom intent
        Intent cIntent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                                      cIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
     // Create custom notification
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context)
          .setSmallIcon(R.drawable.app_logo)
          .setContentText(message)
          .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
          .setVibrate(new long[] { 1000, 1000})
          .setContentTitle("Notification from Parse")
          .setContentIntent(pendingIntent);
         
        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1410, notification);
	}
	private String getData(String jsonData) {
	    // Parse JSON Data
		try {
//			System.out.println("JSON Data [" + jsonData + "]");
			JSONObject obj = new JSONObject(jsonData);
			
			return obj.getString("message");
		} catch (JSONException je) {
		    je.printStackTrace();		
		}
		return "";
	}
}