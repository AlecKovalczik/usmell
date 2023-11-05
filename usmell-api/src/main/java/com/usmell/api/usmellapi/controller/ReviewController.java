package com.usmell.api.usmellapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usmell.api.usmellapi.model.Review;
import com.usmell.api.usmellapi.model.User;
import com.usmell.api.usmellapi.persistence.ReviewDAO;
import com.usmell.api.usmellapi.persistence.SmellDAO;
import com.usmell.api.usmellapi.persistence.UserDAO;

import java.io.IOException;
import java.util.TreeMap;

@RestController
@CrossOrigin
@RequestMapping("data/reviews")
public class ReviewController {
    private ReviewDAO reviewDao;
    private UserDAO userDao;
    private SmellDAO smellDao;

    public ReviewController(ReviewDAO reviewDao, UserDAO userDao, SmellDAO smellDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @GetMapping("/{smellID}")
    public ResponseEntity<TreeMap<Integer, Review>> getReviews(@PathVariable int smellID){
        try {
            TreeMap<Integer, Review> smellReviews = smellDao.getReviews(smellID);
            return new ResponseEntity<TreeMap<Integer, Review>>(smellReviews, HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Review> createReview(@RequestHeader("Authorization") String bearerToken, @RequestBody Review review) {
        try {
            // authentication
            User user = userDao.getUserFromSession(bearerToken); // authenticate user
            if (user == null) { // if authorization fails, tell them they are not authorized
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            int revUserID = review.getReviewer();
            int revSmellID = review.getSmellID();

            if (reviewDao.getReview(revUserID, revSmellID) == null) {
                System.out.println("create");
                Review newReview = reviewDao.createReview(review);
                if (newReview == null){
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                
                return new ResponseEntity<Review>(newReview, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

                        
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Review> updateReview(@RequestHeader("Authorization") String bearerToken, @RequestBody Review review){
        try {
            // authentication
            User user = userDao.getUserFromSession(bearerToken); // authenticate user
            if (user == null) { // if authorization fails, tell them they are not authorized
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            boolean hasReviewed = false; 
            int revUserID = review.getReviewer();
            int revSmellID = review.getSmellID();
            int revID = -1;


            // checks to see if the user has already created a review for that product, if so then just update that
            if (reviewDao.getReview(revUserID, revSmellID) != null) {
                Review rev = reviewDao.getReview(revUserID, revSmellID);
                hasReviewed = true;
                revID = rev.getReviewID();
            }

            if (hasReviewed) {
                System.out.println("update");
                Review r = new Review(revID, review.getReviewer(), review.getSmellID(), review.getComment(), review.getRating());
                Review updatedReview = reviewDao.updateReview(r);
                if (updatedReview == null){
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                return new ResponseEntity<Review>(updatedReview, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

                        
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

