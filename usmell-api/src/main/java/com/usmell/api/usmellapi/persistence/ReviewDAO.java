package com.usmell.api.usmellapi.persistence;

import java.io.IOException;

import com.usmell.api.usmellapi.model.Review;

/**
 * Defines the interface for Review object persistence
 * 
 * @author Alec Kovalczik
 */
public interface ReviewDAO {

    Review getReview(int reviewID) throws IOException;
    Review getReview(int userID, int smellID) throws IOException;

    Review createReview(Review review) throws IOException;

    Review updateReview(Review review) throws IOException;

    Review removeReview(Review review) throws IOException;
}
