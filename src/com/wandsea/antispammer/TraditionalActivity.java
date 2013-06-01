package com.wandsea.antispammer;

import java.util.ArrayList;

import com.wandsea.antispammer.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TraditionalActivity extends Activity {
    /** Called when the activity is first created. */
    ListView list = null;
    ArrayList<String> listItem=null;
    ArrayAdapter<String> adapter=null;
    
    int itemselect=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traditional);

             
        list = (ListView) findViewById(R.id.traditional_listView1); 
        listItem = SmsFilter.gettfilter(getApplicationContext()); 
        adapter = new ArrayAdapter<String>(this,
        	    android.R.layout.simple_list_item_single_choice, listItem);
        
        list.setItemsCanFocus(true);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(adapter);
        list.setItemChecked(0, true);
        
        list.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				if (arg2==0)
					return false;
	            AlertDialog dlg=new AlertDialog.Builder(TraditionalActivity.this)  
	            .setMessage("是否删除该条过滤器?")    
	            .setPositiveButton("确定",   
	            new DialogInterface.OnClickListener() {		                      
	            	public void onClick(DialogInterface dialog, int which) { 
	            		SmsFilter.modifytfilter(getApplicationContext(),listItem.get(arg2),"");
	            		listItem = SmsFilter.gettfilter(getApplicationContext());
	                    adapter = new ArrayAdapter<String>(TraditionalActivity.this,
	                    	    android.R.layout.simple_list_item_single_choice, listItem);           
	                    list.setItemsCanFocus(true);
	                    list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	                    list.setAdapter(adapter);
	                    list.setItemChecked(0, true);
	            	}  
	            }).setNegativeButton("取消",null).create();  
	            dlg.show();
				return false;
			}}
        );
        
        
        list.setOnItemClickListener(new ListView.OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				itemselect=arg2;
    			EditText edittext= (EditText)findViewById(R.id.traditional_editText1);
    	    	Button button1 = (Button) findViewById(R.id.traditional_button1);
    			if(itemselect==0){
    				button1.setText("Add");
    				edittext.setText("");
    			}else{
    				button1.setText("Edit");
    				edittext.setText(listItem.get(itemselect));
    			}				
			}

        	
        });

    	Button button1 = (Button) findViewById(R.id.traditional_button1);
    	button1.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View arg0) {
    			EditText edittext= (EditText)findViewById(R.id.traditional_editText1);
    			if(itemselect==0){
    				SmsFilter.createtfilter(getApplicationContext(),edittext.getText().toString());
    				edittext.setText("");
    			}else{
            		SmsFilter.modifytfilter(getApplicationContext(),listItem.get(itemselect),edittext.getText().toString());
    			}
        		listItem = SmsFilter.gettfilter(getApplicationContext());
                adapter = new ArrayAdapter<String>(TraditionalActivity.this,
                	    android.R.layout.simple_list_item_single_choice, listItem);           
                list.setItemsCanFocus(true);
                list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                list.setAdapter(adapter);
                list.setItemChecked(itemselect, true);
                
    		}
    	});
    	
    }
    
}  