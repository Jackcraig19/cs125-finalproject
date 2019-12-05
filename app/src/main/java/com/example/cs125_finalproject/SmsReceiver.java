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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
    private static Map<String, String[]> responseMap = new HashMap<>();

    public static void setupMessages() {
        String[] greetingsList = {"hello", "hi", "howdy", "greetings"};
        for (String s1 : greetingsList)
            responseMap.put(s1, greetingsList);
        String[] salutationsList = {"bye", "goodbye", "peace", "adios"};
        for (String s1 : salutationsList)
            responseMap.put(s1, salutationsList);
        String[] catList = {"I love cats", "I hate cats"};
        responseMap.put("cat", catList);
        responseMap.put("cats", catList);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        //String strMessage = "";
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            msgs = new SmsMessage[pdus.length];
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            if (pdus.length > 0) {
                if (isVersionM) {
                    msgs[0] = SmsMessage.createFromPdu((byte[]) pdus[0], format);
                } else {
                    msgs[0] = SmsMessage.createFromPdu((byte[]) pdus[0]);
                }
                //strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                //strMessage += " :" + msgs[i].getMessageBody() + "\n";
                Log.d("SmsReceiverDebug", "SmsReceiverDebug: " + msgs[0].getMessageBody() + " from " + msgs[0].getOriginatingAddress());
                //Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                for (Contact toSend : Handler.contacts) {
                    if (msgs[0].getOriginatingAddress().equals("+" + toSend.getNumber())
                    || msgs[0].getOriginatingAddress().equals("+1" + toSend.getNumber())
                    || msgs[0].getOriginatingAddress().equals(toSend.getNumber())) {
                        sendSMSMessage(msgs[0].getMessageBody());
                        break;
                    }
                }
            }
        }
    }
    private void sendSMSMessage(String msgToRespond) {
        for (Contact toSend : Handler.contacts) {
            if (toSend.getState()) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    String text = getResponse(msgToRespond) + " (Test)";
                    smsManager.sendTextMessage(toSend.getNumber(), null, text, null, null);
                } catch (Exception e) {
                    Log.e(TAG, "[Error]", e);
                    e.printStackTrace();
                }
            }
        }
    }
    private String getResponse(String message) {
        String[] msgArray = message.split(" ");
        return getResponseHelper(msgArray);

    }
    private String getResponseHelper(String[] message) {
        String thisAddition;
        for (int i = 0; i < message.length; i++) {
            String[] r = responseMap.get(message[i].toLowerCase().replaceAll("\\W",""));
            if (r != null) {
                if (i + 1 < message.length)
                    message = Arrays.copyOfRange(message,i + 1, message.length);
                else
                    message = new String[0];
                thisAddition = r[(int)(Math.random() * r.length)];
                return thisAddition + " " + getResponseHelper(message);
            }
        }
        return "";
    }
}
