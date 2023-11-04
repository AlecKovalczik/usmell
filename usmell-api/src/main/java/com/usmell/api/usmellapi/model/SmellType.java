package com.usmell.api.usmellapi.model;

public enum SmellType {
    BATHROOM_SMELL("Bathroom Smell"),
    BODY_ODOR("Body Odor"),
    ROTTING("Rotting"),
    CHEMICAL("Chemical"),
    OTHER("Other");

    private final String name;
    private SmellType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
