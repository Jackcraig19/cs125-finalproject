package com.example.cs125_finalproject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
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

    public static String getResponse(String message, String name) {
        String[] msgArray = message.split(" ");
        return buildMessage(msgArray, name);
    }
    private static String buildMessage(String[] message, String name) {
        String thisAddition;
        for (int i = 0; i < message.length; i++) {
            String[] r = responseMap.get(message[i].toLowerCase().replaceAll("\\W", ""));
            if (r != null) {
                if (i + 1 < message.length)
                    message = Arrays.copyOfRange(message, i + 1, message.length);
                else
                    message = new String[0];
                thisAddition = r[(int) (Math.random() * r.length)];
                return thisAddition + " " + buildMessage(message, name);
            }
        }
        return "";
    }
}
