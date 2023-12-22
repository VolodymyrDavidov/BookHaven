The Book Haven is a Java-based RESTful application built using the Spring Boot framework. It serves as a platform for managing an online bookstore, namely creating, updating, deleting components such as books, categories, shopping cart, order. Implements registration and authentication using jvt token for admin and buyer. It provides functions for searching and ordering books. This project was the result of a desire to learn and implement my knowledge and skills in the Spring Boot Framework

Technologies Used
Maven 3.9.5
Java 17+
Spring Boot
Spring Data JPA
Spring Boot Security
JSON Web Token
Lombok 
MapStruct
Hibernate
MySql 8
JUnit5
Testcontainers
Mockito
Docker
Liquibase
Postman

How to use the project
Before you start, make sure you have installed: JDK17+, Docker, Postman.
Clone this repository.
Create an .env file in the root directory 
Run these commands to create a docker container and run the image: 
docker-compose build
docker-compose up
Now whatever port you specified in SPRING_LOCAL_PORT, the program will run on that port: http: //localhost:8081

Examples of endpoints

