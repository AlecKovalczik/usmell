package com.usmell.api.usmellapi.model;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet; 
public class Review {
    private String userID; 
    private HashSet<String> upVotes;
    private HashSet<String> downVotes;
    private Time timeReviewed;
    private String comment; 
    private int reviewID; 
    private int rating;
    private int votes;  
    private static int nextReviewId = 0; 

    public Review(String userID, String comment, int rating){
        upVotes = new HashSet<>(); 
        downVotes = new HashSet<>(); 
        votes = 0; 
        this.rating = rating;
        LocalTime localTime = LocalTime.now();

        Time timeReviewed = new Time(localTime.toNanoOfDay()); 
        this.comment = comment;
        this.userID= userID;
        reviewID = nextReviewId; 
        nextReviewId++;
    }

    public boolean upVote(String userID){
        if(!upVotes.contains(userID)){
            upVotes.add(userID);
            votes = upVotes.size() - downVotes.size(); 
            return true;  
        }else{
            return false; 
        }
    }

    public boolean downVote(String userID){
        if(!downVotes.contains(userID)){
            downVotes.add(userID); 
            votes = upVotes.size() - downVotes.size(); 
            return true; 
        }else{
            return false; 
        }
    }

    /* methods to edit review */
    public void editComment(String newComment){comment = newComment;}
    public void editRating(int newRating){this.rating = newRating; }
    

    /* these are the getters */
    public int getRating(){return rating;}
    public int getVotes(){return votes;} 
    public String getComment(){return comment;}
    public String getReviewee(){return userID;}
    
    

}
