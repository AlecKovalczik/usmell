package com.usmell.api.usmellapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private static int nextID;
    @JsonProperty("userID") private int userID;
    @JsonProperty("username") private String username;
    @JsonProperty("email") private String email;
    @JsonProperty("password") private String password;
    @JsonProperty("token") private String token;

    public User(@JsonProperty("username") String username, @JsonProperty("email") String email, @JsonProperty("password") String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.userID = nextID;
        nextID++;
    }

    public int getUserID(){
        return userID;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(@JsonProperty("username") String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(@JsonProperty("email") String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
    
    public void setToken(@JsonProperty("token") String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            if (u.getUserID() == userID) {
                return true;
            }
        }
        return false;
    }
}
