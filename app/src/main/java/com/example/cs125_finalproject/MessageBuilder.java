package com.example.cs125_finalproject;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
    private static Map<String, String[]> responseMap = new HashMap<>();
    private static Map<String, String[]> contactMap = new HashMap<>();

    // Called Whenever Handler.contacts is updated
    public static void setupContacts(Context context) {
        contactMap = new HashMap<>();
        Resources res = context.getResources();

        String[] contactPrompt = new String[Handler.contacts.size()];
        for (int i = 0; i < Handler.contacts.size(); i++) {
            contactPrompt[i] = Handler.contacts.get(i).getName().split(" ")[0].toLowerCase().trim();
        }
        for (String s : contactPrompt) {
            contactMap.put(s, res.getStringArray(R.array.contact_response));
        }
    }
    // Called once on app creation
    public static void setupMessages(Context context) {
        responseMap = new HashMap<>();
        Resources res = context.getResources();

        //Filling map from /res/values/strings
        responseMap.put("default", res.getStringArray(R.array.default_response));
        responseMap.put("question", res.getStringArray(R.array.question_response));
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
        for (String s : res.getStringArray(R.array.plan_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.plan_response));
        }
        for (String s : res.getStringArray(R.array.president_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.president_response));
        }
        for (String s : res.getStringArray(R.array.chad_prompt)) {
            responseMap.put(s, res.getStringArray(R.array.chad_response));
        }
    }

    public static String getResponse(String message, String name) {
        message = message.trim();
        String[] msgArray = message.split(" ");
        StringBuilder toSend = new StringBuilder();
        for (String s : msgArray) {
            String prompt = cleanUp(s);
            if (!appender(responseMap, toSend, prompt)) {
                appender(contactMap, toSend, prompt);
            }
        }
        if (toSend.length() != 0) {
            return toSend.toString().replaceAll("/n/", name);
        }
        if (message.length() > 0 && message.charAt(message.length() - 1) == '?') {
            String[] r = responseMap.get("question");
            if (r != null) {
                return r[(int) (Math.random() * r.length)];
            }
        }
        String[] r = responseMap.get("default");
        if (r != null) {
            return r[(int) (Math.random() * r.length)];
        }
        return "Error: no message built";
    }
    private static String cleanUp(String toClean) {
        toClean = toClean.toLowerCase();
        toClean = toClean.replaceAll("\\W","");
        toClean = toClean.replaceAll("!", "");
        toClean = toClean.replaceAll("\n", "");
        toClean = toClean.replaceAll("\t", "");
        return toClean;
    }
    private static boolean appender(Map<String, String[]> m, StringBuilder toSend, String prompt) {
        boolean appended = false;
        String[] r = m.get(prompt);
        if (r != null) {
            if (!toSend.toString().equals("")) {
                toSend.append("\n\n");
            }
            String toAppend = r[(int)(Math.random() * r.length)];
            toSend.append(toAppend.replace("/p/", prompt + " "));
            appended = true;
        }
        return appended;
    }
}
