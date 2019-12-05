package com.example.cs125_finalproject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
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

    private String getResponse(String message) {
        String[] msgArray = message.split(" ");
        StringBuilder toSend = new StringBuilder();
        for (String s : msgArray) {
            String[] r = responseMap.get(s.toLowerCase().replaceAll("\\W",""));
            if (r != null) {
                toSend.append(r[(int)(Math.random() * r.length)]);
                toSend.append(" ");
            }
        }
        return toSend.toString();
    }
}
