# Spring Boot Student Managment App (Backend)

## Setup

## Clone the project:

### `git clone https://github.com/clamprou/springboot-students `

## Create a mysql database with docker (you can use watever you want):

### `docker run --name mydb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql`


## Create database using sql client:
### `create database mydb;`
### `use mydb;`
### `insert into users values(1,true,'admin@admin.com','admin admin','$2a$10$BwC.9HN.8qYF2jWihQzS8uA6VlooXWAwvNBdv4.l4l1UBAdKDEm3.');`
### `insert into roles values(1,'ROLE_USER');`
### `insert into roles values(2,'ROLE_STUDENT');`
### `insert into roles values(3,'ROLE_SECRETARY');`
### `insert into roles values(4,'ROLE_ADMIN');`
### `insert into users_roles values(1,1);`
### `insert into users_roles values(1,4);`
### `insert into course values(1,'Some details','Programming');`
### `insert into course values(2,'Some details','A.I.');`
### `insert into course values(3,'Some details','Web programming');`
