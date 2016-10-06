package com.util;

import java.util.ArrayList;
import java.util.List;

//import models.MyProjectListItem;

import com.util.PrefManager;
//import com.parse.ParseObject;
import com.parse.ParseUser;

import android.content.Context;

public class GlobalVariables {
	
	Context mContext;
	
	public static final int ACTION_GET_CATEGORY = 1001 ;
	
	public static final int ACTION_GET_VALIDATION_DATE = 3001 ;
	public static final int ACTION_GET_SUBCATEGORYLIST = 3002 ;
	public static final int ACTION_SAVE_IMAGE = 3003 ;
	
	
//	public static MyProjectListItem selected_MyProjectItem ;
//	public static List<ParseObject> array_category, array_CurrencyObject, array_Country ;
//	public static String[] array_Currency, array_CurrencySymbol ;
//	
//	public static ParseObject selectedProject, selectedCategory, selectedSubCategory ;
	public static ParseUser toUser ;
	
	public static String progressbar_text1 ;
	public static boolean f_NewMessageArrived ;
	public static boolean f_AppIsInBackground ;
	
	public static boolean f_loginState;
	
	
	public static boolean f_PushNotificationReceived ;
	public static boolean f_payStatement;
//	public static boolean 
	public static String strLoginName;
	public static String strPassword;
	
	public static ArrayList<String> message_list = new ArrayList<String>();
	
	// constructor
    public GlobalVariables(Context context){
        this.mContext = context;        
    }
    public static void readPreference(Context context){
    	GlobalVariables.strLoginName = PrefManager.readPrefString(context, "Loing_Username");
    	GlobalVariables.strPassword = PrefManager.readPrefString(context, "Password");
    }
    public static void SaveProfile(Context context){
		PrefManager.savePrefString(context, "Loing_Username", GlobalVariables.strLoginName);
		PrefManager.savePrefString(context, "Password", GlobalVariables.strPassword);
	}
}
