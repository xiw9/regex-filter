package com.wandsea.antispammer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;



public class SmsFilter{
	
	public static boolean iscontact(Context context, String phonenum){
        ContentResolver resolver =context.getContentResolver();
        Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phonenum));
        Cursor cursor = resolver.query(lookupUri,null, null, null, null);
        if(cursor.moveToNext()){ 	 
        	Log.e("Contact",cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME)));
        	cursor.close();
        	return true;
        }
        cursor.close();     
        return false;
	}
	
	public static boolean isspam(Context c,String phonenum,String text){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
		boolean Preferences_CheckBox1 = prefs.getBoolean("Preferences_CheckBox1", true);
		
		if(!Preferences_CheckBox1)
			return false;
		
		
		if (istfilter(c,phonenum,text))
			return true;
			
		return false;
	}
	
	public static void logspam(Context c,String phonenum,String text){
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
		boolean Preferences_CheckBox2 = prefs.getBoolean("Preferences_CheckBox2", false);
	    if(Preferences_CheckBox2)
	    	return;
	    
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getWritableDatabase();
	    
	    ContentValues v = new ContentValues(); 
	    v.put("Phonenum", phonenum);
	    v.put("SMS", text);
	        
        db.insert("SpamSMS", null, v);     
	    db.close();

	}
	public static ArrayList<HashMap<String, Object>> getspam(Context c){
		ArrayList<HashMap<String, Object>> s = new ArrayList<HashMap<String, Object>>();
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getReadableDatabase();  
	    Cursor result=db.rawQuery("SELECT ID, Phonenum, SMS, Time FROM SpamSMS", null); 
	    result.moveToLast(); 
	    while (!result.isBeforeFirst()) { 
	    	HashMap<String, Object> m=new HashMap<String, Object>();
	    	m.put("id", result.getInt(0));
	    	m.put("line1", "From: "+result.getString(1));
	    	m.put("line2", "Date: "+result.getString(3)+"\n"+result.getString(2));
	    	s.add(m);
	        result.moveToPrevious();
	    }
	    result.close(); 
	    db.close();
		return s;
	}
	
	public static void deletespam(Context c,int id){
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getWritableDatabase();     
      	db.delete("SpamSMS", "ID=?", new String[]{String.valueOf(id)});
        db.close();
	}
	
	public static void deleteallspam(Context c){
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getWritableDatabase();     
      	db.delete("SpamSMS", "ID!=?", new String[]{String.valueOf(0)});
        db.close();
	}
	
	
	public static void createtfilter(Context c,String filter){
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getWritableDatabase();
	    
	    ContentValues v = new ContentValues(); 
	    v.put("Filter", filter);
	    v.put("Active", 1);
	        
        db.insert("TFilter", null, v); 
	    db.close();
	}
	

	
	public static void modifytfilter(Context c,String oldfilter,String newfilter){
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getWritableDatabase();

	    ContentValues values = new ContentValues();       
        values.put("Filter", newfilter);       
      
        if (newfilter.equals("")){
        	db.delete("TFilter", "Filter=?", new String[]{oldfilter});
        }else{
        	db.update("TFilter", values, "Filter=?", new String[]{oldfilter});   
        }
        
        db.close();
	}
	
	public static ArrayList<String> gettfilter(Context c){
		ArrayList<String> s = new ArrayList<String>();
		s.add("New Filter");
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getReadableDatabase();  
	    Cursor result=db.rawQuery("SELECT ID, Filter, Active FROM TFilter", null); 
	    result.moveToFirst(); 
	    while (!result.isAfterLast()) { 
	    	s.add(result.getString(1));
	        result.moveToNext(); 
	    }
	    result.close(); 
	    db.close();
		return s;
	}
	
	public static boolean istfilter(Context c,String phonenum,String text){
	    SmsDatabase sqlHelper;  
	    SQLiteDatabase db;  
	    sqlHelper = new SmsDatabase(c, "antispammer.db", null, 1);  
	    db = sqlHelper.getReadableDatabase();  
	    Cursor result=db.rawQuery("SELECT ID, Filter, Active FROM TFilter", null); 
	    result.moveToFirst(); 
	    while (!result.isAfterLast()) { 
	        String filter=result.getString(1); 
	        int active=result.getInt(2); 
	        if (active!=0){
	        	Pattern p=Pattern.compile(filter);
	        	Matcher m=p.matcher(text);
	        	while (m.find()){
	        		logspam(c,phonenum,text);
	        		Log.e("AntiSpammer-tfilter",m.group());
	        	    result.close(); 
	        	    db.close();
	        		return true;
	        	}
	        	p=Pattern.compile(filter);
	        	m=p.matcher(phonenum);
	        	while (m.find()){
	        		logspam(c,phonenum,text);
	        		Log.e("AntiSpammer-tfilter-phone",m.group());
	        	    result.close(); 
	        	    db.close();
	        		return true;
	        	}
	        }
	        result.moveToNext(); 
	      } 
	    result.close(); 
	    db.close();
	    return false;
	}
	
		
}