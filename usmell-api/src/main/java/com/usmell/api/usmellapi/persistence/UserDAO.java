package com.usmell.api.usmellapi.persistence;

import java.io.IOException;


import com.usmell.api.usmellapi.model.User;

public interface UserDAO {
    
    User createUser(User user) throws IOException;

    User getUser(int id) throws IOException;

    User generateNewSession(User user) throws IOException;

    User logout(User user) throws IOException;

    User getUserFromSession(String auth) throws IOException;
}
