package com.usmell.api.usmellapi.persistence;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.usmell.api.usmellapi.model.Review;

@Component
public class ReviewFileDAO implements ReviewDAO {
    private static final Logger LOG = Logger.getLogger(ReviewFileDAO.class.getName());
    Map<Integer,Review> reviews;   // Provides a local cache of the review objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Review
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    public ReviewFileDAO(@Value("${reviews.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the reviews from the file
    }

    private boolean save() throws IOException {
        Review[] reviewArray = getReviews();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), reviewArray);
        return true;
    }

    private boolean load() throws IOException {
        reviews = new HashMap<>();

        // Deserializes the JSON objects from the file into an array of reviews
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Review[] reviewArray = objectMapper.readValue(new File(filename), Review[].class);

        // Add each review to the hash map
        for (Review review : reviewArray) {
            reviews.put(review.getReviewID(), review);
        }
        return true;
    }

    private Review[] getReviews() {
        ArrayList<Review> reviewArrayList = new ArrayList<>();

        for (Review review : reviews.values()) {
            reviewArrayList.add(review);
        }

        Review[] reviewArray = new Review[reviewArrayList.size()];
        reviewArrayList.toArray(reviewArray);
        return reviewArray;
    }

    @Override
    public Review createReview(Review review) throws IOException {
        synchronized(reviews) {
            for (Review element : getReviews()){
                if (element.equals(review)){
                    save();
                    return null;
                }
            }
            Review newReview = new Review(review.getReviewer(), review.getComment(), review.getRating());
            reviews.put(newReview.getReviewID(),newReview);
            save(); // may throw an IOException
            return newReview;
        }
    }

    @Override
    public Review updateReview(Review review) throws IOException {
        synchronized(reviews) {
            if (reviews.containsKey(review.getReviewID())){
                reviews.put(review.getReviewID(), review);
                save(); // may throw an IOException
                return review;
            } else {
                return null;
            }
        }
    }
}
