package com.usmell.api.usmellapi.model;

import java.util.HashMap;

public class Smell {
    private static int nextSmellID;
    private int smellID;
    private String name;
    private double rating = 0.0;
    private HashMap<String, Review> reviews; // <userID, Review>
    private SmellType smellType;
    
    public Smell(String name, SmellType smellType){
        setID();
        this.name = name;
        this.smellType = smellType;
    }

    private void setID(){
        this.smellID = nextSmellID;
        nextSmellID++;
    }
}
