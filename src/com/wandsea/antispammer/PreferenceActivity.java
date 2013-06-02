package com.wandsea.antispammer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;



public class PreferenceActivity extends android.preference.PreferenceActivity implements
OnSharedPreferenceChangeListener{
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	addPreferencesFromResource(R.layout.preferences);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		boolean Preferences_CheckBox2 = prefs.getBoolean("Preferences_CheckBox2", false);
	    if(Preferences_CheckBox2)
	    	SmsFilter.deleteallspam(getApplicationContext());
    }
}
