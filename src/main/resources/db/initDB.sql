DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS voters;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS voters_seq;
DROP SEQUENCE IF EXISTS meal_seq;

SET DATABASE SQL SYNTAX PGS FALSE;

CREATE SEQUENCE user_roles_seq START WITH 0 increment by 1;
CREATE TABLE user_roles
(
    role_id INTEGER GENERATED BY DEFAULT AS SEQUENCE user_roles_seq PRIMARY KEY,
    role    VARCHAR(20)                         NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (role_id, role)
);

CREATE TABLE users
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    name             VARCHAR(30)                 NOT NULL,
    email            VARCHAR(30)                 NOT NULL,
    password         VARCHAR(50)                 NOT NULL,
    CONSTRAINT users_idx UNIQUE (email)
);

CREATE TABLE restaurants
(
    id_rest           INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    id_owner_rest     INTEGER                     NOT NULL,
    name_rest VARCHAR(50)                         NOT NULL,
    address VARCHAR(50)                           NOT NULL
);

CREATE SEQUENCE voters_seq START WITH 0 increment by 1;
CREATE TABLE voters
(
    v_id              INTEGER GENERATED BY DEFAULT AS SEQUENCE voters_seq PRIMARY KEY,
    menu_id           INTEGER,
    voter_id_pk       INTEGER                     NOT NULL,
    date              DATE                        NOT NULL,
    voice             BOOLEAN DEFAULT FALSE       NOT NULL,
    CONSTRAINT voters_idx UNIQUE (v_id, menu_id, voter_id_pk, date)
);

CREATE TABLE menus
(
    id_menu          INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    id_rest          INTEGER                      NOT NULL,
    counter_voice    INTEGER DEFAULT 0            NOT NULL,
    date             DATE                         NOT NULL
);

CREATE SEQUENCE meal_seq START WITH 0 increment by 1;
CREATE TABLE meals
(
    meal_id          INTEGER GENERATED BY DEFAULT AS SEQUENCE meal_seq PRIMARY KEY,
    menu_id          INTEGER                      NOT NULL,
    name             VARCHAR(50)                  NOT NULL,
    description      VARCHAR(200)                 NOT NULL,
    calories         INTEGER                      NOT NULL,
    price            FLOAT                        NOT NULL
);

ALTER TABLE user_roles ADD FOREIGN KEY (role_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE restaurants ADD FOREIGN KEY (id_owner_rest) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE voters ADD FOREIGN KEY (voter_id_pk) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE menus ADD FOREIGN KEY (id_rest) REFERENCES restaurants (id_rest) ON DELETE CASCADE;
ALTER TABLE meals ADD FOREIGN KEY (menu_id) REFERENCES menus (id_menu) ON DELETE CASCADE;
ALTER TABLE voters ADD FOREIGN KEY (menu_id) REFERENCES menus (id_menu) ON DELETE CASCADE;

