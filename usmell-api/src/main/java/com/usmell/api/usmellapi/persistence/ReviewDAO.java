package com.usmell.api.usmellapi.persistence;

import java.io.IOException;

import com.usmell.api.usmellapi.model.Review;

/**
 * Defines the interface for Review object persistence
 * 
 * @author Alec Kovalczik
 */
public interface ReviewDAO {

    Review createReview(Review review) throws IOException;

    Review updateReview(Review review) throws IOException;

    Review removeReview(Review review) throws IOException;
}
