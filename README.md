# Spring Boot Student Managment App (Backend)

## [Documentation.pdf](Documentation.pdf)

## Setup

## Clone the project:

### `git clone https://github.com/clamprou/springboot-students `

## Create a mysql database with docker (you can use watever you want):

### `sudo docker run --name mydb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql`


## Create database using sql client:
### `create database mydb;`
### `use mydb;`

## It is important to do the inserts after running for the first time the app because inserts will fail cause tables are not created
### `insert into users values(1,true,'admin@admin.com','admin admin','$2a$10$BFx2bsSiyw6.uqL3gnMh5eMYb5uvpDa9yzr9JyAFIKyG//kw3rzqK');`
### `insert into roles values(1,'ROLE_USER');`
### `insert into roles values(2,'ROLE_STUDENT');`
### `insert into roles values(3,'ROLE_SECRETARY');`
### `insert into roles values(4,'ROLE_ADMIN');`
### `insert into users_roles values(1,1);`
### `insert into users_roles values(1,4);`
### `insert into course values(1,'Some details','Programming');`
### `insert into course values(2,'Some details','A.I.');`
### `insert into course values(3,'Some details','Web programming');`

## Run with Maven and java 17 installed:
### `mvn clean install`
### `java -jar targer/registration-login-demo-0.0.1-SNAPSHOT.jar`

![Screenshot 2024-03-13 213958.png](Screenshot%202024-03-13%20213958.png)