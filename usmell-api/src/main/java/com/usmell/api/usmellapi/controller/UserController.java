package com.usmell.api.usmellapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usmell.api.usmellapi.model.User;
import com.usmell.api.usmellapi.persistence.UserDAO;

import java.io.IOException;

/**
 * Handles the REST API requests for the User resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Alec Kovalczik
 * @author Jonas Long
 * Used SWEN Facility HeroController.java for reference
 */

@RestController
@CrossOrigin
@RequestMapping("data/users")
public class UserController {
    private UserDAO userDao;

    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Creates a {@linkplain User user} with the provided user object
     * 
     * @param user - The {@link User user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link User user}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userDao.createUser(user);
            if (newUser == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginInfo) {
        try {
            User user = userDao.generateNewSession(loginInfo);

            if (user == null) {
                return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
            }
            
            return new ResponseEntity<User>(user, HttpStatus.OK);
        
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logoutUser(@RequestBody User userInfo) {
        try {
            User user = userDao.logout(userInfo);

            if (user == null) {
                return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<User>(user, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
