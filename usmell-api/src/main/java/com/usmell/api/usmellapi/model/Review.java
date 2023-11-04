package com.usmell.api.usmellapi.model;

import java.util.HashSet; 
public class Review {
    private int userID; 
    private HashSet<Integer> upVotes;
    private HashSet<Integer> downVotes;
    private String comment; 
    private int reviewID; 
    private int rating;
    private int votes;  
    private static int nextReviewId = 0; 

    public Review(int userID, String comment, int rating){
        upVotes = new HashSet<>(); 
        downVotes = new HashSet<>(); 
        votes = 0; 
        this.rating = rating;
        this.comment = comment;
        this.userID= userID;
        this.reviewID = nextReviewId; 
        nextReviewId++;
    }

    public boolean upVote(int userID){
        if(!upVotes.contains(userID)){
            upVotes.add(userID);
            votes = upVotes.size() - downVotes.size(); 
            return true;  
        }else{
            return false; 
        }
    }

    public boolean downVote(int userID){
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
    public int getReviewID(){return reviewID;}
    public int getRating(){return rating;}
    public int getVotes(){return votes;} 
    public String getComment(){return comment;}
    public int getReviewee(){return userID;}
    
    

}
