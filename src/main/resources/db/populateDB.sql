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

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2016-12-25 08:00:00.0','Завтрак тестовый1', 500,100000),
  ('2016-12-26 08:00:00.0','Завтрак тестовый2', 500,100000);

  /*('2016-12-25 12:00:00.0','Обед', 1000,100000),
  ('2016-12-25 19:00:00.0','Ужин', 510,100000),

  ('2016-12-26 12:00:00.0','Обед1', 1000,100000),
  ('2016-12-26 08:00:00.0','Завтрак1', 500,100000),
  ('2016-12-26 19:00:00.0','Ужин1', 500,100000),
  ('2016-12-25 19:00:00.0','Ужин Админ', 2010,100001);*/
