package com.wandsea.antispammer;

import com.wandsea.antispammer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	LinearLayout button1 = (LinearLayout) findViewById(R.id.main_linearlayout1);
    	button1.setOnClickListener(new LinearLayout.OnClickListener() {
    		public void onClick(View arg0) {
    	    	Intent newIntent = new Intent(MainActivity.this,SpamboxActivity.class);
    	    	startActivity(newIntent);
    		}
    	});
    	
    	
    	LinearLayout button2 = (LinearLayout) findViewById(R.id.main_linearlayout2);
    	button2.setOnClickListener(new LinearLayout.OnClickListener() {
    		public void onClick(View arg0) {
    	    	Intent newIntent = new Intent(MainActivity.this,TraditionalActivity.class);
    	    	startActivity(newIntent);
    		}
    	});
    	   	
    	LinearLayout button4 = (LinearLayout) findViewById(R.id.main_linearlayout4);
    	button4.setOnClickListener(new LinearLayout.OnClickListener() {
    		public void onClick(View arg0) {
    	    	Intent newIntent = new Intent(MainActivity.this,PreferenceActivity.class);
    	    	startActivity(newIntent);
    		}
    	});
    	
    	LinearLayout button5 = (LinearLayout) findViewById(R.id.main_linearlayout5);
    	button5.setOnClickListener(new LinearLayout.OnClickListener() {
    		public void onClick(View arg0) {
    	    	Intent newIntent = new Intent(MainActivity.this,AboutActivity.class);
    	    	startActivity(newIntent);
    		}
    	});
    	
    }
    
}