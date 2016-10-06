package com.util;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.util.Log;

/**
 * 
 * 
 * @author zheng
 */
public class PrefManager {
	
	public static final int PREF_MODE 				= Context.MODE_PRIVATE;
	public static final String PREF_NAME 			= "FXGizmo";
	
	
	public static void savePrefString(Context context, String key, String val)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){	
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString(key, val);
			editor.commit();  
		}
	}
	
	public static void savePrefBoolean(Context context, String key, boolean val)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){	
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putBoolean(key, val);
			editor.commit();  
		}
	}
	
	public static void savePrefInt(Context context, String key, int val)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){	
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putInt(key, val);
			editor.commit();  
		}
	}
	
	public static void savePrefStringArray(Context context, String key, Set<String> val)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){	
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putStringSet(key, val);
			editor.commit();  
		}
	}
	public static Set<String> readPrefStringArray(Context context,String key)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		Set<String> result = new HashSet<String>();
		SharedPreferences mySharedPreferences =context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){
			result = mySharedPreferences.getStringSet(key, new HashSet<String>());
		}
		return result;
	}
	public static void savePrefIntArray(Context context, String key, Set<Integer> val)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		SharedPreferences mySharedPreferences = context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){	
			Set<String> strVal = new HashSet<String>();
			for(Integer i : val)
				strVal.add(""+i.intValue());
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putStringSet(key, strVal);
			editor.commit();  
		}
	}
	
	public static Set<Integer> readPrefIntArray(Context context,String key)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		Set<String> result = new HashSet<String>();
		SharedPreferences mySharedPreferences =context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){
			result = mySharedPreferences.getStringSet(key, new HashSet<String>());
		}
		Set<Integer> lResult = new HashSet<Integer>();
		for(String s : result){
			lResult.add(Integer.parseInt(s));
		}
		return lResult;
	}	
	public static String readPrefString(Context context,String key)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		String result = "";
		SharedPreferences mySharedPreferences =context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){
			result = mySharedPreferences.getString(key, "");
		}
		return result;
	}
	
	public static Boolean readPrefBoolean(Context context, String key)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		Boolean result = false;
		SharedPreferences mySharedPreferences =context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){
			result = mySharedPreferences.getBoolean(key, false);
		}
		return result;
	}
	
	public static Boolean readPrefBoolean(Context context, String key, boolean defaultValue)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		Boolean result = false;
		SharedPreferences mySharedPreferences =context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){
			result = mySharedPreferences.getBoolean(key, defaultValue);
		}
		return result;
	}
	
	public static int readPrefInt(Context context, String key)
	{
		int mode = PREF_MODE;
		String name = PREF_NAME;
		int result = 0;
		SharedPreferences mySharedPreferences =context.getSharedPreferences(name, mode);
		if(mySharedPreferences != null){
			result = mySharedPreferences.getInt(key, 0);
		}
		return result;
	}
	
	public static boolean isDoodlesAvailable(Context context, int page, int index)
	{
//		return true;/
		return ( page == 0 || readPrefBoolean(context, "Doodle-" + page + "-" + index));
	}
}
