DROP TABLE IF EXISTS buy_book CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS author_book CASCADE;

CREATE TABLE "user"(
    user_id SERIAL PRIMARY KEY,
    "name" CHARACTER VARYING(50),
    age INT
);

CREATE TABLE author(
    author_id SERIAL PRIMARY KEY,
    "name" CHARACTER VARYING(50)
);

CREATE TABLE book(
    book_id SERIAL PRIMARY KEY,
    title CHARACTER VARYING(100),
    price DECIMAL(10,2),
    amount INT
);

CREATE TABLE author_book(
    book_id INT,
    author_id INT,
    FOREIGN KEY (book_id) REFERENCES book (book_id),
    FOREIGN KEY (author_id) REFERENCES author (author_id)
);

CREATE TABLE buy_book(
    buy_book_id SERIAL PRIMARY KEY,
    user_id INT,
    book_id INT,
    price DECIMAL(10,2),
    amount INT,
    FOREIGN KEY (user_id) REFERENCES "user" (user_id),
    FOREIGN KEY (book_id) REFERENCES book (book_id)
);