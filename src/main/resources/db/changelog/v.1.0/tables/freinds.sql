
CREATE TABLE IF NOT EXISTS friends (
    friend_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (friend_id, user_id),
    FOREIGN KEY (friend_id) REFERENCES users(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);