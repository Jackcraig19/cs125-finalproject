package com.example.cs125_finalproject;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
    private static Map<String, String[]> responseMap = new HashMap<>();

    public static void setupMessages(Context context) {
        responseMap = new HashMap<>();
        Resources res = context.getResources();

        String[] contactPrompt = new String[Handler.contacts.size()];
        for (int i = 0; i < Handler.contacts.size(); i++) {
            contactPrompt[i] = Handler.contacts.get(i).getName().split(" ")[0].toLowerCase();
        }

        //Filling map from /res/values/strings
        for (String s : res.getStringArray(R.array.greet_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.greet_response));
        }
        for (String s : res.getStringArray(R.array.farewell_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.farewell_response));
        }
        for (String s : res.getStringArray(R.array.animal_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.animal_response));
        }
        for (String s : res.getStringArray(R.array.family_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.family_response));
        }
        for (String s : res.getStringArray(R.array.cs_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.cs_response));
        }
        for (String s : res.getStringArray(R.array.no_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.no_response));
        }
        for (String s : res.getStringArray(R.array.yes_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.yes_response));
        }
        for (String s : res.getStringArray(R.array.curse_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.curse_response));
        }
        for (String s : res.getStringArray(R.array.bot_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.bot_response));
        }
        for (String s : contactPrompt) {
            responseMap.put(s, res.getStringArray(R.array.contact_response));
        }
        for (String s : res.getStringArray(R.array.plan_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.plan_response));
        }
    }

    public static String getResponse(String message, String name) {
        String[] msgArray = message.split(" ");
        StringBuilder toSend = new StringBuilder();
        for (String s : msgArray) {
            String prompt = s.toLowerCase().replaceAll("\\W","");
            String[] r = responseMap.get(prompt);
            if (r != null) {
                String toAppend = r[(int)(Math.random() * r.length)];
                toSend.append(toAppend.replace("/n/", name).replace("/p/", prompt));
                toSend.append(" ");
            }
        }
        if (toSend.length() == 0) {
            toSend.append("Sorry, can't talk right now.");
        }
        return toSend.toString();
    }
}
