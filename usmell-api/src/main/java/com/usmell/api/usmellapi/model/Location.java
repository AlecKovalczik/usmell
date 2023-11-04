package com.usmell.api.usmellapi.model;

import java.util.HashMap;

public class Location {
    private int locationID;
    private String name; 
    private HashMap<Smell, Integer> currentSmells; 
    private double [] coordinates; 

    public Location(String name){
        // generate the coordinates using google maps API 
        
        this.name = name; 
        currentSmells = new HashMap<Smell, Integer> (); 
        // get coordinates from getting the location 
    }

    public Location(){
        currentSmells = new HashMap<Smell, Integer>(); 
    }

}
