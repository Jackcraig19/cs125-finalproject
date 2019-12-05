package com.example.cs125_finalproject;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.lang.annotation.Target;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            msgs = new SmsMessage[pdus.length];
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            for (int i = 0; i < pdus.length; i++) {
                if (isVersionM) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                Log.d(TAG, "onReceive: " + strMessage);
                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();

                for (Contact toSend : Handler.contacts) {
                    if (msgs[i].getOriginatingAddress().equals("+" + toSend.getNumber())
                    || msgs[i].getOriginatingAddress().equals("+1" + toSend.getNumber())) {
                        sendSMSMessage();
                        break;
                    }
                }
                break;
            }
        }
    }
    private void sendSMSMessage() {
        for (Contact toSend : Handler.contacts) {
            if (toSend.getState()) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(toSend.getNumber(), null, "Auto Reply", null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
