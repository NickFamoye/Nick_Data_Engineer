create extension hstore;

create schema logindatabase;
create schema multipledatadatabase;

CREATE TABLE logindatabase."LoginDatabase"(
   id SERIAL PRIMARY KEY,
   username varchar(20) NOT NULL UNIQUE,
   password varchar(20) NOT NULL
--   signup_date Date
);

CREATE TABLE IF NOT EXISTS multipledatadatabase."MultipleDataDatabase"(
    user_id             varchar(128),
    device_type         varchar(32),
    masked_ip           varchar(256),
    masked_device_id    varchar(256),
    locale              varchar(32),
    app_version         integer,
    create_date         Date
);
