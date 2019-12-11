package com.example.cs125_finalproject;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
    private static Map<String, String[]> responseMap = new HashMap<>();

    public static void setupMessages() {
        String[] greetingsList = {"hello!", "hi.", "howdy ;)", "greetings...", "hey!", "Whats up, /name?"};
        for (String s1 : greetingsList)
            responseMap.put(s1.replaceAll("\\W", ""), greetingsList);
        String[] salutationsList = {"bye!", "goodbye.", "peace :P", "adios <3", "Farewell /name..."};
        for (String s1 : salutationsList)
            responseMap.put(s1.replaceAll("\\W", ""), salutationsList);
        String[] catList = {"I love cats!", "I hate cats.", "/name, cats are not quite as good as dogs"};
        responseMap.put("cat", catList);
        responseMap.put("cats", catList);
        String[] jackList = {"Jack is great!", "Jack is not the chief of police."};
        responseMap.put("jack", jackList);
        String[] familyList = {"The family is great!", "Everyone is doing well.", "Don't talk to me about my family."};
        responseMap.put("family", familyList);
        String[] csList = {"Did you forget a semicolon?", "Idk, ask on the forum.", "Maybe you have a typo.", "Did you try rebooting?", "Figure it out, /name"};
        String[] csPrompts = {"cs125", "cs", "computer", "code", "programming", "app", "program"};
        for (String s : csPrompts)
            responseMap.put(s, csList);
        String[] noList = {"Ok.", "hmmm...", "Interesting", "Alright then."};
        responseMap.put("no", noList);
        String[] yesList = {"OK!", "Awesome!", "Yeah!!", "okie doke", "sounds good", "yes??!!"};
        responseMap.put("yes", yesList);
        String[] plansList = {"Im down for anything ;)", "My parents aren't home this weekend if you know what I mean.", "I'm free later, wyd", "You have such nice lips"};
        String[] plansResponse = {"wyd", "plans", "date", "romance", "boyfriend", "girlfriend", "later", "doing"};
        for (String s : plansResponse)
            responseMap.put(s, plansList);
        String[] geoffList = {"All Hail Geoff", "Geoff is my dad", "Praise the lord, our savior, Geoffrey Challen.", "I love Geoff, whats your favorite thing about him /name?"};
        String[] geoffPrompts = {"geoff", "challen", "cs125 professor"};
        for (String s : geoffPrompts) {
            responseMap.put(s, geoffList);
        }
    }

    public static String getResponse(String message, String name) {
        String[] msgArray = message.split(" ");
        StringBuilder toSend = new StringBuilder();
        for (String s : msgArray) {
            String[] r = responseMap.get(s.toLowerCase().replaceAll("\\W",""));
            if (r != null) {
                toSend.append(r[(int)(Math.random() * r.length)]);
                toSend.append(" ");
            }
        }
        if (toSend.length() == 0) {
            toSend.append("Sorry, can't talk right now.");
        }
        return toSend.toString().replace("/name", name);
    }
}
