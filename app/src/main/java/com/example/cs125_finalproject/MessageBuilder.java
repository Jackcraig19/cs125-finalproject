package com.example.cs125_finalproject;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
    private static Map<String, String[]> responseMap = new HashMap<>();

    public static void setupMessages(String[] contactNames) {
        responseMap = new HashMap<>();
        //String[] greetReply = Resources.getSystem().getStringArray(R.array.greet_response);
        //String[] greetReply = res.getStringArray(R.array.greet_response);
        String[] greetReply = {"hello!", "hi.", "howdy ;)", "greetings...", "hey!", "Whats up, /name/?"};
        for (String s1 : greetReply)
            responseMap.put(s1.replaceAll("\\W", ""), greetReply);
        String[] salutationsList = {"bye!", "goodbye.", "peace :P", "adios <3", "Farewell /name/..."};
        for (String s1 : salutationsList)
            responseMap.put(s1.replaceAll("\\W", ""), salutationsList);
        String[] catList = {"I love cats!", "I hate cats.", "/name/, cats are not quite as good as dogs"};
        responseMap.put("cat", catList);
        responseMap.put("cats", catList);
        String[] jackList = {"bruh", "bruh", "Jack is great!", "Jack is not the chief of police.", "/name/...\nwhy are you talking about Jack? Let's talk about Inigo ;P", "bruh"};
        responseMap.put("jack", jackList);
        String[] familyList = {"The family is great!", "Everyone is doing well.", "Don't talk to me about my family.", "How's your family, /name/?"};
        responseMap.put("family", familyList);
        String[] csList = {"Did you forget a semicolon?", "Idk, ask on the forum.", "Maybe you have a typo.", "Did you try rebooting?", "Figure it out, /name/", "bruh"};
        String[] csPrompts = {"cs125", "cs", "computer", "code", "programming", "app", "program"};
        for (String s : csPrompts)
            responseMap.put(s, csList);
        String[] noList = {"Ok.", "hmmm...", "Interesting", "Alright then.", "bruh", "That settles it", "\"ok\""};
        responseMap.put("no", noList);
        String[] yesList = {"OK!", "Awesome!", "Yeah!!", "okie doke", "sounds good, /name/", "yes??!!"};
        responseMap.put("yes", yesList);
        String[] plansList = {"Im down for anything ;)", "My parents aren't home this weekend if you know what I mean.", "I'm free later, wyd", "You have such nice lips, /name/"};
        String[] plansResponse = {"wyd", "plans", "date", "romance", "boyfriend", "girlfriend", "later", "doing", "free", "tonight", "plan"};
        for (String s : plansResponse)
            responseMap.put(s, plansList);
        String[] geoffList = {"All Hail Geoff", "Geoff is my dad", "Praise the lord, our savior, Geoffrey Challen.", "I love Geoff, whats your favorite thing about him?"};
        String[] geoffPrompts = {"geoff", "challen", "cs125 professor"};
        for (String s : geoffPrompts) {
            responseMap.put(s, geoffList);
        }
        String[] cursePrompt = {"shit", "fuck", "bitch", "poop", "poopoo", "peepee", "wiener", "ass", "hell", "dick", "cock"};
        String[] curseResponse = {"Watch your tongue, /name/.", "you shouldn't say \"/prompt/\"", "Ha, /prompt/", "/prompt/ is a bad word."};
        for (String s : cursePrompt) {
            responseMap.put(s, curseResponse);
        }
        String[] botPrompt = {"bot", "robot", "chatbot", "reply"};
        String[] botResponse = {"no...", "I am a human, /name/", "I am not a robot", "Maybe you're a robot", "alright you got me :)", "yes?"};
        for (String s : botPrompt) {
            responseMap.put(s, botResponse);
        }
        String[] peopleReply = {"I love /prompt/", "I hate /prompt/.", "Who's /prompt/?"};
        for (String s : contactNames) {
            responseMap.put(s, peopleReply);
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
                toSend.append(toAppend.replace("/name/", name).replace("/prompt/", prompt));
                toSend.append(" ");
            }
        }
        if (toSend.length() == 0) {
            toSend.append("Sorry, can't talk right now.");
        }
        return toSend.toString();
    }
}
