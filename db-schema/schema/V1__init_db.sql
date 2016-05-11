create table users (
    id bigint auto_increment NOT NULL,
    username varchar(30) not null UNIQUE,
    password VARCHAR(60) not NULL,
    name VARCHAR(30) not NULL,
    type VARCHAR(1) not NULL,
    PRIMARY KEY(id)
);


create table departments (
    id bigint auto_increment NOT NULL,
    name varchar(60) not null UNIQUE,
    type VARCHAR(1) NOT NULL,
    PRIMARY KEY(id)
);