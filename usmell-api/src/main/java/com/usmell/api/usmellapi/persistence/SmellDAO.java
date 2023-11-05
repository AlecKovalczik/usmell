package com.usmell.api.usmellapi.persistence;

import java.io.IOException;
import java.util.TreeMap;

import com.usmell.api.usmellapi.model.Review;
import com.usmell.api.usmellapi.model.Smell;

/**
 * Defines the interface for Smell object persistence
 * 
 * @author Alec Kovalczik
 */
public interface SmellDAO {

    Smell getSmell(int smellID) throws IOException;

    Smell[] getSmells() throws IOException;

    TreeMap<Integer, Review> getReviews(int smellID) throws IOException;

    Smell createSmell(Smell smell) throws IOException;

    Smell updateSmell(Smell smell) throws IOException;

    Smell removeSmell(Smell smell) throws IOException;
}
