package com.usmell.api.usmellapi.model;

public class User {
    private static int nextID;
    private int userID;
    private String username;
    private String email;
    private String password;
    private String token;

    public User(String username, String email, String password){
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

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
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
