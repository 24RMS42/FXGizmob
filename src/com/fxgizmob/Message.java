package com.fxgizmob;

import java.util.Date;
import android.content.Context;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.util.GlobalFunction;


@ParseClassName("Chatting")
public class Message extends ParseObject {
    
    
    public ParseObject getFromUser() {
    	return getParseObject("user_from") ; 
    }
    
    public ParseObject getToUser() {
    	return getParseObject("user_to") ; 
    }
    
    public String getBody() {
    	return getString("body");
    }
    
    public String getCreatedDate(Context context) {
    	
    	Date date = getCreatedAt() ;  
    	return GlobalFunction.getTimeAgo(date, context) ;	
    }

    public void setBody(String body) {
        put("body", body);
    }
}
