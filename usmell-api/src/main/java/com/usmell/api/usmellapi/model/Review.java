package com.usmell.api.usmellapi.model;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty("reviewID") private int reviewID; 
    @JsonProperty("userID") private int userID; 
    @JsonProperty("smellID") private int smellID;
    @JsonProperty("upVotes") private HashSet<Integer> upVotes;
    @JsonProperty("downVotes") private HashSet<Integer> downVotes;
    @JsonProperty("comment") private String comment; 
    @JsonProperty("rating") private int rating;
    @JsonProperty("votes") private int votes;  
    // private static int nextReviewId = 0; 

    // public Review(@JsonProperty("userID") int userID, @JsonProperty("smellID") int smellID, @JsonProperty("comment") String comment, @JsonProperty("rating") int rating){
    //     upVotes = new HashSet<>(); 
    //     downVotes = new HashSet<>(); 
    //     votes = 0; 
    //     this.rating = rating;
    //     this.comment = comment;
    //     this.userID = userID;
    //     this.smellID = smellID;
    //     this.reviewID = nextReviewId; 
    //     nextReviewId++;
    // }

    public Review(@JsonProperty("reviewID") int reviewID, @JsonProperty("userID") int userID, @JsonProperty("smellID") int smellID, @JsonProperty("comment") String comment, @JsonProperty("rating") int rating){
        this.upVotes = new HashSet<>(); 
        this.downVotes = new HashSet<>(); 
        this.votes = 0; 
        this.rating = rating;
        this.comment = comment;
        this.userID = userID;
        this.smellID = smellID;
        this.reviewID = reviewID;
    }

    public boolean upVote(@JsonProperty("userID") int userID){
        if(!upVotes.contains(userID)){
            upVotes.add(userID);
            votes = upVotes.size() - downVotes.size(); 
            return true;  
        }else{
            return false; 
        }
    }

    public boolean downVote(@JsonProperty("userID") int userID){
        if(!downVotes.contains(userID)){
            downVotes.add(userID); 
            votes = upVotes.size() - downVotes.size(); 
            return true; 
        }else{
            return false; 
        }
    }

    /* methods to edit review */
    public void editComment(@JsonProperty("comment") String newComment){comment = newComment;}
    public void editRating(@JsonProperty("rating") int newRating){this.rating = newRating; }
    

    /* these are the getters */
    public int getReviewID(){return reviewID;}
    public int getRating(){return rating;}
    public int getVotes(){return votes;} 
    public String getComment(){return comment;}
    public int getReviewer(){return userID;}
    public int getSmellID(){return smellID;}  

}
