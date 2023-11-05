package com.usmell.api.usmellapi.persistence;

import java.io.IOException;

import com.usmell.api.usmellapi.model.Review;

/**
 * Defines the interface for Review object persistence
 * 
 * @author Alec Kovalczik
 * Used SWEN Faculty HeroDAO for reference
 */
public interface ReviewDAO {

    Review[] getReviews() throws IOException;

    Review createReview(Review review) throws IOException;

    Review updateReview(Review review) throws IOException;
}
