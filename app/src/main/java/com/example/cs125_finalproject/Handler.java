package com.example.cs125_finalproject;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Handler {
    public static ArrayList<Contact> contacts = new ArrayList<>();

    public static void fillContactsWithRandom(int amount) {
        if (contacts.size() >= amount) {
            return;
        }
        for (int i = 0; i < amount; i++) {
            Log.d("Create Contact", "Creating Contact");
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            int number = 0000000000;
            contacts.add(new Contact(generatedString, number));
        }
    }
}
