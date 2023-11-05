package com.usmell.api.usmellapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet; 
public class Review {
    @JsonProperty("userID") private int userID; 
    @JsonProperty("upVotes") private HashSet<Integer> upVotes;
    @JsonProperty("downVotes") private HashSet<Integer> downVotes;
    @JsonProperty("comment") private String comment; 
    @JsonProperty("reviewID") private int reviewID; 
    @JsonProperty("rating") private int rating;
    @JsonProperty("votes") private int votes;  
    @JsonProperty("nextReviewID") private static int nextReviewId = 0; 

    public Review(@JsonProperty("userID") int userID, @JsonProperty("comment") String comment, @JsonProperty("rating") int rating){
        upVotes = new HashSet<>(); 
        downVotes = new HashSet<>(); 
        votes = 0; 
        this.rating = rating;
        this.comment = comment;
        this.userID= userID;
        this.reviewID = nextReviewId; 
        nextReviewId++;
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
    public int getReviewee(){return userID;}
    
    

}
