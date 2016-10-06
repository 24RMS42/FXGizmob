package com.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.parse.ParseObject;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;

public class GlobalFunction {
	Context mContext;
	
	public static int getResourceID(Context _context, String fileName) {
    	int resID = _context.getResources().getIdentifier("@drawable/" + fileName, "drawable", _context.getPackageName());
		return resID ;
    }
	
	public static boolean isExpiredProject(ParseObject object) {
		
		Date created = object.getCreatedAt() ;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(created) ;
		cal.add(Calendar.DAY_OF_MONTH, 60);
		Date new_date = cal.getTime() ;
		Date cur_date = new Date() ;
		
		long different = cur_date.getTime() - new_date.getTime();
		
		if ( different > 0 )
			return true ;
		else
			return false ;
		
	}
	
	public static boolean isEndingSoonProject(ParseObject object) {
		
		Date created = object.getCreatedAt() ;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(created) ;
		cal.add(Calendar.DAY_OF_MONTH, 60);
		Date new_date = cal.getTime() ;		// 60 Days later from project Date
		Date cur_date = new Date() ;		// Current Date
		
		long different = (new_date.getTime() - cur_date.getTime() ) / 1000 ;
		
		if ( different > 0 && different <= 10*86400 )
			return true ;
		else
			return false ;
	}
	
	public static Typeface font_pacifico, font_Roboto_Italic ; 
	
    // constructor
    public GlobalFunction(Context context){
        this.mContext = context;
        
    }
    
    public static Boolean isNullString(String str) {
    	
    	if ( str == null || str.length() < 1 )
    		return true ;
    	
    	return false ;
    }
    
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(Date date, Context ctx) {

        if(date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int dim = getTimeDistanceInMinutes(time);

        String timeAgo = null;

        if (dim == 0) {
            timeAgo = "less than a minute" ;
        } else if (dim == 1) {
            return "1 minute" ;
        } else if (dim >= 2 && dim <= 44) {
            timeAgo = dim + " minutes" ;
        } else if (dim >= 45 && dim <= 89) {
            timeAgo = "about an hour" ;
        } else if (dim >= 90 && dim <= 1439) {
            timeAgo = "about " + (Math.round(dim / 60)) + " hours" ;
        } else if (dim >= 1440 && dim <= 2519) {
            timeAgo = "1 day" ;
        } else if (dim >= 2520 && dim <= 43199) {
            timeAgo = (Math.round(dim / 1440)) + " days" ;
        } else if (dim >= 43200 && dim <= 86399) {
            timeAgo = "about a month" ;
        } else if (dim >= 86400 && dim <= 525599) {
            timeAgo = (Math.round(dim / 43200)) + " months" ;
        } else if (dim >= 525600 && dim <= 655199) {
            timeAgo = "about a year" ;
        } else if (dim >= 655200 && dim <= 914399) {
            timeAgo = "over a year" ;
        } else if (dim >= 914400 && dim <= 1051199) {
            timeAgo = "almost 2 years" ;
        } else {
            timeAgo = "about " + (Math.round(dim / 525600)) + " years" ;
        }

        return timeAgo + " ago";
    }

    private static int getTimeDistanceInMinutes(long time) {
          long timeDistance = currentDate().getTime() - time;
          return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }
    
    public static void setAlignParam(View view, boolean direction) {
    	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
    	if ( direction )
    		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	else
    		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	
        view.setLayoutParams(params) ;
    }
    
    public static boolean isNumeric(String str)
    {
    	return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
    public static int getIdAssignedByR(Context pContext, String pIdString)
    {
        // Get the Context's Resources and Package Name
        Resources resources = pContext.getResources();
        String packageName  = pContext.getPackageName();

        // Determine the result and return it
        int result = resources.getIdentifier(pIdString, "id", packageName);
        return result;
    }
    
    public static ParseObject getObjectById(List<ParseObject> objects, String object_id) {
		
    	ParseObject result = null ;
    	
    	for ( int i = 0 ; i < objects.size() ; i++ ) {
    		ParseObject temp = objects.get(i) ;
    		if ( temp.getObjectId().equals(object_id) ) {
    			result = temp ;
    			return result ;
    		}
    	}
    	
    	return result;
    	
    }
}
