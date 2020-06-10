package com.example.readcsv.SendSMSBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();

        if (bundle!=null){

            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            // creating PDU (Protocol Data Unit ) which is protocol for transferring message.

            SmsMessage sms =  SmsMessage.createFromPdu((byte[])pdus[0]);
            // From PDU we get all object and SMS message using following line of code.
            String massage = sms.getMessageBody();
            if (massage.equals("ActiveRingerMode")){
                AudioManager aManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }

        }

    }
}
