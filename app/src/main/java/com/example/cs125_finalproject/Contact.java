package com.example.cs125_finalproject;

public class Contact {
    private String name;
    private int number;
    private boolean toggleState = false;

    public Contact(String setName, int setNum) {
        name = setName;
        number = setNum;
    }
    public String getName() {
        return name;
    }
    public int getNumber() {
        return number;
    }
    public boolean getState() {
        return toggleState;
    }
    public void setState(boolean set) {
        toggleState = set;
    }
}
