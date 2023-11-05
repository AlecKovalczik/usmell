package com.usmell.api.usmellapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmell.api.usmellapi.model.Smell;
import com.usmell.api.usmellapi.model.Review;

@Component
public class SmellFileDAO implements SmellDAO {
    Map<Integer,Smell> smells;   // Provides a local cache of the smell objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Smell
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    public SmellFileDAO(@Value("${smell.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the smells from the file
    }

    private boolean save() throws IOException {
        Smell[] smellArray = getSmells();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), smellArray);
        return true;
    }

    private boolean load() throws IOException {
        smells = new HashMap<>();

        // Deserializes the JSON objects from the file into an array of smells
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Smell[] smellArray = objectMapper.readValue(new File(filename), Smell[].class);

        // Add each smell to the hash map
        for (Smell smell : smellArray) {
            smells.put(smell.getSmellID(), smell);
        }
        return true;
    }

    @Override
    public Smell getSmell(int smellID) throws IOException {
        synchronized(smells) {
            if (smells.containsKey(smellID))
                return smells.get(smellID);
            else
                return null;
        }
    }

    @Override
    public Smell[] getSmells() throws IOException {
        synchronized(smells){
            ArrayList<Smell> smellArrayList = new ArrayList<>();

            for (Smell smell : smells.values()) {
                smellArrayList.add(smell);
            }

            Smell[] smellArray = new Smell[smellArrayList.size()];
            smellArrayList.toArray(smellArray);
            return smellArray;
        }
    }

    @Override
    public TreeMap<Integer, Review> getReviews(int smellID) throws IOException {
        Smell smell = getSmell(smellID);
        TreeMap<Integer, Review> reviews = smell.getReviews();
        return reviews;
    }

    @Override
    public Smell createSmell(Smell smell) throws IOException {
        synchronized(smells) {
            for (Smell element : getSmells()){
                if (element.equals(smell)){
                    save();
                    return null;
                }
            }

            Smell newSmell = null;
            try {
                newSmell = new Smell(smell.getName(), smell.getSmellType());
                smells.put(newSmell.getSmellID(),newSmell);
            } catch (Exception e) {

            }
            save(); // may throw an IOException
            return newSmell;
        }
    }

    @Override
    public Smell updateSmell(Smell smell) throws IOException {
        synchronized(smells) {
            if (smells.containsKey(smell.getSmellID())){
                smells.put(smell.getSmellID(), smell);
                save(); // may throw an IOException
                return smell;
            } else {
                return null;
            }
        }
    }

    @Override
    public Smell removeSmell(Smell smell) throws IOException {
        synchronized(smells) {
            if (smells.containsKey(smell.getSmellID())){
                Smell removed = smells.remove(smell.getSmellID());
                save();
                return removed;
            } else {
                return null;
            }
        }
    }
}
