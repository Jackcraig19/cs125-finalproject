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

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
<<<<<<< HEAD
    private static Map<String, String[]> responseMap = new HashMap<>();

    public static void setupMessages() {
        String[] greetingsList = {"hello!", "hi.", "howdy ;)", "greetings...", "hey!"};
        for (String s1 : greetingsList)
            responseMap.put(s1.replaceAll("\\W", ""), greetingsList);
        String[] salutationsList = {"bye!", "goodbye.", "peace :P", "adios <3"};
        for (String s1 : salutationsList)
            responseMap.put(s1.replaceAll("\\W", ""), salutationsList);
        String[] catList = {"I love cats!", "I hate cats."};
        responseMap.put("cat", catList);
        responseMap.put("cats", catList);
        String[] jackList = {"Jack is great!", "Jack is the chief of police."};
        responseMap.put("jack", jackList);
        String[] familyList = {"The family is great!", "Everyone is doing well.", "Don't talk to me about my family."};
        responseMap.put("family", familyList);
        String[] csList = {"Did you forget a semicolon?", "Idk, ask on the forum.", "Maybe you have a typo.", "Did you try rebooting?"};
        String[] csPrompts = {"cs125", "cs", "computer", "code", "programming", "app", "program"};
        for (String s : csPrompts)
            responseMap.put(s, csList);
        String[] noList = {"Ok.", "hmmm...", "Interesting", "Alright then."};
        responseMap.put("no", noList);
        String[] yesList = {"OK!", "Awesome!", "Yeah!!", "okie doke", "sounds good", "yes??!!"};
        responseMap.put("yes", yesList);
    }
=======
>>>>>>> 63f0973f9395efe07899400a579dc3a33598bab9

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
                Log.d("SmsReceiverDebug", "SmsReceiverDebug: " + msgs[0].getMessageBody() + " from " + msgs[0].getOriginatingAddress());
                for (Contact toSend : Handler.contacts) {
                    if (toSend.getState()
                    && (msgs[0].getOriginatingAddress().equals("+" + toSend.getNumber())
                    || msgs[0].getOriginatingAddress().equals("+1" + toSend.getNumber())
                    || msgs[0].getOriginatingAddress().equals(toSend.getNumber()))) {
                        sendSMSMessage(msgs[0].getMessageBody(), toSend);
                        break;
                    }
                }
            }
        }
    }
    private void sendSMSMessage(String msgToRespond, Contact toSend) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String text = MessageBuilder.getResponse(msgToRespond, toSend.getName()) + " (Test)";
            smsManager.sendTextMessage(toSend.getNumber(), null, text, null, null);
        } catch (Exception e) {
            Log.e(TAG, "[Error]", e);
        }
    }
}
