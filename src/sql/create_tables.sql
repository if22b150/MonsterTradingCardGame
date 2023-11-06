CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    coins INT NOT NULL,
    name VARCHAR(255),
    bio VARCHAR(255),
    image VARCHAR(255),
    password VARCHAR(255) NOT NULL
);