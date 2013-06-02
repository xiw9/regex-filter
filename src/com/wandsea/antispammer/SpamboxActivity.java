package com.wandsea.antispammer;

import java.util.ArrayList;
import java.util.HashMap;

import com.wandsea.antispammer.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class SpamboxActivity extends Activity {
    /** Called when the activity is first created. */
	SimpleAdapter listItemAdapter = null;
	ListView list = null;
    ArrayList<HashMap<String, Object>> listItem = null; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spambox);

        
        list = (ListView) findViewById(R.id.spambox_listView1); 
        listItem = new ArrayList<HashMap<String, Object>>();
        listItemAdapter = new SimpleAdapter(this,listItem,
                R.layout.spambox_list, 
                new String[] {"line1","line2"},   
                new int[] {R.id.spambox_list_textView1,R.id.spambox_list_textView2}  
            ); 
        
        ArrayList<HashMap<String, Object>> newItem = SmsFilter.getspam(getApplicationContext());
        for(int i=0;i<newItem.size();i++)  
            listItem.add(newItem.get(i));  
        list.setAdapter(listItemAdapter);  
        
        list.setOnItemLongClickListener(new OnItemLongClickListener() {            	
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
	            AlertDialog dlg=new AlertDialog.Builder(SpamboxActivity.this)  
	            .setMessage("Remove this message from Spambox?")    
	            .setPositiveButton("Remove",   
	            new DialogInterface.OnClickListener() {		                      
	            	public void onClick(DialogInterface dialog, int which) { 
	            		SmsFilter.deletespam(getApplicationContext(), (Integer) listItem.get(arg2).get("id"));
	                    ArrayList<HashMap<String, Object>> newItem = SmsFilter.getspam(getApplicationContext());
	                    listItem.clear();
	                    for(int i=0;i<newItem.size();i++)  
	                        listItem.add(newItem.get(i));  
	                    listItemAdapter.notifyDataSetChanged();
	                    
	            	}  
	            }).setNegativeButton("Nope",null).create();  
	            dlg.show();
	            
	            return false;				
			}         
        });      
        
    }
    
}  