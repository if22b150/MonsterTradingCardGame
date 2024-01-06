CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    coins INT NOT NULL,
    name VARCHAR(255),
    bio VARCHAR(255),
    image VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    elo INT
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
    winner_user_id INT,
    loser_user_id INT,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_1_id) REFERENCES users(id),
    FOREIGN KEY (user_2_id) REFERENCES users(id),
    FOREIGN KEY (winner_user_id) REFERENCES users(id),
    FOREIGN KEY (loser_user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS battle_rounds (
    id SERIAL PRIMARY KEY,
    battle_id INT NOT NULL,
    card_user_1_id INT NOT NULL,
    card_user_2_id INT NOT NULL,
    user_1_damage INT NOT NULL,
    user_2_damage INT NOT NULL,
    FOREIGN KEY (battle_id) REFERENCES battles(id),
    FOREIGN KEY (card_user_1_id) REFERENCES cards(id),
    FOREIGN KEY (card_user_2_id) REFERENCES cards(id)
);

/*
battle_round_id: if set, from card was traded to to_user
to_user_card_id: if set, 2 cards were swapped; if not set, no card is swapped from to_user to from_user
*/
CREATE TABLE IF NOT EXISTS card_trades (
    id SERIAL PRIMARY KEY,
    battle_round_id INT,
    from_user_id INT NOT NULL,
    to_user_id INT NOT NULL,
    from_user_card_id INT NOT NULL,
    to_user_card_id INT,
    FOREIGN KEY (battle_round_id) REFERENCES battle_rounds(id),
    FOREIGN KEY (from_user_id) REFERENCES users(id),
    FOREIGN KEY (to_user_id) REFERENCES users(id),
    FOREIGN KEY (from_user_card_id) REFERENCES cards(id),
    FOREIGN KEY (to_user_card_id) REFERENCES cards(id)
);

CREATE TABLE IF NOT EXISTS user_stats (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    games_played INT NOT NULL,
    elo INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


delete from user_stats; delete from card_trades; delete from battle_rounds; delete from battles; delete from cards; delete from transactions; delete from packages; delete from sessions; delete from users;