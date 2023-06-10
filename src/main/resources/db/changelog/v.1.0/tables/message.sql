CREATE TABLE IF NOT EXISTS message (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    from_user VARCHAR(255) NOT NULL,
    to_user VARCHAR(255) NOT NULL,
    content VARCHAR(10000) NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chat(chat_id)
);