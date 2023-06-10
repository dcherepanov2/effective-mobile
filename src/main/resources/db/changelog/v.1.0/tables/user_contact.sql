CREATE TABLE IF NOT EXISTS user_contact (
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    type SMALLINT NOT NULL,
    approved BOOLEAN NOT NULL,
    code_time TIMESTAMP NOT NULL,
    code INT NOT NULL,
    contact VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);