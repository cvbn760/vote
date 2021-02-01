DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS voters;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;


CREATE TABLE user_roles
(
     role_id INTEGER PRIMARY KEY,
     role    VARCHAR(20) NOT NULL ,
     CONSTRAINT user_roles_idx UNIQUE (role_id, role)
);

CREATE TABLE users
(
     id               SERIAL PRIMARY KEY,
    --id_role          INTEGER                     NOT NULL,
     name             VARCHAR(30)                 NOT NULL,
     email            VARCHAR(30)                 NOT NULL,
     password         VARCHAR(250)                 NOT NULL
);


CREATE TABLE restaurants
(
	id_rest           SERIAL PRIMARY KEY,
	id_owner_rest     INTEGER                     NOT NULL,
	name_rest VARCHAR(50)                         NOT NULL,
	address VARCHAR(50)                           NOT NULL
);

CREATE TABLE voters
(
	v_id              SERIAL PRIMARY KEY,
	menu_id           INTEGER,
	voter_id_pk       INTEGER                     NOT NULL,  
	date              DATE                        NOT NULL,
	voice             BOOLEAN DEFAULT FALSE       NOT NULL
);

CREATE TABLE menus
(
	id_menu          SERIAL PRIMARY KEY,
	id_rest          INTEGER                      NOT NULL,
	counter_voice    INTEGER DEFAULT 0            NOT NULL,
	date             DATE                         NOT NULL
);

CREATE TABLE meals
(
	meal_id               SERIAL PRIMARY KEY,
	menu_id          INTEGER                      NOT NULL,
	name             VARCHAR(50)                  NOT NULL,
	description      VARCHAR(200)                 NOT NULL,
	calories         INTEGER                      NOT NULL,
	price            FLOAT                        NOT NULL
);

--ALTER TABLE users ADD FOREIGN KEY (id_role) REFERENCES user_roles(role_id);
ALTER TABLE user_roles ADD FOREIGN KEY (role_id) REFERENCES users(id);
ALTER TABLE restaurants ADD FOREIGN KEY (id_owner_rest) REFERENCES users (id);
ALTER TABLE voters ADD FOREIGN KEY (voter_id_pk) REFERENCES users (id);
ALTER TABLE menus ADD FOREIGN KEY (id_rest) REFERENCES restaurants (id_rest);
ALTER TABLE meals ADD FOREIGN KEY (menu_id) REFERENCES menus (id_menu);
ALTER TABLE voters ADD FOREIGN KEY (menu_id) REFERENCES menus (id_menu);

INSERT INTO users (name, email, password)
VALUES
('Алексей', 'user@yandex.ru', 'password'),
('Егор', 'admin@gmail.com', 'admin'),
('Игорь', 'stack@yandex.com', 'Itest'),
('Анна', 'anna2000@mail.com', 'anneta'),
('Евгений', 'orbita13@outlook.com', 'orbit'),
('Ольга', 'thelastofuspart2@gmail.com', 'joelisdeadabbyisskura');

INSERT INTO user_roles (role, role_id) VALUES
('USER', 1),
('ADMIN', 2),
('USER', 3),
('ADMIN', 4),
('USER', 5),
('ADMIN', 6);

INSERT INTO restaurants (id_owner_rest, name_rest, address)
VALUES
(2, 'Якитория', 'г.Москва; ст.м. Тимирязевская'),
(4, 'Шаурдональдс', 'г.Москва; ст.м. Новослободская'),
(6, 'Пицца Папа Джонс', 'г.Москва; ст.м. Проспект Мира');

INSERT INTO menus (id_rest, counter_voice, date)
VALUES
-- Данные о голосовании за обеды в Якитории
(1, 1500, '14-07-2020'),
(1, 1550, '15-07-2020'),
(1, 1560, '16-07-2020'),
-- Данные о голосовании за обеды в Шаурдональдсе
(2, 1100, '14-07-2020'),
(2, 736, '15-07-2020'),
(2, 1080, '16-07-2020'),
-- Данные о голосовании за обеды в Папе Джонс
(3, 699, '14-07-2020'),
(3, 1885, '15-07-2020'),
(3, 1375, '16-07-2020');

INSERT INTO voters (menu_id, voter_id_pk, date, voice)
VALUES
--Голосователь 1
(7, 1, '14-07-2020', true),
(null, 1, '15-07-2020', false),
(3, 1, '16-07-2020', true),
--Голосователь 2
(null, 3, '14-07-2020', false),
(3, 3, '15-07-2020', true),
(null, 3, '16-07-2020', false),
--Голосователь 3
(4, 5, '14-07-2020', true),
(2, 5, '15-07-2020', true),
(null, 5, '16-07-2020', false);


INSERT INTO meals (menu_id, name, description, calories, price)
VALUES 
--Якитория.
--Состав меню за 14-07-2020
(1,'Кофе','капучино', 200, 2.49),
(1,'Сет микс','10/15/20 шт', 1300, 5.59),
(1,'Суп','из осьминога', 350, 4.29),
(1,'Хлеб','черный/белый', 100, 0.99),
(1,'Пончики','шоколадные/с сахарной пудрой 5шт', 400, 3.99),
--Состав меню за 15-07-2020
(2,'Кофе','латте', 200, 2.49),
(2,'Королевский сет','10/15/20 шт', 1500, 7.99),
(2,'Суп','Из крокодила', 330, 4.29),
(2,'Хлеб','черный/белый', 100, 0.99),
(2,'Кекс','куряга/изюм/вишня', 250, 2.7),
--Состав меню за 16-07-2020
(3,'Чай','лимон/персик/яблоко/апельсин/черный/зеленый', 100, 1.2),
(3,'Сет дары моря','10/15/20 шт', 900, 6.49),
(3,'Суп','из морской капусты', 300, 3.49),
(3,'Хлеб','черный/белый', 100, 0.99),
(3,'Кекс','шоколадный/ванильный', 300, 2.99),

