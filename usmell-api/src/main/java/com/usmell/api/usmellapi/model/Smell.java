package com.usmell.api.usmellapi.model;

import java.util.HashMap;

public class Smell {
    private static int nextSmellID;
    private int smellID;
    private String name;
    private double rating = 0.0;
    private HashMap<Integer, Review> reviews = null; // <userID, Review>
    private SmellType smellType;
    
    public Smell(String name, SmellType smellType, int userID, int originalRating, String comment){
        this.name = name;
        this.smellType = smellType;
        createReview(userID, rating);

        // Set the id to a unique int using a static variable
        this.smellID = nextSmellID;
        smellID++;
    }

    public int getReportedCount(){
        return reviews.size();
    }

    public double getRating(){
        return this.rating;
    }

    public void createReview(int userID, int originalRating, String comment){
        Review review = new Review(userID, comment, originalRating);
        if (reviews.containsKey(userID)){
            throw new Exception("User already reviewed this smell");
        }
        this.reviews.put(userID, review);
    }

    public void editReview(int userID, int originalRating, String comment){
        Review review = new Review(userID, comment, originalRating);
        if (reviews.containsKey(userID)){
            this.reviews.replace(userID, review);
        }
    }

    public void removeReview(int userID){
        this.reviews.remove(userID);
    }
}
