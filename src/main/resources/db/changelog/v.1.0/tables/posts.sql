
CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY ,
    slug VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(20000),
    image VARCHAR(10000),
    user_id BIGINT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    CONSTRAINT unique_post_slug UNIQUE (slug),
    FOREIGN KEY (user_id) REFERENCES users(id)
);