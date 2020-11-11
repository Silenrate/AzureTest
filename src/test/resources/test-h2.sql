DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
   id serial PRIMARY KEY,
   username VARCHAR(255) UNIQUE NOT NULL,
   password VARCHAR(255)  NOT NULL
);

CREATE TABLE IF NOT EXISTS foods (
   id serial PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   username INTEGER NOT NULL
);

ALTER TABLE foods ADD CONSTRAINT FK_FOODS_USER
  foreign key (username) references users (id)
  on delete cascade;