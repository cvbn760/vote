DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY,
    name             VARCHAR(30)                 NOT NULL,
    email            VARCHAR(30)                 NOT NULL,
    password         VARCHAR(50)                 NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);