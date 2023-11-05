package com.usmell.api.usmellapi.model;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Smell {
    private static int nextSmellID;
    @JsonProperty("smellID") private int smellID;
    @JsonProperty("name") private String name;
    @JsonProperty("rating") private double rating = 0.0;
    @JsonProperty("reviews") private TreeMap<Integer, Review> reviews = null; // <userID, Review>
    @JsonProperty("smellType") private SmellType smellType;
    
    public Smell(@JsonProperty("name") String name, @JsonProperty("smellType") SmellType smellType) throws Exception{
        this.name = name;
        this.smellType = smellType;
        
        // Set the id to a unique int using a static variable
        this.smellID = nextSmellID;
        smellID++;
    }
    
    public Smell(@JsonProperty("smellID") int smellID, @JsonProperty("name") String name, @JsonProperty("smellType") SmellType smellType) throws Exception{
        this.smellID = smellID;
        this.name = name;
        this.smellType = smellType;
        
        // Set the id to a unique int using a static variable
        this.smellID = nextSmellID;
        smellID++;
    }

    public int getSmellID(){
        return smellID;
    }

    public String getName(){
        return name;
    }

    public void setName(@JsonProperty("name") String name){
        this.name = name;
    }

    public SmellType getSmellType(){
        return smellType;
    }
    
    public void setSmellType(@JsonProperty("smellType") SmellType smellType){
        this.smellType = smellType;
    }

    public int getReportedCount(){
        return reviews.size();
    }

    public double getRating(){
        return this.rating;
    }

    public TreeMap<Integer, Review> getReviews(){
        return reviews;
    }

    public void createReview(int reviewID, int userID, String comment, int originalRating) throws Exception{
        Review review = new Review(reviewID, userID, smellID, comment, originalRating);
        if (reviews.containsKey(userID)){
            throw new Exception("User already reviewed this smell");
        }
        this.reviews.put(userID, review);
    }

    public void editReview(int reviewID, int userID, int originalRating, String comment){
        Review review = new Review(reviewID, userID, smellID, comment, originalRating);
        if (reviews.containsKey(userID)){
            this.reviews.replace(userID, review);
        }
    }

    public void removeReview(int userID){
        this.reviews.remove(userID);
    }
}
