
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY ,
    slug VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT unique_user_slug UNIQUE (slug),
    status SMALLINT NOT NULL
);