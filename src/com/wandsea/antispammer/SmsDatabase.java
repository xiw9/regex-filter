package com.wandsea.antispammer;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

 
public class SmsDatabase extends SQLiteOpenHelper {     
	SmsDatabase(Context context, String name, CursorFactory cursorFactory, int version) {     
		super(context, name, cursorFactory, version);     
    }     
     
	@Override    
	public void onCreate(SQLiteDatabase db) {     
        Log.e("SQLiteHelper", "SQLitehelper onCreate!");  
        try {  
            db.execSQL("Create TABLE TFilter( " +  
            			"ID integer Primary Key AUTOINCREMENT, " +  
            			"Filter varchar(255), " +  
            			"Active integer " +
            			")");  
            db.execSQL("Create TABLE SpamSMS( " +  
        			"ID integer Primary Key AUTOINCREMENT, " +  
        			"Phonenum varchar(48), " +  
        			"SMS varchar(512), " +
        			"Time TIMESTAMP default (datetime('now', 'localtime')) " +
        			")");  
            Log.e("SQLiteHelper", "createDataTable OK!");  
        } catch (SQLException se) {  
            se.printStackTrace();  
        }    
	}     
	     
	@Override    
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {     
		// TODO 更改数据库版本的操作     
	}     
	     
	 @Override    
	public void onOpen(SQLiteDatabase db) {     
		 super.onOpen(db);       
		 // TODO 每次成功打开数据库后首先被执行     
	}     
 } 