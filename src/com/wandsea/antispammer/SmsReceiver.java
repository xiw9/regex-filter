package com.wandsea.antispammer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

 
public class SmsReceiver extends BroadcastReceiver {  
	@Override  
	public void onReceive(Context context, Intent intent) {  
		//---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";     
        String text = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str += "Spam from " + msgs[i].getOriginatingAddress();                     
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n"; 
                text+=msgs[i].getMessageBody().toString();
            }
            if (SmsFilter.isspam(context,msgs[0].getOriginatingAddress().toString(),text)){
            	abortBroadcast();
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
	}
}  