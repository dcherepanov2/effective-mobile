
CREATE TABLE IF NOT EXISTS followers (
    follower_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (follower_id, user_id),
    FOREIGN KEY (follower_id) REFERENCES users(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);