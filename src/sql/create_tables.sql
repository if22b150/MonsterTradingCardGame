CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    coins INT NOT NULL,
    name VARCHAR(255),
    bio VARCHAR(255),
    image VARCHAR(255),
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sessions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS packages (
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    package_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (package_id) REFERENCES packages(id)
);

CREATE TABLE IF NOT EXISTS cards (
    id SERIAL PRIMARY KEY,
    public_id VARCHAR(255) NOT NULL UNIQUE,
    user_id INT,
    package_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    damage INT NOT NULL,
    card_type VARCHAR(255) NOT NULL,
    element_type VARCHAR(255) NOT NULL,
    deck BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (package_id) REFERENCES packages(id)
);

CREATE TABLE IF NOT EXISTS battles (
    id SERIAL PRIMARY KEY,
    user_1_id INT NOT NULL,
    user_2_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_1_id) REFERENCES users(id),
    FOREIGN KEY (user_2_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS battle_rounds (
    id SERIAL PRIMARY KEY,
    battle_id INT NOT NULL,
    card_user_1_id INT NOT NULL,
    card_user_2_id INT NOT NULL,
    FOREIGN KEY (battle_id) REFERENCES battles(id),
    FOREIGN KEY (card_user_1_id) REFERENCES cards(id),
    FOREIGN KEY (card_user_2_id) REFERENCES cards(id)
);



delete from cards; delete from transactions; delete from packages; delete from sessions; delete from users;
