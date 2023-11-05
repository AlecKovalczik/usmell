package com.usmell.api.usmellapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usmell.api.usmellapi.model.Smell;
import com.usmell.api.usmellapi.model.User;
import com.usmell.api.usmellapi.persistence.SmellDAO;
import com.usmell.api.usmellapi.persistence.UserDAO;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("data/smells")
public class SmellController {
    private UserDAO userDao;
    private SmellDAO smellDao;

    public SmellController(UserDAO userDao, SmellDAO smellDao) {
        this.userDao = userDao;
        this.smellDao = smellDao;
    }

    @GetMapping("/{smellID}")
    public ResponseEntity<Smell> getSmell(@PathVariable int smellID){
        try {
            Smell smell = smellDao.getSmell(smellID);
            return new ResponseEntity<Smell>(smell, HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Smell[]> getSmells(){
        try {
            Smell[] smells = smellDao.getSmells();
            return new ResponseEntity<Smell[]>(smells, HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Smell> createSmell(@RequestHeader("Authorization") String bearerToken, @RequestBody Smell smell) {
        try {
            // authentication
            User user = userDao.getUserFromSession(bearerToken); // authenticate user
            if (user == null) { // if authorization fails, tell them they are not authorized
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Smell newSmell = smellDao.createSmell(smell);
            return new ResponseEntity<>(newSmell, HttpStatus.OK);
                        
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Smell> updateReview(@RequestHeader("Authorization") String bearerToken, @RequestBody Smell smell){
        try {
            // authentication
            User user = userDao.getUserFromSession(bearerToken); // authenticate user
            if (user == null) { // if authorization fails, tell them they are not authorized
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (smellDao.getSmell(smell.getSmellID()) != null) {
                Smell newSmell = new Smell(smell.getSmellID(), smell.getName(), smell.getSmellType());
                Smell updatedSmell = smellDao.updateSmell(newSmell);
                if (updatedSmell == null){
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                return new ResponseEntity<Smell>(updatedSmell, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

                        
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{smellID}")
    public ResponseEntity<Smell> deleteSmell(@RequestHeader("Authorization") String bearerToken, @PathVariable int smellID) {
    
        try {
            // authentication
            User user = userDao.getUserFromSession(bearerToken); // authenticate user
            if (user == null) { // if authorization fails, tell them they are not authorized
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(smellDao.removeSmell(smellID) != null)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
