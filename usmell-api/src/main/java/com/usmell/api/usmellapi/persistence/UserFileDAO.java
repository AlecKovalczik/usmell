package com.usmell.api.usmellapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmell.api.usmellapi.model.User;

@Component
public class UserFileDAO implements UserDAO {
    Map<Integer,User> users;   // Provides a local cache of the user objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private String filename; // Filename to read from and write to
    
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the users from the file
    }
    
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    private User[] getUsersArray() {
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            userArrayList.add(user);
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getUserID(), user);
        }
        // Make the next id one greater than the maximum from the file
        return true;
    }

    @Override
    public User getUser(int userID) {
        synchronized(users) {
            if (users.containsKey(userID))
                return users.get(userID);
            else
                return null;
        }
    }

    @Override
    public User createUser(User user) throws IOException {
        synchronized (users) {
            // We create a new user object because the id field is immutable
            // and we need to assign the next unique id
            for (User element : getUsersArray()) {
                if (element.getUsername().equals(user.getUsername())) {
                    save();
                    return null;
                }
            }
            User newUser = new User(user.getUsername(), user.getEmail(), user.getPassword());
            users.put(newUser.getUserID(), newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    @Override
    public User generateNewSession(User auth) throws IOException {
        synchronized (users) {
            for (User user : getUsersArray()) {
                //for logins, search the whole db since users won't know their ids.
                if (user.getUsername().equals(auth.getUsername())) {
                    if (user.getPassword().equals(auth.getPassword())) {
                        //user has valid credentials, now need to set session token
                        user.setToken(generateToken());
                        save();
                        /* 
                         * Return actual user info
                         * This method is only used to log in,
                         * so returning the user instance provided by the user
                         * probably wouldn't be a security issue,
                         * but it would break logins,
                         * since the user wouldn't know their id and session token.
                         */
                        return user;
                    } else {
                        //password is wrong, return
                        return null;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public User logout(User auth) throws IOException {
        synchronized (users) {
            for (User user : getUsersArray()) {
                //for logins, search the whole db since users won't know their ids.
                if (user.getUsername().equals(auth.getUsername())) {
                    if (user.getPassword().equals(auth.getPassword())) {
                        //user has valid credentials, now need to set session token
                        user.setToken(null);
                        save();
                        /* 
                         * Return actual user info
                         * This method is only used to logout,
                         * so returning the user instance provided by the user
                         * probably wouldn't be a security issue,
                         * but it would break logouts,
                         * since the user wouldn't know their id and session token.
                         */
                        return user;
                    } else {
                        //password is wrong, return
                        return null;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public User getUserFromSession(String auth) throws IOException {
        synchronized (users) {
            String token = null;

            try {
                token = auth;
            } catch (NumberFormatException e) {
                return null;
            }

            /*
             * very important- all users start with null session tokens.
             * therefore users should not be able to authenticate with a null session token
             * otherwise they would be treated as the first user in the list with a null session token
             */
            if (token == null) {
                return null;
            }
            
            //finally, compare the token to other users
            for (User user : getUsersArray()){
                if (token.equals(user.getToken())) {
                    /* 
                     * only this variable is safe to use,
                     * not the one passed in the constructor.
                     * Since the passed-in var was provided by the user,
                     * it could have falsified values like the is_admin flag
                     */
                    return user;
                }
            }
            
            //token does not match
            return null;
        }
    }    
}
