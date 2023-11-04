CREATE TABLE users(
    id SERIAL PRIMARY KEY, 
    username TEXT, 
    paswrd TEXT, 
    email TEXT
);

CREATE TABLE reviews(
    id SERIAL PRIMARY KEY,
    user_id TEXT, 
    rating INT, 
    comment TEXT,
    votes INT, 
    time_reviewed TIME,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE upvotes(
    id SERIAL PRIMARY KEY, 
    review_id INT, 
    user_id INT
    FOREIGN KEY(review_id) REFERENCES reviews(id)
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE downvotes(
    id SERIAL PRIMARY KEY, 
    review_id INT, 
    user_id INT
    FOREIGN KEY(review_id) REFERENCES reviews(id)
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE locations(
    id SERIAL PRIMARY KEY, 
    
);

CREATE TABLE smell(

); 


