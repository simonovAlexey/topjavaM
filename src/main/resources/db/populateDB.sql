DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, user_id) VALUES
  ( 'Завтрак', 500,100000),
  ( 'Обед', 1000,100000),
  ( 'Ужин', 500,100000),
  ( 'Завтрак Админ', 500,100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2016-12-25 08:39:49.0','Завтрак1', 500,100000),
  ('2016-12-25 12:39:49.0','Обед1', 1000,100000),
  ('2016-12-25 18:39:49.0','Ужин1', 510,100000),
  ('2016-12-25 16:39:49.0','Ужин Админ', 510,100001);