--Шаурдональдс.
--Состав меню за 14-07-2020
(4,'Сок','персик/яблоко/виноград/мультифрукт', 160, 2.00),
(4,'Шаурма','острая', 600, 4.40),
(4,'Пирожки','картошка/капуста/яблоко 3шт', 500, 3.29),
(4,'Мороженное','шоколадное/ванильное', 250, 1.49),
(4,'Рогалик','шоколадный/ванильный/яблочный/персиковый крем', 280, 3.2),
--Состав меню за 15-07-2020
(5,'Сок','персик/яблоко/виноград/мультифрукт', 160, 2.00),
(5,'Шаурма','сырная', 700, 4.99),
(5,'Блины','картошка/сметана 3шт', 400, 2.79),
(5,'Мороженное','шоколадное/ванильное', 250, 1.49),
(5,'Пирожные','махаон/медовик', 300, 2.9),
--Состав меню за 16-07-2020
(6,'Сок','персик/яблоко/виноград/мультифрукт', 100, 2.00),
(6,'Шаурма','вегетарианская', 400, 4.99),
(6,'Сырники','обыкновенные 300гр', 400, 2.99),
(6,'Мороженное','шоколадное/ванильное', 250, 1.49),
(6,'Пирожные',' картошка', 300, 2.9),
--Папа Джонс.
--Состав меню за 14-07-2020
(7,'Пиво','темное/светлое', 300, 1.6),
(7,'Пицца','Итальянская 30/45 см', 1100, 7.00),
(7,'Стейк','свиной/говяжий', 700, 5.79),
(7,'Сок','яблоко/персик/мультифрукт', 200, 2.49),
(7,'Мороженное','шоколадное/полонез', 290, 3.00),
--Состав меню за 15-07-2020
(8,'Пиво','темно/светлое', 300, 1.6),
(8,'Пицца','С солями 30/45 см', 1200, 6.49),
(8,'Рулька','свиная', 800, 6.49),
(8,'Мороженное','шоколадное/ванильное', 250, 1.49),
(8,'Кекс','шоколадный/ванильный', 200, 2.49),
--Состав меню за 16-07-2020
(9,'Пиво','темное/светлое', 300, 1.6),
(9,'Пицца','4 сыра 30/45 см', 1000, 6.49),
(9,'Грудка','куриная', 400, 4.99),
(9,'Мороженное','шоколадное/ванильное', 250, 1.49),
(9,'Мус','шоколадный/ванильный', 280, 2.7);




-- SELECT * FROM restaurants rest
-- INNER JOIN menus mns ON mns.id_rest = rest.id_rest;

-- SELECT * FROM restaurants rest
-- INNER JOIN users us ON us.id = rest.id_owner_rest
-- INNER JOIN user_roles ur ON ur.role_id = us.id_role;

--SELECT * FROM voters;

--SELECT * FROM menus;

















-- INSERT INTO user_roles (role, user_id) VALUES
-- ('USER', 0),
-- ('ADMIN', 1),
-- ('USER', 2),
-- ('ADMIN', 3),
-- ('ADMIN', 4),
-- ('USER', 5);

-- INSERT INTO restaurans (id_rest, name_rest) VALUES
-- (1, 'Якитория'),
-- (3, 'Шаурдональдс'),
-- (4, 'Пицца Папа Джонс');

-- INSERT INTO voter (vote_id, counter_voice, date) VALUES
-- (1, 928, '2020-08-07'),
-- (3, 1374, '2020-08-07'),
-- (4, 748, '2020-08-07');

-- INSERT INTO meals (meal_id, name, description, price) VALUES
-- -- Меню для Якитории
-- (1, 'Обычные роллы', 'В коробке 20 штук. 5 разных вкусов по 4 штуки', 39.99),
-- (1, 'Римские роллы', 'Изготовлены по секретному рецепту. Рецепт выкрали у древних римлян', 35),
-- (1, 'Китайский суп из кошки', '...?!', 99.99),
-- (1, 'Острые роллы', 'Самые острые среди тех что вы можете найти. 10 штук.', 44.99),
-- (1, 'Роллы из морепродуктов', 'В коробке 10 штук. Вместо рыбы добавленна морская капуста', 44.99),
-- -- Меню для Шаурдональдса
-- (3, 'Шаурма в обычном лаваше', '300 гр', 10),
-- (3, 'Шаурма в сырном лаваше', '300 гр', 12.5),
-- (3, 'Шаурма в пите', '300 гр', 15),
-- (3, 'Шаурма с грибами', '300 гр', 11.99),
-- (3, 'Шаурма в обычном лаваше', '300 гр', 15),
-- -- Меню для Пицца Папа Джонс
-- (4, 'Пицца 4 сыра', '30/45 см', 29.99),
-- (4, 'Пицца микс', '30/45 см', 34.99),
-- (4, 'Пицца с солями', '30/45 см', 29.99),
-- (4, 'Пицца c луком и сметаной', '30/45 см', 29.99),
-- (4, 'Пицца с грибами', '30/45 см', 34.99);

-- SELECT DISTINCT * FROM meals ms 
-- INNER JOIN voter vt ON vt.vote_id = ms.meal_id
-- INNER JOIN restaurans rs ON rs.id_rest = vt.vote_id;