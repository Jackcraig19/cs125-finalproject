package com.example.cs125_finalproject;

public class Contact {
    private String name;
    private String number;
    private boolean toggleState = true;

    public Contact(String setName, String setNum) {
        name = setName;
        number = setNum;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public boolean getState() {
        return toggleState;
    }
    public void setState(boolean set) {
        toggleState = set;
    }
}
