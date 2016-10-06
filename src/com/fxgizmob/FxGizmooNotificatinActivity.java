package com.fxgizmob;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.util.GlobalVariables;


public class FxGizmooNotificatinActivity extends Activity {

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fxgizmo_notification);
	      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.notification_item, GlobalVariables.message_list);
	      
	      ListView listView = (ListView) findViewById(R.id.notification_list);
	      adapter.notifyDataSetChanged();
	      listView.setAdapter(adapter);
		
	}
	public static void receiveMessage() {
        
		ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
		query.orderByAscending("createdAt");
		query.setLimit(10000) ;
	}	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
}
