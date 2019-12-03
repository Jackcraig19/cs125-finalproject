package com.example.cs125_finalproject;

public class Contact {
    private String name;
    private long number;
    private boolean toggleState = false;

    public Contact(String setName, long setNum) {
        name = setName;
        number = setNum;
    }
    public String getName() {
        return name;
    }
    public long getNumber() {
        return number;
    }
    public boolean getState() {
        return toggleState;
    }
    public void setState(boolean set) {
        toggleState = set;
    }
}
